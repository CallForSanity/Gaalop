package de.gaalop.maple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
import de.gaalop.dfg.Variable;

/**
 * This class is responsible for inlining user-defined macros at places where macros are called.
 * 
 * @author Christian Schwinn
 * 
 */
public class InlineMacrosVisitor extends EmptyExpressionVisitor implements ControlFlowVisitor {

	private SequentialNode currentStatement;
	private List<Expression> currentArguments;
	private Set<Node> visitedNodes = new HashSet<Node>();
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
		currentStatement = node;
		node.getValue().accept(this);
		continueVisitFrom(node);
	}

	@Override
	public void visit(ExpressionStatement node) {
		currentStatement = node;
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
		// TODO Auto-generated method stub

		continueVisitFrom(node);
	}

	@Override
	public void visit(BreakNode node) {
		// TODO Auto-generated method stub

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
		SequentialNode caller = currentStatement;
		Macro macro = caller.getGraph().getMacro(node.getName());
		currentArguments = node.getArguments();
		// generate unique names for each variable in current scope (to be used for each statement in this scope)
		Map<String, String> newNames = new HashMap<String, String>();
		for (Variable v : graph.getLocalVariables()) {
			newNames.put(v.getName(), generateUniqueName(v.getName()));
		}
		macro.getBody().get(0).removePredecessor(macro);
		for (SequentialNode statement : macro.getBody()) {
			SequentialNode newStatement = statement.copy();
			replaceUsedVariables(newStatement, newNames);
			caller.insertBefore(newStatement);
		}
		Expression returnValue = macro.getReturnValue();
		replaceUsedVariablesInExpression(returnValue, newNames);
		caller.replaceExpression(node, returnValue);
	}

	private void replaceUsedVariables(Node statement, Map<String, String> newNames) {
		if (statement instanceof AssignmentNode) {
			AssignmentNode assignment = (AssignmentNode) statement;
			replaceUsedVariablesInExpression(assignment.getVariable(), newNames);
			replaceUsedVariablesInExpression(assignment.getValue(), newNames);
		}
		if (statement instanceof StoreResultNode) {
			StoreResultNode srn = (StoreResultNode) statement;
			replaceUsedVariablesInExpression(srn.getValue(), newNames);
		}
		if (statement instanceof IfThenElseNode) {
			IfThenElseNode ite = (IfThenElseNode) statement;
			replaceUsedVariablesInExpression(ite.getCondition(), newNames);
			replaceSubtree(ite.getPositive(), newNames);
			replaceSubtree(ite.getNegative(), newNames);
		}
	}

	private void replaceSubtree(Node root, Map<String, String> newNames) {
		if (root instanceof BlockEndNode) {
			return;
		}
		if (root instanceof SequentialNode) {
			SequentialNode node = (SequentialNode) root;
			replaceUsedVariables(node, newNames);
			replaceSubtree(node.getSuccessor(), newNames);
		}
	}

	private void replaceUsedVariablesInExpression(Expression e, Map<String, String> newNames) {
		UsedVariablesVisitor visitor = new UsedVariablesVisitor();
		e.accept(visitor);
		Set<Variable> variables = visitor.getVariables();
		for (Variable v : variables) {
			if (graph.containsLocalVariable(v.getName()) && !v.globalAccess()) {
				String unique = newNames.get(v.getName());
				Variable newVariable = new Variable(unique);
				e.replaceExpression(v, newVariable);
				graph.addLocalVariable(newVariable);
			} else if (v instanceof FunctionArgument) {
				FunctionArgument argument = (FunctionArgument) v;
				e.replaceExpression(argument, currentArguments.get(argument.getIndex() - 1));
			}
		}
	}

}
