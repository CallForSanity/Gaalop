package de.gaalop.clucalc.input;

import de.gaalop.CheckGAVisitor;
import de.gaalop.UsedVariablesVisitor;
import de.gaalop.cfg.*;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.Variable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is a utility class used by the CluCalcTransformer to build a control flow graph while parsing the CluCalc AST.
 */
public final class GraphBuilder {

	private class SetLocalAndInputVariables implements ControlFlowVisitor {

		SetLocalAndInputVariables() {
			// empty non-private constructor (prevent synthetic accessors)
		}

		/**
		 * Searches the expression for variable references. If an undeclared reference is found, it is added to the
		 * input variables of the graph.
		 * 
		 * @param expression The expression to search in.
		 * @param inMacro
		 */
		private void findUndeclaredVariables(Expression expression) {
			UsedVariablesVisitor visitor = new UsedVariablesVisitor();
			expression.accept(visitor);
			for (Variable usedVariable : visitor.getVariables()) {
				checkIllegalVariable(usedVariable);
				if (!graph.getLocalVariables().contains(usedVariable)) {
					// in case we have pragmas giving ranges for the variable, add them
					if (graph.getPragmaMinValue().containsKey(usedVariable.getName())) {
						usedVariable.setMinValue(graph.getPragmaMinValue().get(usedVariable.getName()));
					}
					if (graph.getPragmaMaxValue().containsKey(usedVariable.getName())) {
						usedVariable.setMaxValue(graph.getPragmaMaxValue().get(usedVariable.getName()));
					}
					graph.addInputVariable(usedVariable);
				}
			}
		}

		@Override
		public void visit(StartNode node) {
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(AssignmentNode node) {
			findUndeclaredVariables(node.getValue());
			graph.addLocalVariable(node.getVariable());
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(StoreResultNode node) {
			findUndeclaredVariables(node.getValue());
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(IfThenElseNode node) {
			findUndeclaredVariables(node.getCondition());
			node.getPositive().accept(this);
			node.getNegative().accept(this);
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(BlockEndNode node) {
		}

		@Override
		public void visit(LoopNode node) {
			node.getBody().accept(this);
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(BreakNode node) {
		}

		@Override
		public void visit(Macro node) {
			// ignore body of macro
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(ExpressionStatement node) {
			findUndeclaredVariables(node.getExpression());
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(EndNode node) {
		}

		@Override
		public void visit(ColorNode node) {
			node.getSuccessor().accept(this);
		}

	}
	
	private final Map<String, ColorNode> COLORS;

	private static final Map<String, String> illegalNames;

	static {
		illegalNames = new HashMap<String, String>();
//		illegalNames.put("condition_", "synthetic variable inserted by Gaalop");
		illegalNames.put("length", "protected in Maxima");
	}
        
	final ControlFlowGraph graph;

	private SequentialNode lastNode;

	private CluCalcFileHeader header;

	private boolean setMode = false;

	private Set<String> macros = new HashSet<String>();
	private String currentMacroDefinition;

	private VariableScope currentScope = VariableScope.GLOBAL;

	public void beginNewScope() {
		currentScope = new VariableScope(currentScope);
	}

	public void endNewScope() {
		currentScope = currentScope.getParent();
	}

	public GraphBuilder() {
		graph = new ControlFlowGraph();
		lastNode = graph.getStartNode();
		
		{
			COLORS = new HashMap<String, ColorNode>();
			COLORS.put("Black", getRBGColor(0, 0, 0));
			COLORS.put("Blue", getRBGColor(0, 0, 1));
			COLORS.put("Cyan", getRBGColor(0, 1, 1));
			COLORS.put("Green", getRBGColor(0, 1, 0));
			COLORS.put("Magenta", getRBGColor(1, 0, 1));
			COLORS.put("Orange", getRBGColor(1, 0.7f, 0));
			COLORS.put("Red", getRBGColor(1, 0, 0));
			COLORS.put("White", getRBGColor(1, 1, 1));
			COLORS.put("Yellow", getRBGColor(1, 1, 0));
		}
                // Get annotation for graphs start node
		header = CluCalcFileHeader.get(graph.getStartNode());
		if (header == null) {
			header = new CluCalcFileHeader(graph.getStartNode());
		}
	}

	/**
	 * Adds a variable name to the control flow graph as Pragma output marked.
	 * 
	 * @param variable The variable
	 */
	public void addPragmaOutputVariable(String variable) {
            graph.addPragmaOutputVariable(variable);
	}

	/**
	 * Adds a pragma hint for a variable, which defines value range for it. The pragma must be set before the variable
	 * is added to the input variables, i.e. the pragma must appear for the use of the variable
	 */
	public void addPragmaMinMaxValues(String variable, String min, String max) {
		graph.addPragmaMinMaxValues(variable, min, max);
	}

	/**
	 * Adds a node to the end of the graph.
	 * 
	 * @param node The node that should be added.
	 */
	private void addNode(SequentialNode node) {
		lastNode.insertAfter(node);
		lastNode = node;
	}

	public ControlFlowGraph getGraph() {
		return graph;
	}
        
	/**
	 * Add an assignment node to the end of this graph.
	 * 
	 * @param variable The variable that is assigned to.
	 * @param expression The expression that is assigned to the variable.
	 */
	public AssignmentNode handleAssignment(Variable variable, Expression expression) {
		checkIllegalVariable(variable);

		CheckGAVisitor gaVisitor = new CheckGAVisitor();
		expression.accept(gaVisitor);
		if (gaVisitor.isGA()) {
			gaVisitor.addGAVariable(variable);
		}
		AssignmentNode assignment = new AssignmentNode(graph, variable, expression);
		addNode(assignment);
		return assignment;
	}

	void checkIllegalVariable(Variable variable) {
		String name = variable.getName();
		String reason = illegalNames.get(name);
		if (reason != null) {
			throw new IllegalArgumentException("Illegal variable name '" + name + "' (" + reason + ")."
					+ " Please use another variable.");
		}
	}

	/**
	 * Sets the null space for the CLUCalc header object.
	 * 
	 * @param ns null space to be set (IPNS / OPNS)
	 */
	public void handleNullSpace(NullSpace ns) {
		header.setNullSpace(ns);
	}

	/**
	 * Handle the CluCalc print operator '?'.
	 * 
	 * This only works for variables.
	 * 
	 * @param variable The variable that should be printed.
	 */
	public StoreResultNode handlePrint(Expression variable) {
		if (variable instanceof Variable) {
			StoreResultNode storeResult = new StoreResultNode(graph, (Variable) variable);
			addNode(storeResult);
			return storeResult;
		} else {
			throw new IllegalArgumentException("Only variables can be marked for optimization.");
		}
	}

	/**
	 * Handles an if statement. The statement consists of a condition expression, a mandatory then part (block) and an
	 * optional else part.
	 * 
	 * @param condition condition expression
	 * @param then_part list of statements belonging to then part
	 * @param else_part optional list of statements belonging to else part (empty list in case of no else part)
	 * @return new {@link IfThenElseNode} representing this statement.
	 */
	public IfThenElseNode handleIfStatement(Expression condition, List<SequentialNode> then_part,
			List<SequentialNode> else_part) {
		IfThenElseNode ifthenelse = new IfThenElseNode(graph, condition);
		addNode(ifthenelse);

		rewireNodes(then_part, ifthenelse);
		rewireNodes(else_part, ifthenelse);

		ifthenelse.setPositive(then_part.get(0));
		ifthenelse.setNegative((else_part != null && else_part.size() > 0) ? else_part.get(0) : new BlockEndNode(graph,
				ifthenelse));

		return ifthenelse;
	}

	/**
	 * Handles a loop statement. A CluCalc loop consists of a body of statements, typically containing the break
	 * keyword.
	 * 
	 * @param body list of statements belonging to body
	 * @param iterations (optional) number of iterations for loop unrolling
	 * @return new {@link LoopNode} representing this statement.
	 */
	public LoopNode handleLoop(List<SequentialNode> body, String iterations) {
		LoopNode loop = new LoopNode(graph);
		addNode(loop);
		rewireNodes(body, loop);
		loop.setBody((body != null && body.size() > 0) ? body.get(0) : new BlockEndNode(graph, loop));

		// save number of iterations and reset value for next loops
		if (iterations != null) {
			loop.setIterations(Integer.parseInt(iterations));
		}

		return loop;
	}

	/**
	 * Handles the break keyword in a loop statement.
	 * 
	 * @see #handleLoop(List)
	 * @return new {@link BreakNode} representing this statement.
	 */
	public BreakNode handleBreak() {
		BreakNode brk = new BreakNode(graph);
		addNode(brk);
		return brk;
	}
	
	public void handleSlider(Variable var, String label, double min, double max, double step, double init) {
		Slider slider = new Slider(var, label, min, max, step, init);
		graph.addInputVariable(var);
		graph.addSlider(slider);
	}
	
	public ColorNode handleColor(List<Expression> args) {
		ColorNode color = getRGBAColor(args);
		addNode(color);
		return color;
	}
	
	public ColorNode handleColor(String name) {
		ColorNode color = (ColorNode) COLORS.get(name).copy();
		if (color == null) {
			throw new IllegalArgumentException("Color " + name + " is not known.");
		}
		addNode(color);
		return color;
	}
	
	public void handleBGColor(List<Expression> args) {
		graph.setBGColor(getRGBAColor(args));
	}

	private ColorNode getRGBAColor(List<Expression> args) {
		if (args.size() < 3) {
			throw new IllegalArgumentException("Argument must have 3 values for R, G and B");
		}
		Expression r = args.get(0);
		Expression g = args.get(1);
		Expression b = args.get(2);
		Expression alpha = (args.size() == 4) ? args.get(3) : null; 
		ColorNode color;
		if (alpha == null) {
			color = new ColorNode(graph, r, g, b);
		} else {
			color = new ColorNode(graph, r, g, b, alpha);
		}
		return color;
	}

	private ColorNode getRBGColor(double r, double g, double b) {
		return new ColorNode(graph, new FloatConstant(r), new FloatConstant(g), new FloatConstant(b));
	}

	public Macro handleMacroDefinition(String id, List<SequentialNode> body, Expression ret) {
		// reset current macro name to allow further calls
		currentMacroDefinition = "";
		Macro macro = new Macro(graph, id, body, ret);
		addNode(macro);
		graph.addMacro(macro);

		//rewireNodes(body, macro);

		return macro;
	}

	public void addMacroName(String name) {
		macros.add(name);
		currentMacroDefinition = name;
	}

	/**
	 * Removes the nodes given by <code>list</code> from the control flow graph and rewires them to be a sequence of
	 * separate nodes, e.g. in the body of an if-statement. The first node has <code>base</code> as predecessor. For the
	 * last node in the list, base's successor will be set as successor, e.g. the first statement after an if-then-else
	 * statement.
	 * 
	 * @param list list of nodes from a block
	 * @param base basis of block, e.g. an if-statement
	 */
	private void rewireNodes(List<SequentialNode> list, SequentialNode base) {
		if (list == null || list.size() == 0) {
			return;
		}
		Iterator<SequentialNode> it = list.iterator();
		SequentialNode current = it.next();
		graph.removeNode(current);
		current.addPredecessor(base); // first node has if-then-else node as predecessor
		while (it.hasNext()) {
			SequentialNode next = it.next();
			graph.removeNode(next);
			current.replaceSuccessor(current.getSuccessor(), next); // could be redundant
			next.addPredecessor(current);
			current = next;
		}
		current.replaceSuccessor(current.getSuccessor(), new BlockEndNode(graph, base)); // mark the end of this block
	}

	
	// Creates an expression from an identifier and takes constants into account
	public Expression processIdentifier(String name) {
            Variable v = new Variable(name);
            currentScope.addVariable(v);
            return v;
	}

	public Expression processFunction(String name, List<Expression> args) {
            return new MacroCall(name, args);
         }

	public ExpressionStatement processExpressionStatement(Expression e) {
		ExpressionStatement statement = new ExpressionStatement(graph, e);
		addNode(statement);
		return statement;
	}
	
	public void addVisualizerExpression(ExpressionStatement expr) {
		graph.visualizerExpressions.add(expr);
	}

	/**
	 * Should be called to notify the graph builder that the parsing process has finished. If needed, post-processing of
	 * the graph can be performed here.
	 */
	public void finish() {
                //Check, if the cluscript contains at least one '?'-mark is moved to algebra plugin,
                //because of problems in macros.clu parsing in algebra plugin
            
		SetCallerVisitor visitor = new SetCallerVisitor();
		graph.accept(visitor);
		
		SetLocalAndInputVariables inputFinder = new SetLocalAndInputVariables();
		graph.accept(inputFinder);
	}

}
