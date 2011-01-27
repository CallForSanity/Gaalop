package de.gaalop.tablebased;

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

/**
 * This visitor creates code for Maple.
 */
public class TableBasedCfgVisitor implements ControlFlowVisitor {

	/**
	 * This visitor removes assignments to unused helper variables, e.g. resulting from inlined macros or loops.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
	private class RemoveUnusedAssignmentsVisitor extends EmptyControlFlowVisitor {

		private final Set<String> unusedNames = new HashSet<String>();

		public RemoveUnusedAssignmentsVisitor(Set<MultivectorComponent> components) {
			for (MultivectorComponent comp : components) {
				unusedNames.add(getTempVarName(comp));
			}
		}

		@Override
		public void visit(AssignmentNode node) {
			Node successor = node.getSuccessor();
			String name = node.getVariable().getName();
			if (unusedNames.contains(name)) {
				node.getGraph().removeNode(node);
				node.getGraph().removeScalarVariable(name);
			}
			successor.accept(this);
		}

	}

	/**
	 * This visitor removes empty nodes like {@link IfThenElseNode} from the graph. During loop unrolling, conditional
	 * statements could have become empty because of the removal of <code>break</code> statements.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
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

	/**
	 * This class checks variables for reuse after conditional statements or loops. The detection of these variables is
	 * necessary to trigger a forced optimization for assignments within an {@link IfThenElseNode} or {@link LoopNode}.
	 * For each assignment to a variable within a {@link Block} the current hierarchy of blocks is saved in a stack,
	 * representing the current scope. If the assigned variable is reused later, the scope of the variable reuse is
	 * compared with the last scope of the assignment. If the variable is identified to be reused, it is saved in the
	 * list of reused variables, which can be queried by {@link CheckDependencyVisitor#getDependentVariables()} after
	 * the graph has been traversed.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
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
	 * Simple visitor used to inline the relevant branch of an if-statement. The other branch is cut off and the
	 * original {@link IfThenElseNode} is removed from the graph.
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

	/**
	 * This visitor has to be called before a loop is processed by {@link TableBasedCfgVisitor}. It makes sure that assigned
	 * variables are initialized in front of the outermost loop. Although initialization is also performed in
	 * {@link TableBasedCfgVisitor#visit(AssignmentNode)}, this step is required to make sure variables are initialized with
	 * the full set of symbolic coefficients (linear combination) when they are reused within the loop. Otherwise, the
	 * structure of multivectors could change in the loop and previous assignments would only "see" parts of the
	 * non-zero coefficients. This is required because loops are executed more than one time and after the first
	 * iteration multivectors could consist of more components than before. This visitor guarantees that all
	 * coefficients are initialized right away.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
	private class InitializeVariablesInLoopVisitor extends EmptyControlFlowVisitor {

		private final LoopNode root;

		InitializeVariablesInLoopVisitor(LoopNode node) {
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
			// optimize current value of variable and add variables for coefficients in front of block
			initializeCoefficients(variable);
			// optimize value in a temporary variable and add missing initializations
			initializeMissingCoefficients(node);
			// reset Maple binding with linear combination of variables for coefficients
			resetVariable(variable);

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

	/**
	 * This visitor performs loop unrolling on-the-fly, i.e. every time a loop is visited which has the iterations
	 * property set to a value greater than zero. Unrolling means to copy the body of the loop as many times as
	 * specified by {@link LoopNode#getIterations()} and removing the corresponding {@link LoopNode} from the graph.
	 * Nested loops are not unrolled right away, instead they are copied like other statements, too. Unrolling of nested
	 * loops is performed in the context of {@link TableBasedCfgVisitor#visit(LoopNode)} when the inner loop is processed for
	 * optimization. The loop to be unrolled therefore has to be specified in the constructor.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
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
	 * This visitor reorders relational operations to a left-hand side expression that is compared to 0. Equalities and
	 * inequalities are processed by optimizing both sides of the comparison and replacing the condition by a
	 * component-by-component comparison of each coefficient.
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
			Variable condition = new Variable(CONDITION_PREFIX + conditionSuffix++);
			Expression newRight = new FloatConstant(0);
				String assignment = generateCode(condition) + ":=" + generateCode(lhs) + ";";
				//engine.evaluate(assignment);
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
			Variable lVar = new Variable(CONDITION_PREFIX + conditionSuffix++);
			Variable rVar = new Variable(CONDITION_PREFIX + conditionSuffix++);
			Variable diff = new Variable("__temp__");

				/*engine.evaluate(generateCode(lVar) + ":=" + generateCode(left) + ";");
				engine.evaluate(generateCode(rVar) + ":=" + generateCode(right) + ";");
				engine.evaluate(generateCode(diff) + ":=" + generateCode(difference) + ";");*/

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

	private Log log = LogFactory.getLog(TableBasedCfgVisitor.class);

	static final String CONDITION_PREFIX = "condition_";
	static int conditionSuffix = 0;
	private static final String suffix = "_opt";

	private HashMap<String, String> oldMinVal;
	private HashMap<String, String> oldMaxVal;

	Plugin plugin;

	/** Used to distinguish normal assignments and such from a loop or if-statement where GA must be eliminated. */
	private int blockDepth = 0;
	private boolean loopMode = false;
	/** Used to insert synthetic variables in front of this {@link IfThenElseNode} or {@link LoopNode} **/
	private SequentialNode currentRoot;

	/** Keeps track of which synthetic variables for multivector coefficients already have been initialized. **/
	Map<Variable, Set<MultivectorComponent>> initializedVariables = new HashMap<Variable, Set<MultivectorComponent>>();
	/** Used to detect unknown variables which would make an if-condition undecidable at compile-time. **/
	private final List<Variable> unknownVariables = new ArrayList<Variable>();
	/** Variables determined to be optimized in any case. **/
	final Set<Variable> optVariables = new HashSet<Variable>();
	/** Keeps track of synthetic variables which were initialized but can be removed afterwards. **/
	private final Set<MultivectorComponent> unusedInitializations = new HashSet<MultivectorComponent>();

	/** Current mapping of new variable names used for renaming in branches to avoid backtracking after then-part. **/
	private Map<Variable, Variable> newNames = new HashMap<Variable, Variable>();
	/** Suffix to be appended to temporary variables for renaming in branches. **/
	private static int tempSuffix = 1;

	ControlFlowGraph graph;

	public TableBasedCfgVisitor(Plugin plugin) {
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

	/**
	 * Initializes synthetic variables representing the current coefficients of the given variable.
	 * 
	 * @param variable Variable to be initialized in front of the current root.
	 */
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

	/**
	 * Helper method for {@link #initializeCoefficients(Variable)} to initialize a single coefficient.
	 * 
	 * @param variable Variable to be initialized.
	 * @param value Current value of variable.
	 * @param component Component to be assigned, resulting from previous optimization.
	 */
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
				checkUnusedInitialization(component);
			}
			initCoefficients.add(component);
		}
	}

	/**
	 * Initializes synthetic variables in front of the current root which have not been initialized before. This has to
	 * be done when a variable is reassigned in a branch, resulting in new multivector components.
	 * 
	 * @param node Assignment which potentially changes the structure of the assigned multivector.
	 */
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
				checkUnusedInitialization(originalComp);
			}
		}
	}

	/**
	 * Assigns new values to synthetic variables representing the multivector's coefficients. The given node is used as
	 * the basis in front of which these assignments are inserted into the graph.
	 * 
	 * @param base Basis in front of which to insert new assignments.
	 * @param variable Variable to be optimized.
	 */
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

	/**
	 * Resets synthetic variables to zero which are not part of the multivector anymore.
	 * 
	 * @param base Basis in front of which to insert the reset statements.
	 * @param variable Variable which has been reassigned.
	 * @param newValues Multivector components resulting from current optimization.
	 */
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

	/**
	 * Resets the Maple binding of the given variable to a linear combination of symbolic coefficients. Concrete
	 * coefficients are the synthetic variables which have been initialized in front of the current root and have been
	 * assigned in the current branch.
	 * 
	 * @param variable Variable to be reset as a linear combination using previously assigned synthetic variables.
	 */
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
			//engine.evaluate(codeBuffer.toString());
	}

	/**
	 * Generates the Maple equivalent code for the given expression.
	 * 
	 * @param expression Expression to be translated.
	 * @return the corresponding Maple code
	 */
	String generateCode(Expression expression) {
		TableBasedDfgVisitor visitor = new TableBasedDfgVisitor();
		expression.accept(visitor);
		return visitor.getCode();
	}

	/**
	 * Convenience method for {@link #generateTempVariable(Variable)}.
	 * 
	 * @param component
	 * @return the name of the corresponding synthetic variable
	 */
	String getTempVarName(MultivectorComponent component) {
		return getTempVarName(component.getName(), component.getBladeIndex());
	}

	/**
	 * Generates the name of the synthetic variable representing the coefficient at given index for the given variable.
	 * 
	 * @param variable Variable name.
	 * @param index Index of coefficient.
	 * @return <code>variable__index</code>
	 */
	String getTempVarName(String variable, int index) {
		// attention: must not collide with renamed variables from macro inlining
		return variable.replace('e', 'E').replace(suffix, "") + "__" + index;
	}

	/**
	 * Generates a temporary variable as a replacement from variables in a branch.
	 * 
	 * @param v original variable
	 * @return variable including __tmp__
	 */
	private Variable generateTempVariable(Variable v) {
		Variable newVariable = new Variable(v + "__tmp__" + tempSuffix++);
		return newVariable;
	}

	/**
	 * Checks if the initialization of a synthetic variable has to be kept in the code.
	 * 
	 * @param component Name and index of the synthetic variable to be checked.
	 */
	private void checkUnusedInitialization(MultivectorComponent component) {
		String variableName = component.getName().replace(suffix, "");
		if (!optVariables.contains(new Variable(variableName)) && !variableName.startsWith(CONDITION_PREFIX)) {
			unusedInitializations.add(component);
		}
	}

	@Override
	public void visit(ExpressionStatement node) {
		String command = generateCode(node.getExpression());
		command += ";\n";
			//engine.evaluate(command);
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

			//return engine.evaluate(codeBuffer.toString());
		return null;
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

		/*MapleLexer lexer = new MapleLexer(new ANTLRStringStream(mapleCode));
		MapleParser parser = new MapleParser(new CommonTokenStream(lexer));
		try {
			MapleParser.program_return result = parser.program();
			MapleTransformer transformer = new MapleTransformer(new CommonTreeNodeStream(result.getTree()));
			return transformer.script(graph, oldMinVal, oldMaxVal);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}*/
		
		return null;
	}

	@Override
	public void visit(IfThenElseNode node) {
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
				/*String result = engine.evaluate(evalb);
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
				}*/
		}
		if (unknown) {
			// condition contains unknown variables or evalb has failed to evaluate to true or false
			handleUnknownBranches(node, condition);
			node.getSuccessor().accept(this);
		}
	}

	/**
	 * Performs the "regular" handling of {@link IfThenElseNode} which cannot be inlined due to an undecidable
	 * condition.
	 * 
	 * @param node {@link IfThenElseNode} to be processed.
	 * @param condition Condition which has to be reordered and checked for GA.
	 */
	private void handleUnknownBranches(IfThenElseNode node, Expression condition) {
		if (blockDepth == 0) {
			currentRoot = node;
		}

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

			/*
			 * Initialize synthetic variables of assigned values in front of the current root. Additional to the normal
			 * initialization in visit(AssignmentNode), synthetic variables have to be present for every potential
			 * coefficient which might result from an assignment anywhere in the loop. This is necessary since the structure
			 * of multivectors can change in the loop body and the loop is executed more than one time (in contrast to 
			 * if-statements, which are processed only once).
			 */
			InitializeVariablesInLoopVisitor visitor = new InitializeVariablesInLoopVisitor(node);
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
		RemoveUnusedAssignmentsVisitor v1 = new RemoveUnusedAssignmentsVisitor(unusedInitializations);
		graph.accept(v1);
		RemoveEmptyNodesVisitor v2 = new RemoveEmptyNodesVisitor();
		graph.accept(v2);
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
