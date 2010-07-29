package de.gaalop.maple;

import java.util.ArrayList;
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
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Inequality;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Subtraction;
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

	private static class UnrollLoopsVisitor extends EmptyControlFlowVisitor {

		private LoopNode root;
		SequentialNode firstNewNode;

		public UnrollLoopsVisitor(LoopNode root) {
			this.root = root;
		}

		/**
		 * Inserts a new node before the current node.
		 * 
		 * @param newNode node to be inserted before the current node
		 */
		private void insertNewNode(SequentialNode newNode) {
			if (firstNewNode == null) {
				firstNewNode = newNode;
			}
			root.insertBefore(newNode);
		}

		@Override
		public void visit(StartNode node) {
			throw new IllegalStateException("This visitor should be invoked on a loop node only.");
		}

		@Override
		public void visit(AssignmentNode node) {
			insertNewNode(node.copy());
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(StoreResultNode node) {
			insertNewNode(node.copy());
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(LoopNode node) {
			if (node == root) {
				for (int i = 0; i < node.getIterations(); i++) {
					node.getBody().accept(this);
				}
				node.getGraph().removeNode(node);
			} else {
				insertNewNode(node.copy());
				// ignore nested loops (process them later)
				node.getSuccessor().accept(this);
			}
			// do not visit root's successor
		}

		@Override
		public void visit(IfThenElseNode node) {
			IfThenElseNode newNode = (IfThenElseNode) node.copy();
			insertNewNode(newNode);

			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(ExpressionStatement node) {
			insertNewNode(node.copy());
			node.getSuccessor().accept(this);
		}
	}

	/**
	 * This visitor re-orders compare operations like >, <= or == to a left-hand side expression that is compared to 0.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
	private class ReorderConditionVisitor extends EmptyExpressionVisitor {

		private IfThenElseNode root;

		public ReorderConditionVisitor(IfThenElseNode root) {
			this.root = root;
		}

		private void reorderToLeft(BinaryOperation node) {
			Expression left = node.getLeft();
			Expression right = node.getRight();
			Subtraction lhs = ExpressionFactory.subtract(left.copy(), right.copy());
			Variable condition = new Variable("condition_");
			Expression newRight = new FloatConstant(0);
			try {
				String assignment = generateCode(condition) + ":=" + generateCode(lhs) + ";";
				engine.evaluate(assignment);
				String opt = simplify(condition);
				List<AssignmentNode> newNodes = parseMapleCode(graph, opt);
				graph.addScalarVariable(condition);
				for (AssignmentNode newAssignment : newNodes) {
					if (newAssignment.getVariable() instanceof MultivectorComponent) {
						MultivectorComponent mc = (MultivectorComponent) newAssignment.getVariable();
						if (mc.getBladeIndex() == 0) {
							AssignmentNode scalarPart = new AssignmentNode(graph, condition, newAssignment.getValue());
							root.insertBefore(scalarPart);
						} else {
							throw new IllegalArgumentException("Condition in if-statement '" + root.getCondition()
									+ "' is not scalar and cannot be evaluated.");
						}
					}
				}
			} catch (MapleEngineException e) {
				throw new RuntimeException("Unable to optimize condition " + lhs + " in Maple.", e);
			}

			node.replaceExpression(left, condition);
			node.replaceExpression(right, newRight);

			node.getLeft().accept(this);
		}

		@Override
		public void visit(Equality node) {
			reorderToLeft(node);
		}

		@Override
		public void visit(Inequality node) {
			reorderToLeft(node);
		}

		@Override
		public void visit(Relation node) {
			reorderToLeft(node);
		}

	}

	/**
	 * Simple helper visitor used to inline parts of conditional statements.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
	private class InlineBlockVisitor extends EmptyControlFlowVisitor {

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
				graph.removeNode(node);
			}
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(BlockEndNode node) {
			// this relies on the fact that nested statements are being ignored in visit(IfThenElseNode),
			// otherwise successor could be the wrong one
			if (node.getBase() == root) {
				replaceSuccessor(node, successor);
			}
		}

	}

	private Log log = LogFactory.getLog(MapleCfgVisitor.class);

	private MapleEngine engine;

	private HashMap<String, String> oldMinVal;
	private HashMap<String, String> oldMaxVal;

	private Plugin plugin;

	/** Used to distinguish normal assignments and such from a loop or if-statement where GA must be eliminated. */
	private int blockDepth = 0;
	private SequentialNode currentRoot;

	private Map<Variable, Set<MultivectorComponent>> initializedVariables = new HashMap<Variable, Set<MultivectorComponent>>();

	private ControlFlowGraph graph;

	public MapleCfgVisitor(MapleEngine engine, Plugin plugin) {
		this.engine = engine;
		this.plugin = plugin;
	}

	@Override
	public void visit(StartNode startNode) {
		graph = startNode.getGraph();
		plugin.notifyStart();
		startNode.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		Variable variable = node.getVariable();
		Expression value = node.getValue();
		Node successor = node.getSuccessor();

		if (graph.getIgnoreVariables().contains(variable)) {
			// do not process this assignment
			successor.accept(this);
			return;
		}

		if (blockDepth > 0) {
			// optimize current value of variable and add variables for coefficients in front of block
			initializeCoefficients(node.getVariable());
			// optimize value in a temporary variable and add missing initializations
			initializeMissingCoefficients(node);
			// reset Maple binding with linear combination of variables for coefficients
			resetVariable(variable);
		}

		// perform actual calculation
		assignVariable(variable, value);

		if (blockDepth > 0) {
			// optimize new value and reset Maple binding with linear combination of new value
			assignCoefficients(node, variable);
		}

		// notify observers about progress (must be called before successor.accept(this))
		plugin.notifyProgress();
		graph.removeNode(node);
		successor.accept(this);
	}

	private void initializeCoefficients(Variable variable) {
		List<AssignmentNode> coefficients = optimizeVariable(graph, variable);
		for (AssignmentNode coefficient : coefficients) {
			if (coefficient.getVariable() instanceof MultivectorComponent) {
				MultivectorComponent component = (MultivectorComponent) coefficient.getVariable();
				if (component.getBladeIndex() == 0 && coefficients.size() == 1) {
					// check that Maple result is not of type x := 'x'
					String optName = coefficient.getVariable().getName();
					Variable coeffVariable = new Variable(optName.substring(0, optName.lastIndexOf("_opt")));
					if (coeffVariable.equals(coefficient.getValue())) {
						coefficient.setValue(new FloatConstant(0));
					}
				}
				Variable tempVar = new Variable(getTempVarName(component));
				if (initializedVariables.get(variable) == null) {
					initializedVariables.put(variable, new HashSet<MultivectorComponent>());
				}
				Set<MultivectorComponent> initCoefficients = initializedVariables.get(variable);
				if (!initCoefficients.contains(component)) {
					AssignmentNode initialization = new AssignmentNode(graph, tempVar, coefficient.getValue());
					currentRoot.insertBefore(initialization);
					initCoefficients.add(component);
				}
			}
		}
	}

	private void initializeMissingCoefficients(AssignmentNode node) {
		Variable temp = new Variable("__temp__");
		assignVariable(temp, node.getValue());
		List<MultivectorComponent> coefficients = getComponents(optimizeVariable(graph, temp));
		for (MultivectorComponent coefficient : coefficients) {
			Variable originalVariable = node.getVariable();
			String optVarName = originalVariable.getName() + "_opt";
			MultivectorComponent originalComp = new MultivectorComponent(optVarName, coefficient.getBladeIndex());
			Variable tempVar = new Variable(getTempVarName(originalComp));
			if (initializedVariables.get(originalVariable) == null) {
				initializedVariables.put(originalVariable, new HashSet<MultivectorComponent>());
			}
			Set<MultivectorComponent> initCoefficients = initializedVariables.get(originalVariable);
			if (!initCoefficients.contains(originalComp)) {
				AssignmentNode initialization = new AssignmentNode(graph, tempVar, new FloatConstant(0));
				currentRoot.insertBefore(initialization);
				initCoefficients.add(originalComp);
			}
		}
	}

	private void assignCoefficients(AssignmentNode base, Variable variable) {
		List<AssignmentNode> coefficients = optimizeVariable(graph, variable);
		List<MultivectorComponent> newValues = new ArrayList<MultivectorComponent>();
		for (AssignmentNode coefficient : coefficients) {
			if (coefficient.getVariable() instanceof MultivectorComponent) {
				MultivectorComponent mc = (MultivectorComponent) coefficient.getVariable();
				newValues.add(mc);
				Variable newVariable = new Variable(getTempVarName(mc));
				Expression newValue = coefficient.getValue();
				if (!newVariable.equals(newValue)) {
					AssignmentNode newAssignment = new AssignmentNode(graph, newVariable, newValue);
					base.insertAfter(newAssignment);
				}
			}
		}
		resetZeroCoefficients(base, variable, newValues);
		resetVariable(variable);
	}

	private void resetZeroCoefficients(AssignmentNode base, Variable variable, List<MultivectorComponent> newValues) {
		List<MultivectorComponent> zeroCoefficients = new ArrayList<MultivectorComponent>();
		for (MultivectorComponent initCoeff : initializedVariables.get(variable)) {
			if (!newValues.contains(initCoeff)) {
				// this component is not part of the multivector anymore
				zeroCoefficients.add(initCoeff);
			}
		}
		for (MultivectorComponent zeroCoeff : zeroCoefficients) {
			Variable newVariable = new Variable(getTempVarName(zeroCoeff));
			AssignmentNode resetToZero = new AssignmentNode(graph, newVariable, new FloatConstant(0));
			base.insertAfter(resetToZero);
		}
	}

	private void resetVariable(Variable variable) {
		Set<MultivectorComponent> components = initializedVariables.get(variable);
		if (components == null || components.size() == 0) {
			throw new IllegalStateException("No components to reset for variable " + variable);
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

		assignVariable(variable, sum);
	}

	/**
	 * Extracts the {@link MultivectorComponent}s from a list of coefficients.
	 * 
	 * @param coefficients nodes from the Maple parser
	 * @return list of multivector components
	 */
	private List<MultivectorComponent> getComponents(List<AssignmentNode> coefficients) {
		List<MultivectorComponent> components = new ArrayList<MultivectorComponent>();
		for (AssignmentNode coefficient : coefficients) {
			if (coefficient.getVariable() instanceof MultivectorComponent) {
				components.add((MultivectorComponent) coefficient.getVariable());
			}
		}
		return components;
	}

	/**
	 * Translates the given variable and value to Maple syntax and executes it.
	 * 
	 * @param variable
	 * @param value
	 */
	private void assignVariable(Variable variable, Expression value) {
		String variableCode = generateCode(variable);
		StringBuilder codeBuffer = new StringBuilder();
		codeBuffer.append(variableCode);
		codeBuffer.append(" := ");
		codeBuffer.append(generateCode(value));
		codeBuffer.append(";\n");

		try {
			engine.evaluate(codeBuffer.toString());
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to process assignment " + variable + " := " + value + " in Maple.", e);
		}
	}

	private String generateCode(Expression expression) {
		MapleDfgVisitor visitor = new MapleDfgVisitor();
		expression.accept(visitor);
		return visitor.getCode();
	}

	private String getTempVarName(MultivectorComponent component) {
		return component.getName() + "__" + component.getBladeIndex();
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
		graph.removeNode(node);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(StoreResultNode node) {
		List<AssignmentNode> newNodes = optimizeVariable(graph, node.getValue());
		for (SequentialNode newNode : newNodes) {
			node.insertBefore(newNode);
		}
		node.getSuccessor().accept(this);
	}

	/**
	 * Simplifies the given variable and parses the Maple code to return the new nodes.
	 */
	private List<AssignmentNode> optimizeVariable(ControlFlowGraph graph, Variable v) {
		String simplification = simplify(v);
		log.debug("Maple simplification of " + v + ": " + simplification);
		return parseMapleCode(graph, simplification);
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
			throw new RuntimeException("Unable to apply gaalop() function on expression " + expression + " in Maple.",
					e);
		}
	}

	/**
	 * Parses a snippet of maple code and returns a list of CFG nodes that implement the returned maple expressions.
	 * 
	 * @param graph The control flow graph the new nodes should be created in.
	 * @param mapleCode The code returned by Maple.
	 * @return A list of control flow nodes modeling the returned code.
	 */
	private List<AssignmentNode> parseMapleCode(ControlFlowGraph graph, String mapleCode) {
		oldMinVal = new HashMap<String, String>();
		oldMaxVal = new HashMap<String, String>();

		/* fill the Maps with the min and maxvalues from the nodes */
		for (Variable v : graph.getInputVariables()) {
			if (v.getMinValue() != null)
				oldMinVal.put(v.getName(), v.getMinValue());
			if (v.getMaxValue() != null)
				oldMaxVal.put(v.getName(), v.getMaxValue());
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

	@Override
	public void visit(IfThenElseNode node) {
		if (blockDepth == 0) {
			currentRoot = node;
		}
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
				unknown = inlineIfBranch(node, condition);
			}
			if (unknown) {
				handleUnknownBranches(node, condition);
				node.getSuccessor().accept(this);
			}
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to check condition " + condition + " in if-statement " + node, e);
		}
	}

	private boolean inlineIfBranch(IfThenElseNode node, Expression condition) throws MapleEngineException {
		boolean unknown = false;
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
			if (node.getNegative() instanceof BlockEndNode) {
				node.getSuccessor().accept(this);
			} else {
				node.getNegative().accept(this);
			}
		} else {
			// reset unknown status in order to process branches
			Notifications.addWarning("Could not evaluate condition " + condition);
			unknown = true;
		}
		return unknown;
	}

	private void handleUnknownBranches(IfThenElseNode node, Expression condition) {
		ReorderConditionVisitor reorder = new ReorderConditionVisitor(node);
		condition.accept(reorder);

		blockDepth++;
		node.getPositive().accept(this);
		node.getNegative().accept(this);
		blockDepth--;

		if (blockDepth == 0) {
			initializedVariables.clear();
		}
	}

	@Override
	public void visit(LoopNode node) {
		if (node.getIterations() > 0) {
			UnrollLoopsVisitor ulv = new UnrollLoopsVisitor(node);
			node.accept(ulv);
			ulv.firstNewNode.accept(this);
		} else {
			if (blockDepth == 0) {
				currentRoot = node;
			}

			Variable counterVariable = node.getCounterVariable();
			if (counterVariable != null) {
				Notifications.addWarning("Assignments to counter variable " + counterVariable
						+ " are not processed by Maple.");
			}

			blockDepth++;
			node.getBody().accept(this);
			blockDepth--;

			if (blockDepth == 0) {
				initializedVariables.clear();
			}
			node.getSuccessor().accept(this);
		}
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

	@Override
	public void visit(Macro node) {
		throw new IllegalStateException("Macros should have been inlined.");
	}
}
