package de.gaalop.maple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gaalop.Notifications;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Variable;
import de.gaalop.maple.engine.MapleEngine;
import de.gaalop.maple.engine.MapleEngineException;
import de.gaalop.maple.parser.MapleLexer;
import de.gaalop.maple.parser.MapleParser;
import de.gaalop.maple.parser.MapleTransformer;

/**
 * This visitor creates code for Maple.
 */
public class MapleCfgVisitor implements ControlFlowVisitor {

	/**
	 * Simple helper visitor used to inline parts of conditional statements.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
	private static class InlineBlockVisitor extends EmptyControlFlowVisitor {

		private final IfThenElseNode root;
		private final Node branch;
		private final Node successor;

		/**
		 * Creates a new visitor with given root and branch.
		 * 
		 * @param root root node from which to inline a branch
		 * @param branch first node of branch to be inlined
		 */
		public InlineBlockVisitor(IfThenElseNode root, Node branch) {
			this.root = root;
			this.branch = branch;
			successor = root.getSuccessor();
		}

		private void replaceSuccessor(Node oldSuccessor, Node newSuccessor) {
			Set<Node> predecessors = new HashSet<Node>(oldSuccessor.getPredecessors());
			for (Node p : predecessors) {
				p.replaceSuccessor(oldSuccessor, newSuccessor);
			}
		}

		@Override
		public void visit(IfThenElseNode node) {
			// we peek only to next level of nested statements
			if (node == root) {
				if (node.getPositive() == branch) {
					replaceSuccessor(node, branch);
					node.getPositive().accept(this);
				} else if (node.getNegative() == branch) {
					replaceSuccessor(node, branch);
					node.getNegative().accept(this);
				}
				node.getGraph().removeNode(node);
			}
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(BlockEndNode node) {
			// this relies on the fact that nested statements are being ignored in visit(IfThenElseNode),
			// otherwise successor could be the wrong one
			replaceSuccessor(node, successor);
		}

	}

	/**
	 * Simple control flow visitor that restores variable assignments that might be overridden in if-then-else statements. For
	 * those statements where the condition cannot be evaluated due to unknown input parameters, and hence cannot be inlined, this
	 * visitor places an additional assignment to affected variables right before the {@link IfThenElseNode} that would override
	 * the value of a variable. Therefore, the root {@link IfThenElseNode} has to be passed to the constructor. Nested
	 * if-then-else statements are processed, too, but variable restores are placed before the root node.
	 */
	private class RestoreValuesVisitor extends EmptyControlFlowVisitor {

		private IfThenElseNode root;
		private Set<String> processedVariables = new HashSet<String>();

		/**
		 * Creates a new visitor which restores overridden variables in this statement and its nested subnodes.
		 * 
		 * @param root {@link IfThenElseNode} in front of which restored variables have to be inserted.
		 */
		public RestoreValuesVisitor(IfThenElseNode root) {
			this.root = root;
		}

		@Override
		public void visit(AssignmentNode node) {
			ControlFlowGraph graph = node.getGraph();
			Variable variable = node.getVariable();
			String name = variable.getName();
			if (graph.containsLocalVariable(name) && !processedVariables.contains(name)) {
				try {
					processedVariables.add(name);
					// 1. get current value
					String result = engine.evaluate(name + ";");
					Notifications.addWarning("Restored optimized current value of " + variable
						+ " before occurence of if-statement.");
					if (result.endsWith("\n")) {
						result = result.substring(0, result.length() - 1);
					}
					result = name + ":=" + result + ";";
					List<SequentialNode> parsed = parseMapleCode(graph, result);
					// 2. restore value
					for (SequentialNode newAssignment : parsed) {
						root.insertBefore(newAssignment);
					}
				} catch (MapleEngineException e) {
					throw new RuntimeException("Unable to restore state of variable " + name + " before if-statement", e);
				}
			}
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(IfThenElseNode node) {
			node.getPositive().accept(this);
			node.getNegative().accept(this);
			if (node != root) {
				// only for nested if-then-else nodes
				node.getSuccessor().accept(this);
			}
		}
		
		public Set<String> getVariables() {
			return processedVariables;
		}
	}

	private Log log = LogFactory.getLog(MapleCfgVisitor.class);

	private MapleEngine engine;

	private HashMap<String, String> oldMinVal;
	private HashMap<String, String> oldMaxVal;

	private Plugin plugin;
	
	private boolean branchMode = false;
	private Map<String, String> rollbackValues = new HashMap<String, String>();


	public MapleCfgVisitor(MapleEngine engine, Plugin plugin) {
		this.engine = engine;
		this.plugin = plugin;
	}

	private String generateCode(Expression expression) {
		MapleDfgVisitor visitor = new MapleDfgVisitor();
		expression.accept(visitor);
		return visitor.getCode();
	}

	@Override
	public void visit(StartNode startNode) {
		plugin.notifyStart();
		startNode.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		/*
		 * FIXME: special treatment in case it is a single line using a math function, which is not support. (Atm only abs is
		 * supported) We dont call Matlab for this, as it cannot handle these correct the mathfunction has to be on a single line,
		 * with a single var parameter like x = sqrt(y);
		 */
//		if (node.getValue() instanceof MathFunctionCall) {
//			MathFunction func = ((MathFunctionCall) (node.getValue())).getFunction();
//			if ((func != MathFunction.ABS)) {
//				node.getSuccessor().accept(this);
//				return;
//				// FIXME: previous assignments contributing to this statement might get lost
//			}
//		}
		// FIXME: Maple cannot compute things like sqrt(abs(VecN3(1,2,3)));
		
		if (branchMode) {
			// get current value from maple for rollback
			String variableName = node.getVariable().getName();
			try {
				String command = variableName + ";\n";
				String currentValue = engine.evaluate(command);
				if (!rollbackValues.containsKey(variableName)) {
					rollbackValues.put(variableName, currentValue);
				}				
			} catch (MapleEngineException e) {
				throw new RuntimeException("Unable to query current value of " + variableName + " from Maple.", e);
			}
		}

		String variableCode = generateCode(node.getVariable());
		// If you want to simplify (and keep) the last assignment to every variable
		// uncomment the following statement:
		// simplifyBuffer.add(variableCode);
		
		StringBuilder codeBuffer = new StringBuilder();
		
		codeBuffer.append(variableCode);
		codeBuffer.append(" := ");
		codeBuffer.append(generateCode(node.getValue()));
		codeBuffer.append(";\n");
		
		try {
			engine.evaluate(codeBuffer.toString());
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to simplify assignment " + node + " in Maple.", e);
		}
		
		// notify observables about progress (must be called before successor.accept(this))
		plugin.notifyProgress();
		
		if (branchMode) {
			// add store result node, if necessary
			if (!(node.getSuccessor() instanceof StoreResultNode)) {
				node.insertAfter(new StoreResultNode(node.getGraph(), node.getVariable()));
			}
		}

		node.getGraph().removeNode(node);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(ExpressionStatement node) {
		String command = generateCode(node.getExpression());
		command += ";\n";
		try {
			engine.evaluate(command);
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to simplify statement " + node + " in Maple.", e);
		}
		node.getGraph().removeNode(node);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(StoreResultNode node) {
		String evalResult = simplify(node.getValue());
		log.debug("Maple simplification of " + node.getValue() + ": " + evalResult);

		List<SequentialNode> newNodes = parseMapleCode(node.getGraph(), evalResult);

		for (SequentialNode newNode : newNodes) {
			node.insertBefore(newNode);
		}

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		Expression condition = node.getCondition();
		try {
			boolean unknown = false;
			UsedVariablesVisitor visitor = new UsedVariablesVisitor();
			condition.accept(visitor);
			for (Variable v : visitor.getVariables()) {
				String name = v.getName();
				String result = engine.evaluate(name + ";");
				if (result.equals(name + "\n")) {
					unknown = true;
					break;
				}
			}
			if (!unknown) {
				StringBuilder codeBuffer = new StringBuilder();
				codeBuffer.append("evalb(");
				codeBuffer.append(generateCode(condition));
				codeBuffer.append(");\n");
				// try to evaluate the condition
				String result = engine.evaluate(codeBuffer.toString());
				log.debug("Maple simplification of IF condition " + condition + ": " + result);
				// if condition can be determined to be true or false, inline relevant part
				if ("true\n".equals(result)) {
					node.accept(new InlineBlockVisitor(node, node.getPositive()));
					node.getPositive().accept(this);
				} else if ("false\n".equals(result)) {
					node.accept(new InlineBlockVisitor(node, node.getNegative()));
					node.getNegative().accept(this);
				} else {
					// reset unknown status in order to process branches
					Notifications.addWarning("Could not evaluate condition " + condition);
					unknown = true;
				}
			}
			if (unknown) {
				/*
				 * Restore overridden variables to prevent loss of information during Maple optimization Must be performed in any
				 * case because Maple assignment gets reset and variable could be modified by one branch only.
				 */
				RestoreValuesVisitor restoreVisitor = new RestoreValuesVisitor(node);
				node.accept(restoreVisitor);

				branchMode = true;
				node.getPositive().accept(this);
				rollback();

				node.getNegative().accept(this);
				resetVariables(restoreVisitor.getVariables());
				branchMode = false;

				node.getSuccessor().accept(this);
			} 
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to check condition " + condition + " in if-statement " + node, e);
		}
	}
	
	private void rollback() {
		for (String variable : rollbackValues.keySet()) {
			String value = rollbackValues.get(variable);
			String command = variable + ":=" + value + ";\n";
			try {
				engine.evaluate(command);
			} catch (MapleEngineException e) {
				throw new RuntimeException("Could not rollback assignment of variable " + variable, e);
			}
		}
		
		rollbackValues.clear();
	}
	
	private void resetVariables(Set<String> variables) {
		for (String variable : variables) {
			String clearCommand = variable + ":= '" + variable + "';";
			try {
				engine.evaluate(clearCommand);
			} catch (MapleEngineException e) {
				throw new RuntimeException("Could not reset variable " + variable, e);
			}
		}
	}

	@Override
	public void visit(LoopNode node) {
		// TODO: eliminate GA operations from loop body in any case
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(BreakNode node) {
		// nothing to do
	}

	@Override
	public void visit(BlockEndNode node) {
		// nothing to do
	}

	@Override
	public void visit(EndNode endNode) {
	}

	/**
	 * Parses a snippet of maple code and returns a list of CFG nodes that implement the returned maple expressions.
	 * 
	 * @param graph The control flow graph the new nodes should be created in.
	 * @param mapleCode The code returned by Maple.
	 * @return A list of control flow nodes modeling the returned code.
	 */
	private List<SequentialNode> parseMapleCode(ControlFlowGraph graph, String mapleCode) {
		oldMinVal = new HashMap<String, String>();
		oldMaxVal = new HashMap<String, String>();

		/* fill the Maps with the min and maxvalues from the nodes */
		for (Variable v : graph.getInputVariables()) {
			if (v.getMinValue() != null) oldMinVal.put(v.getName(), v.getMinValue());
			if (v.getMaxValue() != null) oldMaxVal.put(v.getName(), v.getMaxValue());
		}

		MapleLexer lexer = new MapleLexer(new ANTLRStringStream(mapleCode));
		MapleParser parser = new MapleParser(new CommonTokenStream(lexer));
		try {
			MapleParser.program_return result = parser.program();
			MapleTransformer transformer = new MapleTransformer(new CommonTreeNodeStream(result.getTree()));
			return transformer.script(graph, oldMinVal, oldMaxVal);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Simplifies a single Expression node.
	 * 
	 * @param expression The data flow graph that should be simplified
	 * @return The code Maple returned as the simplification.
	 */
	private String simplify(Expression expression) {
		StringBuilder codeBuffer = new StringBuilder();
		codeBuffer.append("gaalop(");
		codeBuffer.append(generateCode(expression));
		codeBuffer.append(");\n");

		try {
			return engine.evaluate(codeBuffer.toString());
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to apply gaalop() function on expression " + expression + " in Maple.", e);
		}
	}

	@Override
	public void visit(Macro node) {
		throw new IllegalStateException("Macros should have been inlined.");
	}
}
