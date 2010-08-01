package de.gaalop.maple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import de.gaalop.Notifications;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FunctionArgument;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.UpdateMacroCallVisitor;
import de.gaalop.dfg.Variable;

/**
 * This class is responsible for inlining user-defined macros at places where macros are called.
 * 
 * @author Christian Schwinn
 * 
 */
public class InlineMacrosVisitor extends EmptyExpressionVisitor implements ControlFlowVisitor {

	private Map<String, List<Expression>> currentArguments = new HashMap<String, List<Expression>>();
	private Set<Node> visitedNodes = new HashSet<Node>();
	private List<Variable> newVariables = new ArrayList<Variable>();
	private ControlFlowGraph graph;

	private String generateUniqueName(String original) {
		String unique = original;
		Random r = new Random(System.currentTimeMillis());
		while (graph.containsLocalVariable(unique)) {
			unique = original + "__" + r.nextInt(100);
		}
		return unique;
	}

	/**
	 * Prevents visited nodes from being visited again and causing a potential infinite loop.
	 * 
	 * @param node node from which to continue visiting
	 */
	private void continueVisitFrom(SequentialNode node) {
		if (!visitedNodes.contains(node.getSuccessor())) {
			visitedNodes.add(node.getSuccessor());
			node.getSuccessor().accept(this);
		}
	}

	@Override
	public void visit(StartNode node) {
		this.graph = node.getGraph();
		continueVisitFrom(node);
	}

	@Override
	public void visit(AssignmentNode node) {
		node.getValue().accept(this);
		continueVisitFrom(node);
	}

	@Override
	public void visit(ExpressionStatement node) {
		if (node.getExpression() instanceof MacroCall) {
			((MacroCall) node.getExpression()).setSingleLine();
		} else {
			Notifications.addWarning("Found an expression without assignment: '" + node
					+ "' (Side effects are not supported.)");
		}
		node.getExpression().accept(this);
		continueVisitFrom(node);
	}

	@Override
	public void visit(StoreResultNode node) {
		node.getValue().accept(this);
		continueVisitFrom(node);
	}

	@Override
	public void visit(IfThenElseNode node) {
		node.getCondition().accept(this);
		node.getPositive().accept(this);
		node.getNegative().accept(this);
		continueVisitFrom(node);
	}

	@Override
	public void visit(BlockEndNode node) {
	}

	@Override
	public void visit(LoopNode node) {
		node.getBody().accept(this);

		continueVisitFrom(node);
	}

	@Override
	public void visit(BreakNode node) {
		continueVisitFrom(node);
	}

	@Override
	public void visit(Macro node) {
		node.getGraph().removeNode(node);
		continueVisitFrom(node);
	}

	@Override
	public void visit(EndNode node) {
	}

	@Override
	public void visit(FunctionArgument node) {
		throw new IllegalStateException("This method should not be reachable...");
	}

	@Override
	public void visit(MacroCall node) {
		newVariables.clear();
		SequentialNode caller = node.getCaller();
		Macro macro = graph.getMacro(node.getName());
		String macroName = macro.getName();
		List<Expression> arguments = node.getArguments();
		for (Expression arg : arguments) {
			arg.accept(this);
		}
		currentArguments.put(macroName, arguments);
		// generate unique names for each variable in current scope (to be used for each statement in this scope)
		Map<String, String> newNames = new HashMap<String, String>();
		for (Variable v : graph.getLocalVariables()) {
			newNames.put(v.getName(), generateUniqueName(v.getName()));
		}
		Node oldPredecessor = macro;
		for (SequentialNode statement : macro.getBody()) {
			SequentialNode newStatement = statement.copy();
			if (newStatement instanceof AssignmentNode) {
				AssignmentNode assignment = (AssignmentNode) newStatement;
				UpdateMacroCallVisitor updater = new UpdateMacroCallVisitor(assignment);
				assignment.getValue().accept(updater);
			}
			newStatement.removePredecessor(oldPredecessor);
			replaceUsedVariables(newStatement, macroName, newNames);
			caller.insertBefore(newStatement);
			newStatement.accept(this);
			oldPredecessor = statement;
		}
		if (!node.isSingleLine()) {
			if (macro.getReturnValue() == null) {
				throw new IllegalArgumentException(
						"Cannot inline a macro without return value into the following statement:\n" + caller);
			}
			Expression returnValue = macro.getReturnValue().copy();
			replaceUsedVariablesInExpression(returnValue, macroName, newNames);
			Variable retVal = new Variable(generateUniqueName("rslt"));
			graph.addLocalVariable(retVal);
			AssignmentNode result = new AssignmentNode(graph, retVal, returnValue);
			caller.insertBefore(result);
			caller.replaceExpression(node, retVal);
		} else {
			graph.removeNode(caller);
		}
		// add new local variables to graph
		for (Variable v : newVariables) {
			graph.removeInputVariable(v);
			if (!graph.getIgnoreVariables().contains(v)) {
				graph.addLocalVariable(v);
			}
		}
	}
	
	private void replaceUsedVariables(Node statement, String macroName, Map<String, String> newNames) {
		if (statement instanceof AssignmentNode) {
			AssignmentNode assignment = (AssignmentNode) statement;
			newVariables.add(assignment.getVariable());
			replaceUsedVariablesInExpression(assignment.getVariable(), macroName, newNames);
			Expression newExpression = replaceUsedVariablesInExpression(assignment.getValue(), macroName, newNames);
			assignment.replaceExpression(assignment.getValue(), newExpression);
		}
		if (statement instanceof StoreResultNode) {
			StoreResultNode srn = (StoreResultNode) statement;
			replaceUsedVariablesInExpression(srn.getValue(), macroName, newNames);
		}
		if (statement instanceof IfThenElseNode) {
			IfThenElseNode ite = (IfThenElseNode) statement;
			replaceUsedVariablesInExpression(ite.getCondition(), macroName, newNames);
			replaceSubtree(ite.getPositive(), macroName, newNames);
			replaceSubtree(ite.getNegative(), macroName, newNames);
		}
		if (statement instanceof LoopNode) {
			LoopNode loop = (LoopNode) statement;
			replaceSubtree(loop.getBody(), macroName, newNames);
		}
	}

	private void replaceSubtree(Node root, String macroName, Map<String, String> newNames) {
		if (root instanceof BlockEndNode) {
			return;
		}
		if (root instanceof SequentialNode) {
			SequentialNode node = (SequentialNode) root;
			replaceUsedVariables(node, macroName, newNames);
			replaceSubtree(node.getSuccessor(), macroName, newNames);
		}
	}

	private Expression replaceUsedVariablesInExpression(Expression e, String macroName, Map<String, String> newNames) {
		UsedVariablesVisitor visitor = new UsedVariablesVisitor();
		e.accept(visitor);
		Expression newExpression = e;
		for (Variable v : visitor.getVariables()) {
			if (graph.containsLocalVariable(v.getName()) && !v.globalAccess()) {
				String unique = newNames.get(v.getName());
				Variable newVariable = new Variable(unique);
				e.replaceExpression(v, newVariable);
				newExpression = e;
				graph.addLocalVariable(newVariable);
			} else if (v instanceof FunctionArgument) {
				FunctionArgument argument = (FunctionArgument) v;
				Expression newArgument = currentArguments.get(macroName).get(argument.getIndex() - 1);
				e.replaceExpression(argument, newArgument);
				if (e instanceof FunctionArgument) {
					newExpression = newArgument;
				} else {
					newExpression = e;
				}
			}
		}
		return newExpression;
	}

}
