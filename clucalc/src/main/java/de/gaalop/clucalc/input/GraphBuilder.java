package de.gaalop.clucalc.input;

import de.gaalop.cfg.*;
import de.gaalop.clucalc.algebra.*;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;

import java.util.Iterator;
import java.util.List;

/**
 * This is a utility class used by the CluCalcTransformer to build a control flow graph while parsing the CluCalc AST.
 */
public final class GraphBuilder {

  /**
   * The list of algebra modes supported in CluCalc.
   */
  private static final AlgebraMode[] ALGEBRA_MODES = new AlgebraMode[] {
    new AlgebraC2(), new AlgebraE3(), new AlgebraN3(), new AlgebraP3(),
  };

  private final ControlFlowGraph graph;

  private SequentialNode lastNode;

  private AlgebraMode mode;

  private CluCalcFileHeader header;

  private final FunctionFactory functionFactory;

  public GraphBuilder() {
    graph = new ControlFlowGraph();
    lastNode = graph.getStartNode();
    functionFactory = new FunctionFactory();
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

  public AlgebraMode getMode() {
    return mode;
  }

  public FunctionFactory getFunctionFactory() {
    return functionFactory;
  }

  /**
   * Add an assignment node to the end of this graph.
   * 
   * @param variable The variable that is assigned to.
   * @param expression The expression that is assigned to the variable.
   */
  public AssignmentNode handleAssignment(Variable variable, Expression expression) {
    findUndeclaredVariables(expression);

    AssignmentNode assignment = new AssignmentNode(graph, variable, expression);
    addNode(assignment);
    graph.addLocalVariable(variable);
    return assignment;
  }

  /**
   * Handle a proecedure call.
   * 
   * @param name The name of the procedure that was called.
   */
  public void handleProcedure(String name) {
    // Initialization of an algebra mode?
    for (AlgebraMode mode : ALGEBRA_MODES) {
      if (mode.getDefinitionMethod().equals(name)) {
        setMode(mode);
        return;
      }
    }

    throw new IllegalArgumentException("Unknown procedure: " + name);
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
      findUndeclaredVariables(variable);

      StoreResultNode storeResult = new StoreResultNode(graph, (Variable) variable);
      addNode(storeResult);
      return storeResult;
    } else {
      throw new IllegalArgumentException("Only variables can be printed.");
    }
  }

  public IfThenElseNode handleIfStatement(Expression condition, List<SequentialNode> then_part, List<SequentialNode> else_part) {
    IfThenElseNode ifthenelse = new IfThenElseNode(graph, condition);
    addNode(ifthenelse);

    rewireNodes(then_part, ifthenelse);
    rewireNodes(else_part, ifthenelse);

    ifthenelse.setPositive(then_part.get(0));
    ifthenelse.setNegative((else_part != null && else_part.size() > 0) ? else_part.get(0) : new BlockEndNode(graph));

    return ifthenelse;
  }

  /**
   * Removes the nodes given by <code>list</code> from the control flow graph and rewires them to be a sequence of separate nodes,
   * e.g. in the body of an if-statement. The first node has <code>base</code> as predecessor. For the last node in the list,
   * base's successor will be set as successor, e.g. the first statement after an if-then-else statement.
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
    current.replaceSuccessor(current.getSuccessor(), new BlockEndNode(graph)); // mark the end of this block
  }

  /**
   * Activate a new algebra mode.
   * 
   * @param newMode The new algebra mode.
   */
  private void setMode(AlgebraMode newMode) {
    // Get annotation for graphs start node
    header = CluCalcFileHeader.get(graph.getStartNode());
    if (header == null) {
      header = new CluCalcFileHeader(graph.getStartNode());
    }
    header.setAlgebraMode(newMode);

    // Set the graphs dimension+signature based on our algebra
    boolean N3 = false;
    if (newMode instanceof AlgebraN3) {
      // handle the case of 5D conformal algebra
      N3 = true;
    }
    graph.setSignature(new AlgebraSignature(newMode.getSignature(), N3));

    mode = newMode;
    functionFactory.setMode(newMode);
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
        graph.addInputVariable(usedVariable);
      }
    }
  }

  // Creates an expression from an identifier and takes constants into account
  public Expression processIdentifier(String name) {
    if (mode != null && mode.isConstant(name)) {
      return mode.getConstant(name);
    } else {
      return new Variable(name);
    }
  }

  public Expression processFunction(String name, List<Expression> args) {
    if (functionFactory.isDefined(name)) {
      Expression[] argsArray = args.toArray(new Expression[args.size()]);
      return functionFactory.createExpression(name, argsArray);
    }

    for (MathFunction mathFunction : MathFunction.values()) {
      if (mathFunction.toString().toLowerCase().equals(name)) {
        if (args.size() == 1) {
          return new MathFunctionCall(args.get(0), mathFunction);
        } else {
          throw new IllegalArgumentException("Calling math function " + mathFunction + " with more than one" + " argument: "
            + args);
        }
      }
    }

    throw new IllegalArgumentException("Call to undefined function " + name + "(" + args + ").\n"
      + "Maybe this function is not defined in " + mode);
  }
}
