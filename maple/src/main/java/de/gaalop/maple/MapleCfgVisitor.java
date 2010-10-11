package de.gaalop.maple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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
import de.gaalop.cfg.ColorNode;
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
	
	private static class RemoveEmptyNodesVisitor extends EmptyControlFlowVisitor {
		
		public RemoveEmptyNodesVisitor() {
		}
		
		@Override
		public void visit(IfThenElseNode node) {
			Node successor = node.getSuccessor();
			SequentialNode positive = node.getPositive();
			SequentialNode negative = node.getNegative();
			if (positive instanceof BlockEndNode && negative instanceof BlockEndNode) {
				node.getGraph().removeNode(node);
			} else {
				positive.accept(this);
				negative.accept(this);
			}
			successor.accept(this);
		}
		
	}
	
	private static class CheckDependencyVisitor extends EmptyControlFlowVisitor {
		
		private enum Branch {
			POSITIVE, NEGATIVE, LOOP
		}
		
		private static class Block {
			
			private final SequentialNode node;
			private final Branch branch;

			public Block(IfThenElseNode node, Branch branch) {
				this.node = node;
				this.branch = branch;
			}
			
			public Block(LoopNode node) {
				this.node = node;
				this.branch = Branch.LOOP;
			}
			
			public SequentialNode getNode() {
				return node;
			}
			
			public Branch getBranch() {
				return branch;
			}
			
			@Override
			public boolean equals(Object obj) {
				if (obj instanceof Block) {
					Block block = (Block) obj;
					if (node == block.getNode() && branch == block.getBranch()) {
						return true;
					}
				}
				return false;
			}
			
			@Override
			public int hashCode() {
				return node.hashCode() + branch.hashCode();
			}
			
			@Override
			public String toString() {
				return "BLOCK[" + node + ":" + branch + "]";
			}
			
		}
		
		private Set<Variable> optVariables = new HashSet<Variable>();
		private Stack<Block> hierarchy = new Stack<Block>();
		private Map<Variable, List<Block>> currentStack = new HashMap<Variable, List<Block>>();
		private boolean loopMode = false;

		public CheckDependencyVisitor() {
		}
		
		public Set<Variable> getDependentVariables() {
			return optVariables;
		}
		
		@Override
		public void visit(IfThenElseNode node) {
			hierarchy.push(new Block(node, Branch.POSITIVE));
			node.getPositive().accept(this);
			hierarchy.pop();
			hierarchy.push(new Block(node, Branch.NEGATIVE));
			node.getNegative().accept(this);
			hierarchy.pop();
			
			node.getSuccessor().accept(this);
		}
		
		@Override
		public void visit(LoopNode node) {
			boolean oldLoopMode = loopMode;
			loopMode = true;
			hierarchy.push(new Block(node));
			node.getBody().accept(this);
			hierarchy.pop();
			loopMode = oldLoopMode;
			
			node.getSuccessor().accept(this);
		}
		
		@Override
		public void visit(AssignmentNode node) {
			Variable variable = node.getVariable();
			Set<Variable> usedVariables = checkVariables(node.getValue());
			boolean recursive = usedVariables.contains(variable);
			if (loopMode && recursive) {
				optVariables.add(variable);
			}
			if (!hierarchy.empty()) {
				currentStack.put(variable, new ArrayList<Block>(hierarchy));
			}
			node.getSuccessor().accept(this);
		}
		
		@Override
		public void visit(StoreResultNode node) {
			Variable variable = node.getValue();
			checkVariable(variable);
			node.getSuccessor().accept(this);
		}
		
		@Override
		public void visit(ExpressionStatement node) {
			checkVariables(node.getExpression());
			node.getSuccessor().accept(this);
		}

		private Set<Variable> checkVariables(Expression expression) {
			de.gaalop.UsedVariablesVisitor usedVariables = new de.gaalop.UsedVariablesVisitor();
			expression.accept(usedVariables);
			// check for common hierarchy
			for (Variable v : usedVariables.getVariables()) {
				checkVariable(v);
			}
			return usedVariables.getVariables();
		}

		private void checkVariable(Variable v) {
			if (checkHierarchy(v)) {
				optVariables.add(v);
			}
		}
		
		private boolean checkHierarchy(Variable v) {
			List<Block> vStack = currentStack.get(v);
			if (vStack == null) {
				return false;
			}
			List<Block> variableList = new ArrayList<Block>(vStack);
			List<Block> currentList = new ArrayList<Block>(hierarchy);
			int min = Math.min(variableList.size(), currentList.size());
			if (min == 0) {
				// found reuse in global space
				return true;
			}
			for (int i = 0; i < min; i++) {
				Block variableBlock = variableList.get(i);
				Block currentBlock = currentList.get(i);
				if (variableBlock.getNode() != currentBlock.getNode()) {
					return true;
				} else if (variableBlock.getBranch() != currentBlock.getBranch()) {
					// reuse in different branches of if-statement
					return false;
				}
			}
			if (variableList.size() > currentList.size()) {
				return true;
			}
			return false;
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


	private class InitializeRecursiveVariablesVisitor extends EmptyControlFlowVisitor {

		private final LoopNode root;

		InitializeRecursiveVariablesVisitor(LoopNode node) {
			this.root = node;
		}

		@Override
		public void visit(StartNode node) {
			throw new IllegalStateException("This visitor is intended to be called on loop nodes only.");
		}

		@Override
		public void visit(AssignmentNode node) {
			// optimize current value of variable and add variables for coefficients in front of block
			Variable variable = node.getVariable();
			Expression value = node.getValue();
			
			UsedVariablesVisitor visitor = new UsedVariablesVisitor();
			value.accept(visitor);
			if (visitor.getVariables().contains(variable) || optVariables.contains(variable)) {
				optVariables.add(variable);
				// optimize current value of variable and add variables for coefficients in front of block
				initializeCoefficients(variable);
				// optimize value in a temporary variable and add missing initializations
				initializeMissingCoefficients(node);
				// reset Maple binding with linear combination of variables for coefficients
				resetVariable(variable);				
			}			

			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(LoopNode node) {
			node.getBody().accept(this);
			if (node != root) {
				node.getSuccessor().accept(this);
			}
		}

	}

	private class UnrollLoopsVisitor extends EmptyControlFlowVisitor {

		private LoopNode root;
		SequentialNode firstNewNode;
		boolean showWarning;

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
			boolean endOfLoop = node.getSuccessor() instanceof BlockEndNode;
			boolean startOfLoop = false;
			for (Node pred : node.getPredecessors()) {
				if (pred instanceof LoopNode) {
					LoopNode loop = (LoopNode) pred;
					if (loop.getBody() == node) {
						startOfLoop = true;
					}
				}
			}
			if (!(endOfLoop || startOfLoop)) {
				showWarning = true;
			}
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
			Variable condition = new Variable("condition_" + conditionSuffix++);
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

		private boolean containsTrueOrFalse(Expression expression) {
			UsedVariablesVisitor visitor = new UsedVariablesVisitor();
			expression.accept(visitor);
			for (Variable v : visitor.getVariables()) {
				if ("true".equals(v.getName()) || "false".equals(v.getName())) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Compares equalities and inequalities component-wise. Both multivectors are optimized and subtracted to get
		 * the components which are different. For these components, a comparison is added to the condition.
		 * 
		 * @param node equality or inequality node
		 * @param equality whether to compare for equality or inequality (convenience to avoid instanceof checks)
		 */
		private void compareComponents(BinaryOperation node, boolean equality) {
			Expression left = node.getLeft();
			Expression right = node.getRight();
			
			if (containsTrueOrFalse(left) || containsTrueOrFalse(right)) {
				return;
			}

			Subtraction difference = ExpressionFactory.subtract(left.copy(), right.copy());
			Variable lVar = new Variable("condition_" + conditionSuffix++);
			Variable rVar = new Variable("condition_" + conditionSuffix++);
			Variable diff = new Variable("__temp__");

			try {
				engine.evaluate(generateCode(lVar) + ":=" + generateCode(left) + ";");
				engine.evaluate(generateCode(rVar) + ":=" + generateCode(right) + ";");
				engine.evaluate(generateCode(diff) + ":=" + generateCode(difference) + ";");

				initializeCoefficients(lVar);
				initializeCoefficients(rVar);
				Set<Integer> lVarCoeffs = new HashSet<Integer>();
				Set<Integer> rVarCoeffs = new HashSet<Integer>();
				for (MultivectorComponent comp : initializedVariables.get(lVar)) {
					lVarCoeffs.add(comp.getBladeIndex());
				}
				for (MultivectorComponent comp : initializedVariables.get(rVar)) {
					rVarCoeffs.add(comp.getBladeIndex());
				}

				BinaryOperation newCondition = null;
				for (AssignmentNode diffOpt : optimizeVariable(graph, diff)) {
					if (diffOpt.getVariable() instanceof MultivectorComponent) {
						MultivectorComponent comp = (MultivectorComponent) diffOpt.getVariable();
						int index = comp.getBladeIndex();
						Expression lVarNew = getNewExpression(lVar, lVarCoeffs, index);
						Expression rVarNew = getNewExpression(rVar, rVarCoeffs, index);
						
						BinaryOperation comparison;
						if (equality) {
							comparison = new Equality(lVarNew, rVarNew);
						} else {
							comparison = new Inequality(lVarNew, rVarNew);
						}
						if (newCondition == null) {
							newCondition = comparison;
						} else {
							newCondition = ExpressionFactory.and(newCondition, comparison);
						}
					}
				}
				root.replaceExpression(node, newCondition);
			} catch (MapleEngineException e) {
				throw new RuntimeException("Could not optimize (in)equality " + node + " in condition "
						+ root.getCondition(), e);
			}

		}

		private Expression getNewExpression(Variable var, Set<Integer> coeffs, int index) {
			Expression newExp;
			if (coeffs.contains(index)) {
				newExp = new Variable(getTempVarName(var.getName(), index));
			} else {
				newExp = new FloatConstant(0.0f);
			}
			return newExp;
		}

		@Override
		public void visit(Equality node) {
			compareComponents(node, true);
		}

		@Override
		public void visit(Inequality node) {
			compareComponents(node, false);
		}

		@Override
		public void visit(Relation node) {
			reorderToLeft(node);
		}

	}

	private Log log = LogFactory.getLog(MapleCfgVisitor.class);

	private static final String suffix = "_opt";
	static int conditionSuffix = 0;

	MapleEngine engine;

	private HashMap<String, String> oldMinVal;
	private HashMap<String, String> oldMaxVal;

	Plugin plugin;

	/** Used to distinguish normal assignments and such from a loop or if-statement where GA must be eliminated. */
	private int blockDepth = 0;
	private boolean loopMode = false;
	private SequentialNode currentRoot;

	Map<Variable, Set<MultivectorComponent>> initializedVariables = new HashMap<Variable, Set<MultivectorComponent>>();
	private final List<Variable> unknownVariables = new ArrayList<Variable>();
	final Set<Variable> optVariables = new HashSet<Variable>();
	
	private Map<Variable, Variable> newNames = new HashMap<Variable, Variable>();

	ControlFlowGraph graph;

	private static int tempSuffix = 1;

	public MapleCfgVisitor(MapleEngine engine, Plugin plugin) {
		this.engine = engine;
		this.plugin = plugin;
	}

	@Override
	public void visit(StartNode startNode) {
		graph = startNode.getGraph();
		
		CheckDependencyVisitor visitor = new CheckDependencyVisitor();
		graph.accept(visitor);
		optVariables.addAll(visitor.getDependentVariables());
		
		unknownVariables.addAll(graph.getInputVariables()); 
		plugin.notifyStart();
		startNode.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		Variable variable = node.getVariable();
		Expression value = node.getValue();
		Node successor = node.getSuccessor();

		UsedVariablesVisitor usedVariables = new UsedVariablesVisitor();
		value.accept(usedVariables);
		for (Variable var : usedVariables.getVariables()) {
			if (unknownVariables.contains(var)) {
				unknownVariables.add(variable);
				break;
			}
		}
		
		boolean optimize = optVariables.contains(variable);
		
		if (blockDepth > 0) {
			if (!optimize && successor instanceof StoreResultNode) {
				optimize = true;
			}
			// replace each variable with new name, if existing
			for (Variable var : usedVariables.getVariables()) {
				Variable newName = newNames.get(var);
				if (newName != null) {
					value.replaceExpression(var, newName);
				}
			}
			if (optimize) {
				// optimize current value of variable and add variables for coefficients in front of block
				initializeCoefficients(node.getVariable());
				// optimize value in a temporary variable and add missing initializations
				initializeMissingCoefficients(node);
				// reset Maple binding with linear combination of variables for coefficients
				resetVariable(variable);				
			} else {
				// create new variable name for this variable in current block
				Variable newVariable = generateTempVariable(variable);
				node.replaceExpression(variable, newVariable);
				// add new variable (lhs) to map for use in subsequent assignments 
				// -> prevent recursive assignments from producing wrong values
				newNames.put(variable, newVariable);
			}
			// update local variables with new expressions
			variable = node.getVariable();
			value = node.getValue();
		}
		
		// perform actual calculation
		assignVariable(variable, value);

		if (blockDepth > 0 && optimize) {
			// optimize new value and reset Maple binding with linear combination of new value
			assignCoefficients(node, variable);
		}

		// notify observers about progress (must be called before successor.accept(this))
		plugin.notifyProgress();
		graph.removeNode(node);
		successor.accept(this);
	}

	void initializeCoefficients(Variable variable) {
		List<AssignmentNode> coefficients = optimizeVariable(graph, variable);
		for (AssignmentNode coefficient : coefficients) {
			if (coefficient.getVariable() instanceof MultivectorComponent) {
				MultivectorComponent component = (MultivectorComponent) coefficient.getVariable();
				if (component.getBladeIndex() == 0 && coefficients.size() == 1) {
					// check that Maple result is not of type x := 'x'
					String optName = coefficient.getVariable().getName();
					Variable coeffVariable = new Variable(optName.substring(0, optName.lastIndexOf(suffix)));
					if (coeffVariable.equals(coefficient.getValue())) {
						coefficient.setValue(new FloatConstant(0));
						// throw new RuntimeException("Variable " + variable + " is not initialized in global space.");
					}
				}
				initializeCoefficient(variable, coefficient.getValue(), component);
			}
		}
	}

	private void initializeCoefficient(Variable variable, Expression value, MultivectorComponent component) {
		Variable tempVar = new Variable(getTempVarName(component));
		if (initializedVariables.get(variable) == null) {
			initializedVariables.put(variable, new HashSet<MultivectorComponent>());
		}
		Set<MultivectorComponent> initCoefficients = initializedVariables.get(variable);
		if (!initCoefficients.contains(component)) {
			graph.addScalarVariable(tempVar);
			AssignmentNode initialization = new AssignmentNode(graph, tempVar, value);
			if (!tempVar.equals(value)) {
				currentRoot.insertBefore(initialization);
			}
			initCoefficients.add(component);
		}
	}

	void initializeMissingCoefficients(AssignmentNode node) {
		Variable temp = new Variable("__temp__");
		assignVariable(temp, node.getValue());
		List<MultivectorComponent> coefficients = getComponents(optimizeVariable(graph, temp));
		for (MultivectorComponent coefficient : coefficients) {
			Variable originalVariable = node.getVariable();
			String optVarName = originalVariable.getName() + suffix;
			MultivectorComponent originalComp = new MultivectorComponent(optVarName, coefficient.getBladeIndex());
			Variable tempVar = new Variable(getTempVarName(originalComp));
			if (initializedVariables.get(originalVariable) == null) {
				initializedVariables.put(originalVariable, new HashSet<MultivectorComponent>());
			}
			Set<MultivectorComponent> initCoefficients = initializedVariables.get(originalVariable);
			if (!initCoefficients.contains(originalComp)) {
				graph.addScalarVariable(tempVar);
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
					base.insertBefore(newAssignment);
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
			base.insertBefore(resetToZero);
		}
	}

	void resetVariable(Variable variable) {
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

	String generateCode(Expression expression) {
		MapleDfgVisitor visitor = new MapleDfgVisitor();
		expression.accept(visitor);
		return visitor.getCode();
	}

	private String getTempVarName(MultivectorComponent component) {
		return getTempVarName(component.getName(), component.getBladeIndex());
	}

	String getTempVarName(String variable, int index) {
		return variable.replace('e', 'E').replace(suffix, "") + "__" + index;
	}
	
	/**
	 * Generates a temporary variable as a replacement from variables in a branch.
	 * 
	 * @param v original variable
	 * @return variable including __tmp__
	 */
	private Variable generateTempVariable(Variable v) {
		return new Variable(v + "__tmp__" + tempSuffix++);
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
		for (AssignmentNode newNode : newNodes) {
			node.insertBefore(newNode);
		}
		if (blockDepth > 0) {
			// do not reuse coefficients in form v[i] but with helper variables, e.g. v__0
			resetVariable(node.getValue());
		}
		node.getSuccessor().accept(this);
	}

	/**
	 * Simplifies the given variable and parses the Maple code to return the new nodes.
	 */
	List<AssignmentNode> optimizeVariable(ControlFlowGraph graph, Variable v) {
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
	String simplify(Expression expression) {
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
	List<AssignmentNode> parseMapleCode(ControlFlowGraph graph, String mapleCode) {
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
		
		UsedVariablesVisitor usedVariables = new UsedVariablesVisitor();
		condition.accept(usedVariables);
		boolean unknown = false;
		for (Variable var : usedVariables.getVariables()) {
			if (unknownVariables.contains(var)) {
				unknown = true;
				break;
			}
		}
		for (Variable var : usedVariables.getVariables()) {
			Variable newName = newNames.get(var);
			if (newName != null) {
				condition.replaceExpression(var, newName);
			}
		}
		if (loopMode) {
			unknown = true;
		}
		if (!unknown) {
			// try to evaluate condition to true or false
			String evalb = "evalb(" + generateCode(condition) + ");";
			try {
				String result = engine.evaluate(evalb);
				log.debug("evalb result for condition " + condition + ": " + result);
				if ("true\n".equals(result)) {
					InlineBlockVisitor inliner = new InlineBlockVisitor(node, node.getPositive());
					node.accept(inliner);
					node.getPositive().accept(this);
				} else if ("false\n".equals(result)) {
					InlineBlockVisitor inliner = new InlineBlockVisitor(node, node.getNegative());
					node.accept(inliner);			
					node.getNegative().accept(this);
				} else {
					unknown = true;
				}
			} catch (MapleEngineException e) {
				e.printStackTrace();
				unknown = true;
			}
		}
		if (unknown) {
			// condition contains unknown variables or evalb has failed to evaluate to true or false
			handleUnknownBranches(node, condition);			
			node.getSuccessor().accept(this);
		}		
	}

	private void handleUnknownBranches(IfThenElseNode node, Expression condition) {
		ReorderConditionVisitor reorder = new ReorderConditionVisitor(node);
		condition.accept(reorder);

		blockDepth++;
		Map<Variable, Variable> currentMapping = new HashMap<Variable, Variable>(newNames);
		node.getPositive().accept(this);
		// restore previous mapping for variables
		newNames = currentMapping;
		
		node.getNegative().accept(this);
		// restore previous mapping for variables
		newNames = currentMapping;
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
			if (ulv.showWarning) {
				Notifications.addWarning("Make sure that termination conditions for unroll-loops are "
						+ "always at beginning or end of a loop.\n"
						+ "Otherwise, partial loop bodies cannot be unrolled.");
			}
			ulv.firstNewNode.accept(this);
		} else {
			if (blockDepth == 0) {
				currentRoot = node;
			}
			InitializeRecursiveVariablesVisitor visitor = new InitializeRecursiveVariablesVisitor(node);
			node.accept(visitor);
			
			blockDepth++;
			boolean oldMode = loopMode;
			loopMode = true;
			Map<Variable, Variable> currentMapping = new HashMap<Variable, Variable>(newNames);
			node.getBody().accept(this);
			// restore previous mapping for variables
			newNames = currentMapping;
			loopMode = oldMode;
			blockDepth--;
			
			if (blockDepth == 0) {
				initializedVariables.clear();
			}
			node.getSuccessor().accept(this);
		}
	}

	@Override
	public void visit(BreakNode node) {
		Node successor = node.getSuccessor();
		if (!loopMode) {
			node.getGraph().removeNode(node);
		}
		successor.accept(this);
	}

	@Override
	public void visit(BlockEndNode node) {
		// nothing to do
	}

	@Override
	public void visit(EndNode endNode) {
		RemoveEmptyNodesVisitor visitor = new RemoveEmptyNodesVisitor();
		graph.accept(visitor);
	}

	@Override
	public void visit(Macro node) {
		throw new IllegalStateException("Macros should have been inlined.");
	}

	@Override
	public void visit(ColorNode node) {
		node.getSuccessor().accept(this);
	}
}
