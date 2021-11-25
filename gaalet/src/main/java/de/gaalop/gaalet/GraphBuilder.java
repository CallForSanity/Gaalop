package de.gaalop.gaalet;

import de.gaalop.NameTable;
import de.gaalop.CheckGAVisitor;
import de.gaalop.cfg.*;
import de.gaalop.UsedVariablesVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;

import java.util.ArrayList;
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
			//graph.addLocalVariable(node.getVariable());
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

	private final ControlFlowGraph graph;

	private SequentialNode lastNode;

	private HashMap<String, GaaletMultiVector> vectorSet = new HashMap<String, GaaletMultiVector>();
	
	private int assignments;

	private Set<String> macros = new HashSet<String>();
	
	private String currentMacroDefinition;	
	
	private VariableScope currentScope = VariableScope.GLOBAL;

	private static final Map<String, String> illegalNames = new HashMap<String, String>();
	
	static {
//		illegalNames = new HashMap<String, String>();
//		illegalNames.put("B", "B is the metric matrix in Maple");
//		illegalNames.put("condition_", "synthetic variable inserted by Gaalop");
//		illegalNames.put("norm", "protected in Maple");
//		illegalNames.put("normal", "protected in Maple");
//		illegalNames.put("length", "protected in Maple");
//		illegalNames.put("point", "protected in Maple");
	}
	
	public GraphBuilder() {
		graph = new ControlFlowGraph();
		lastNode = graph.getStartNode();
	}

	void checkIllegalVariable(Variable variable) {
		String name = variable.getName();
		String reason = illegalNames.get(name);
		if (reason != null) {
			throw new IllegalArgumentException("Illegal variable name '" + name + "' (" + reason + ")."
					+ " Please use another variable.");
		}
		if (variable.getName().startsWith("re")) {
			throw new IllegalArgumentException("Variable '" + variable
					+ "' cannot be used in Maple because of prefix 're' which is protected."
					+ " Please choose another name.");
		}
		if (variable.getName().startsWith("condition_")) {
			throw new IllegalArgumentException("Variable '" + variable 
					+ "' cannot be used because of prefix 'condition_' which is used for conditional statements."
					+ " Please choose another name.");
		}
	}
	
	public void beginNewScope() {
		currentScope = new VariableScope(currentScope);
	}

	public void endNewScope() {
		currentScope = currentScope.getParent();
	}
	
	/**
	 * Adds a variable name to the control flow graph as Pragma output marked.
	 * 
	 * @param variable
	 */
	public void addPragmaOutputVariable(String variable) {
		graph.addPragmaOutputVariable(variable);
	}

	/**
	 * Adds a pragma hint for a variable, which defines value range for it. The pragma must be set before the variable is added to
	 * the input variables, i.e. the pragma must appear for the use of the variable
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

	/**
	 * Adds a control variable for loops to the control flow graph.
	 * 
	 * @param variable
	 */
	public void addIgnoreVariable(Variable variable) {
		//graph.addIgnoreVariable(variable);
		graph.addScalarVariable(variable);
	}
	
	public ExpressionStatement processExpressionStatement(Expression e) {
		ExpressionStatement statement = new ExpressionStatement(graph, e);
		addNode(statement);
		return statement;
	}
	
	
	public ControlFlowGraph getGraph() {
		return graph;
	}
	
	/**
	 * Handles a loop statement. A CluCalc loop consists of a body of statements, typically containing the break
	 * keyword.
	 * 
	 * @param body list of statements belonging to body
	 * @param iterations (optional) number of iterations for loop unrolling
	 * @param counter (optional) counter variable
	 * @return new {@link LoopNode} representing this statement.
	 */
	public LoopNode handleLoop(List<SequentialNode> body, String iterations, Variable counter) {
		LoopNode loop = new LoopNode(graph);
		addNode(loop);
		rewireNodes(body, loop);
		loop.setBody((body != null && body.size() > 0) ? body.get(0) : new BlockEndNode(graph, loop));

		// save number of iterations and reset value for next loops
		if (iterations != null) {
			loop.setIterations(Integer.parseInt(iterations));
		}
		// save counter variable for this loop
		if (counter != null) {
			if (CheckGAVisitor.isGAVariable(counter)) {
				throw new IllegalArgumentException("Counter variable " + counter + " is not scalar. Please use "
						+ counter + " only as counter variable and do not assign Geometric Algebra expressions to it.");
			}
			loop.setCounterVariable(counter);
			graph.removeLocalVariable(counter);
			graph.addScalarVariable(counter);
			//graph.addIgnoreVariable(counter);
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
	
	
	public Macro handleMacroDefinition(String id, List<SequentialNode> body, Expression ret) {
		// reset current macro name to allow further calls
		currentMacroDefinition = "";
		Macro macro = new Macro(graph, id, body, ret);
		addNode(macro);
		graph.addMacro(macro);

		rewireNodes(body, macro);

		return macro;
	}

	public void addMacroName(String name) {
		String hashname = NameTable.getInstance().add(name);
		macros.add(hashname);
		currentMacroDefinition = hashname;
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
	
	protected int getNumberOfAssignments() {
		return assignments;
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
			//gaVisitor.addGAVariable(variable);
		}
		
		AssignmentNode assignment = new AssignmentNode(graph, variable, expression);
		
		
		addNode(assignment);
		return assignment;
	}
	
	/**
	 * Add an assignment node to the end of this graph.
	 * 
	 * @param name The name of the variable that is assigned to.
	 * @param expression The expression that is assigned to the variable.
	 */
	public AssignmentNode handleAssignment(String name, Expression expression) {
		return handleAssignment(new Variable(name), expression);
	}
	

	/**
	 * Handle a proecedure call.
	 * 
	 * @param name The name of the procedure that was called.
	 */
	public void handleProcedure(String name) {
            /*//@changedalgebra
		String hashname = NameTable.getInstance().add(name);
		// Initialization of an algebra mode?
		for (AlgebraMode mode : ALGEBRA_MODES) {
			if (mode.getDefinitionMethod().equals(hashname)) {
				setMode(mode);
				return;
			}
		}

		throw new IllegalArgumentException("Unknown procedure: " + name + "  "+ hashname);
            */
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

	public IfThenElseNode handleIfStatement(Expression condition, List<SequentialNode> then_part, List<SequentialNode> else_part) {
		IfThenElseNode ifthenelse = new IfThenElseNode(graph, condition);
		addNode(ifthenelse);

		rewireNodes(then_part, ifthenelse);
		rewireNodes(else_part, ifthenelse);

		ifthenelse.setPositive(then_part.get(0));
		ifthenelse.setNegative((else_part != null && else_part.size() > 0) ? else_part.get(0) : new BlockEndNode(graph, lastNode));

		return ifthenelse;
	}

	/**
	 * Removes the nodes given by <code>list</code> from the control flow graph and rewires them to be a sequence of separate
	 * nodes, e.g. in the body of an if-statement. The first node has <code>base</code> as predecessor. For the last node in the
	 * list, base's successor will be set as successor, e.g. the first statement after an if-then-else statement.
	 * 
	 * @param list list of nodes from a block
	 * @param base basis of block, e.g. an if-statement
	 */
	private void rewireNodes(List<SequentialNode> list, IfThenElseNode base) {
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
		current.replaceSuccessor(current.getSuccessor(), new BlockEndNode(graph, current)); // mark the end of this block
	}


	/**
	 * Searches the expression for variable references. If an undeclared reference is found, it is added to the input variables of
	 * the graph.
	 * 
	 * @param expression The expression to search in.
	 */
	private void findUndeclaredVariables(Expression expression) {

		UsedVariablesVisitor visitor = new UsedVariablesVisitor();
		expression.accept(visitor);

		for (Variable usedVariable : visitor.getVariables()) {
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

	// Creates an expression from an identifier and takes constants into account
	public Expression processIdentifier(String name) {
            /*//@changedalgebra
		String hashname = NameTable.getInstance().add(name);
		if (mode != null && mode.isConstant(hashname)) {
			return mode.getConstant(hashname);
		} else {
			return new Variable(hashname);
		}
             */
            return new Variable(name);
	}
	public Expression processFunction(String name, List<Expression> args) {
            /*//@changedalgebra
		for (AlgebraMode mode : ALGEBRA_MODES) {
			if (mode.getDefinitionMethod().equals(name)) {
				setMode(mode);
				return null;
			}
		}
//		if (functionFactory.isDefined(name)) {
//			Expression[] argsArray = args.toArray(new Expression[args.size()]);
//			return functionFactory.createExpression(name, argsArray);
//		}

		for (MathFunction mathFunction : MathFunction.values()) {
			if (mathFunction.toString().toLowerCase().equals(name)) {
				if (args.size() == 1) {
					return new MathFunctionCall(args.get(0), mathFunction);
				} else {
					throw new IllegalArgumentException("Calling math function " + mathFunction + " with more than one"
							+ " argument: " + args);
				}
			}
		}

		if (name.startsWith("::")) {
			name = name.substring(2);
		}
		String hashname = NameTable.getInstance().add(name);

		if (macros.contains(hashname)) {
			if (name.equals(currentMacroDefinition)) {
				throw new IllegalArgumentException("Recursive macro calls are not supported: " + name);
			} else {
				return new MacroCall(hashname, args);
			}
		}

		throw new IllegalArgumentException("Call to undefined function " + name + "(" + args + ").\n"
				+ "Maybe this function is not defined in " + mode + "\n"
				+ "Also make sure that macros are defined before they are called.");
             *
             */
            return new MacroCall(name, args);
	}

	public SequentialNode handleDefGaalet(String name,ArrayList<String> blades,
			ArrayList<Expression> expression) {
		assignments++;
		
		String hashname = NameTable.getInstance().add(name);
		
	    GaaletMultiVector muVec = new GaaletMultiVector(hashname);
		// we guess that blades and expression have the same size.
		for (int n=0; n<expression.size();n++) {
			findUndeclaredVariables(expression.get(n));
	
			String hex = blades.get(n);
			int number;
			if ((hex.length()>1)&&(hex.charAt(1) =='x')) {
				hex = hex.substring(2);
				number = Integer.valueOf(hex,16);			
			} else
				number = new Integer(hex);
			
			muVec.addGaaletTupel(number,expression.get(n));
		}
		
		Variable variable;		
		variable = new Variable(hashname);
		AssignmentNode assignment = new AssignmentNode(graph, variable, muVec.getExpression());
		addNode(assignment);
		
	 // assignmentNode cares about hashnames by itself	
		vectorSet.put(hashname, muVec);

		wasDefined(hashname);	
		
		
		System.out.println(muVec.getExpression());
		return assignment;
	}

	public SequentialNode bladeAssignment(String name, String index,
			Expression expression) {
		
			assignments++;
			findUndeclaredVariables(expression);
			System.out.println("Blade Assignment");		

			Integer number = new Integer(index); // check needed.
			String hashname = NameTable.getInstance().add(name);			
			if (vectorSet.get(hashname) == null) {
				System.err.println("Definition of variable " + name + " needed!!!");
			}
			
			int gealgBlade = vectorSet.get(hashname).get(number);

			Variable variable = new MultivectorComponent(hashname, gealgBlade); 
			AssignmentNode assignment = new AssignmentNode(graph, variable, expression);
			addNode(assignment);

		return assignment;
	}

	public void defineMV(String name, ArrayList<String> blades) {
		String hashname = NameTable.getInstance().add(name);
		GaaletMultiVector variable = new GaaletMultiVector(hashname);
		
		vectorSet.put(hashname, variable);
		for (String blade: blades) {	
			
			vectorSet.get(hashname).addGaaletBlades(blade);	
		}
		
		wasDefined(hashname);
	}
	
	public MultivectorComponent blade(String name, String blade) {
		int index = new Integer(blade);
		String hashname = NameTable.getInstance().add(name);		
		int gealgBlade = vectorSet.get(hashname).get(index);
		
		return new MultivectorComponent(hashname, gealgBlade);
	}
	
	/**
	 * Should be called to notify the graph builder that the parsing process has finished. If needed, post-processing of
	 * the graph can be performed here.
	 */
	public void finish() {

//		for (Variable v : graph.getIgnoreVariables()) {
//			graph.removeLocalVariable(v);
//		}
		//FindStoreOutputNodes outputNodes = new FindStoreOutputNodes();
		//graph.accept(outputNodes);
		
		//if (outputNodes.getNodes().isEmpty()) {
		//	throw new RuntimeException("There are no lines marked for optimization ('?')");
		//}
		
		SetCallerVisitor visitor = new SetCallerVisitor();
		graph.accept(visitor);
		
		SetLocalAndInputVariables inputFinder = new SetLocalAndInputVariables();
		graph.accept(inputFinder);
	}
	
	/**
	 * Whenever a variable is defined, we want it to be also defined in the generated output.
	 * 
	 * @param variableName The identifier of the variable. NOT the hashname given be NameTable.
	 */
	public void wasDefined(String variableName) {
		//String hashname = NameTable.getInstance().add(variableName);		
		graph.addLocalVariable(new Variable(variableName));		
	}
	/**
	 * Whenever a variable is defined, we want it to be also defined in the generated output.
	 * 
	 * @param variableName The identifier of the variable. NOT the hashname given be NameTable.
	 */
	
	public void wasDefined(Variable variable) {
		wasDefined(variable.getName());		
	}

}
