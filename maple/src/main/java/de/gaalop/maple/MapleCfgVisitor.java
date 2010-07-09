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
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
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
					if (!(branch instanceof BlockEndNode)) {
						replaceSuccessor(node, branch);
					}
					node.getPositive().accept(this);
				} else if (node.getNegative() == branch) {
					if (!(branch instanceof BlockEndNode)) {
						replaceSuccessor(node, branch);
					}
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

	private Log log = LogFactory.getLog(MapleCfgVisitor.class);

	private MapleEngine engine;

	private HashMap<String, String> oldMinVal;
	private HashMap<String, String> oldMaxVal;

	private Plugin plugin;
	
	private int branchDepth = 0;
	private boolean loopMode = false;
	private Map<String, String> rollbackValues = new HashMap<String, String>();
	private IfThenElseNode currentIf;
	private Map<Variable, Set<MultivectorComponent>> resetComponents;
	private Set<Variable> initialiedVariables = new HashSet<Variable>();

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
		if (loopMode || branchDepth > 0) {
			if (!(node.getSuccessor() instanceof StoreResultNode)) {
				StoreResultNode srn = new StoreResultNode(node.getGraph(), node.getVariable());
				node.insertAfter(srn);
			}
		}
		
		/*
		 * FIXME: special treatment in case it is a single line using a math function, which is not support. (Atm only abs is
		 * supported) We dont call Matlab for this, as it cannot handle these correct the mathfunction has to be on a single line,
		 * with a single var parameter like x = sqrt(y);
		 */
		if (node.getValue() instanceof MathFunctionCall) {
			MathFunction func = ((MathFunctionCall) (node.getValue())).getFunction();
			if ((func != MathFunction.ABS)) {
				node.getSuccessor().accept(this);
				return;
				// FIXME: previous assignments contributing to this statement might get lost
			}
		}
		// FIXME: Maple cannot compute things like sqrt(abs(VecN3(1,2,3)));
		
		if (branchDepth > 0) {
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
	
	private String getTempVarName(MultivectorComponent component) {
		// TODO: generate unused names!
		return component.getName() + "__" + component.getBladeIndex();
	}
	
	@Override
	public void visit(StoreResultNode node) {
		Variable variable = node.getValue();
		String evalResult = simplify(variable);
		log.debug("Maple simplification of " + variable + ": " + evalResult);

		ControlFlowGraph graph = node.getGraph();
		List<SequentialNode> newNodes = parseMapleCode(graph, evalResult);
		if (branchDepth > 0) {
			
			for (SequentialNode newNode : newNodes) {
				if (newNode instanceof AssignmentNode) {
					AssignmentNode assignment = (AssignmentNode) newNode;
					if (assignment.getVariable() instanceof MultivectorComponent) {
						MultivectorComponent comp = (MultivectorComponent) assignment.getVariable();
						Variable initVar = new Variable(getTempVarName(comp));
						if (!initialiedVariables.contains(initVar)) {
							initialiedVariables.add(initVar);
							AssignmentNode init = new AssignmentNode(graph, initVar, new FloatConstant(0));
							currentIf.insertBefore(init);
							graph.addTempVariable(initVar);
						}
						
						if (resetComponents.get(variable) == null) {
							resetComponents.put(variable, new HashSet<MultivectorComponent>());
						}
						resetComponents.get(variable).add(comp);
						
						AssignmentNode assign = new AssignmentNode(graph, initVar, assignment.getValue());
						node.insertBefore(assign);
					}
				} else {
					throw new IllegalStateException("unexpected node type: " + newNode);
				}
			}
			graph.removeNode(node);
			
		} else {
			for (SequentialNode newNode : newNodes) {
				node.insertBefore(newNode);
			}
		}

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		if (branchDepth == 0) {
			currentIf = node;
			initialiedVariables.clear();
		}
		// FIXME: how to handle variables in condition which might have been changed in previous branches...?
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
				// save current rollback values, in case a nested if-statement is found
				Map<String, String> previousRollback = new HashMap<String, String>();
				for (String s : rollbackValues.keySet()) {
					previousRollback.put(s, rollbackValues.get(s));
				}
				
				branchDepth++;
				resetComponents = new HashMap<Variable, Set<MultivectorComponent>>();
				node.getPositive().accept(this);
				rollback();

				node.getNegative().accept(this);
				resetVariables(node.getGraph());
				branchDepth--;

				rollbackValues = previousRollback;
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
	
	private void resetVariables(ControlFlowGraph graph) {
		for (Variable v : resetComponents.keySet()) {
			Set<MultivectorComponent> components = resetComponents.get(v);
			if (components == null || components.size() == 0) {
				throw new IllegalStateException("No components to reset for variable " + v);
			}
			Expression[] products = new Expression[components.size()];
			int i = 0;
			for (MultivectorComponent mc : components) {
				Variable coefficient = new Variable(getTempVarName(mc));
				Expression blade = graph.getBladeList()[mc.getBladeIndex()];
				Multiplication product = ExpressionFactory.product(coefficient, blade);
				products[i++] = product;
			}
			Expression sum;
			if (products.length > 1) {
				sum = ExpressionFactory.sum(products);
			} else {
				sum = products[0];
			}
			
			// insert a new (temporary) assignment behind OUTER if-statement in order to "reset" Maple binding to variable 
			AssignmentNode reset = new AssignmentNode(graph, v, sum);
			currentIf.insertAfter(reset);
						
//			MapleDfgVisitor visitor = new MapleDfgVisitor();
//			sum.accept(visitor);
//			String valueCode = visitor.getCode();
//						
//			String command = v.getName() + ":=" + valueCode + ";";
//			try {
//				engine.evaluate(command);
//			} catch (MapleEngineException e) {
//				throw new RuntimeException("Could not reset variable " + v, e);
//			}
		}
	}

	@Override
	public void visit(LoopNode node) {
		loopMode = true;
		node.getBody().accept(this);
		loopMode = true;
		
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
