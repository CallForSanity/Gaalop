package de.gaalop.maple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.gaalop.Notifications;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.SetCallerVisitor;
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
	
	private class ReplaceExpressionVisitor implements ControlFlowVisitor {
		
		private final String macroName;
		private final Map<String, String> newNames;
		
		public ReplaceExpressionVisitor(String macroName, Map<String, String> newNames) {
			this.macroName = macroName;
			this.newNames = newNames;
		}


		Expression replaceInExpression(Expression e) {
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

		private void replaceSubtree(Node root) {
			if (root instanceof BlockEndNode) {
				return;
			}
			if (root instanceof SequentialNode) {
				SequentialNode node = (SequentialNode) root;
				node.accept(this);
				replaceSubtree(node.getSuccessor());
			}
		}

		@Override
		public void visit(StartNode node) {
		}

		@Override
		public void visit(AssignmentNode node) {
			Variable variable = node.getVariable();
			newVariables.add(variable);
			replaceInExpression(variable);
			Expression newExpression = replaceInExpression(node.getValue());
			node.replaceExpression(node.getValue(), newExpression);
		}

		@Override
		public void visit(StoreResultNode node) {
			replaceInExpression(node.getValue());
		}

		@Override
		public void visit(IfThenElseNode node) {
			replaceInExpression(node.getCondition());
			replaceSubtree(node.getPositive());
			replaceSubtree(node.getNegative());

		}

		@Override
		public void visit(BlockEndNode node) {
		}

		@Override
		public void visit(LoopNode node) {
			replaceSubtree(node.getBody());
		}

		@Override
		public void visit(BreakNode node) {
		}

		@Override
		public void visit(Macro node) {
		}

		@Override
		public void visit(ExpressionStatement node) {
			replaceInExpression(node.getExpression());
		}

		@Override
		public void visit(EndNode node) {
		}


		@Override
		public void visit(ColorNode node) {
			node.getSuccessor().accept(this);
		}
		
	}

	private Set<Node> visitedNodes = new HashSet<Node>();
	Map<String, List<Expression>> currentArguments = new HashMap<String, List<Expression>>();
	List<Variable> newVariables = new ArrayList<Variable>();
	ControlFlowGraph graph;

	private String generateUniqueName(String original) {
		String unique = original;
		int i = 1;
		while (graph.containsLocalVariable(unique)) {
			unique = original + "__" + i;
			i++;
		}
		return unique;
	}

	/**
	 * Prevents visited nodes from being visited again and causing a potential infinite loop.
	 * 
	 * @param node node from which to continue visiting
	 */
	private void continueVisitFrom(SequentialNode node) {
		boolean visitSuccessor = true;
		Node successor = node.getSuccessor();
		for (Node visited : visitedNodes) {
			if (visited == successor) {
				visitSuccessor = false;
			}
		}
		if (visitSuccessor) {
			visitedNodes.add(successor);
			successor.accept(this);
		}
	}

	@Override
	public void visit(StartNode node) {
		visitedNodes.add(node);
		this.graph = node.getGraph();
		continueVisitFrom(node);
	}

	@Override
	public void visit(AssignmentNode node) {
		visitedNodes.add(node);
		node.getValue().accept(this);
		continueVisitFrom(node);
	}

	@Override
	public void visit(ExpressionStatement node) {
		visitedNodes.add(node);
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
		visitedNodes.add(node);
		node.getValue().accept(this);
		continueVisitFrom(node);
	}

	@Override
	public void visit(IfThenElseNode node) {
		visitedNodes.add(node);
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
		visitedNodes.add(node);
		node.getBody().accept(this);

		continueVisitFrom(node);
	}

	@Override
	public void visit(BreakNode node) {
		visitedNodes.add(node);
		continueVisitFrom(node);
	}

	@Override
	public void visit(Macro node) {
		visitedNodes.add(node);
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
		SetCallerVisitor setCaller = new SetCallerVisitor();
		ReplaceExpressionVisitor replacer = new ReplaceExpressionVisitor(macroName, newNames);
		for (SequentialNode statement : macro.getBody()) {
			SequentialNode newStatement = statement.copy();
			newStatement.removePredecessor(oldPredecessor);
			newStatement.accept(setCaller);
			newStatement.accept(replacer);
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
			replacer.replaceInExpression(returnValue);
			Variable retVal = new Variable(generateUniqueName("rslt"));
			graph.addLocalVariable(retVal);
			AssignmentNode result = new AssignmentNode(graph, retVal, returnValue);
			UpdateMacroCallVisitor updater = new UpdateMacroCallVisitor(result);
			result.getValue().accept(updater);
			caller.insertBefore(result);
			caller.replaceExpression(node, retVal);
			result.accept(this);
		} else {
			graph.removeNode(caller);
		}
		// add new local variables to graph
		for (Variable v : newVariables) {
			graph.removeInputVariable(v);
			graph.addLocalVariable(v);
		}
	}

	@Override
	public void visit(ColorNode node) {
		node.getSuccessor().accept(this);
	}

}
