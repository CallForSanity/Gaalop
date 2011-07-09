package de.gaalop.gapp.importing;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.FunctionArgument;
import de.gaalop.dfg.Inequality;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.LogicalAnd;
import de.gaalop.dfg.LogicalNegation;
import de.gaalop.dfg.LogicalOr;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.dfg.Variable;
import java.util.Set;

/**
 * Splits the expressions of graph so that at most one operation exists in a node.
 * See [Hildenbrand, Charrier, Koch; Specialized Machine Instruction Set for Geometric Algebra, chapter 3 section Example]
 * @author christian
 */
public class Splitter extends EmptyControlFlowVisitor implements ExpressionVisitor {

    private int tmpVariablesCounter = 0;

    private ControlFlowGraph graph;

    private SequentialNode curNode;
    private Expression curExpression;
    private Expression resultExpression;

    public Splitter(ControlFlowGraph graph) {
        this.graph = graph;
    }

    /**
     * Creates a new temp variable that don't exists in the graph
     * @return The new temp Variable
     */
    private Variable createNewTempVariable() {

        Set<Variable> variables = graph.getLocalVariables();
        Variable result;

        do {
            result = new Variable("sTmp"+tmpVariablesCounter);
            tmpVariablesCounter++;
        } while (variables.contains(result));

        return result;
    }

    /**
     * Splits a expression and inserts the inner expressions before a given node
     * @param expression The expression to split
     * @param curNode The node to insert before
     * @return The expression containing only one operation
     */
    private Expression splitExpression(Expression expression, SequentialNode curNode) {
        this.curNode = curNode;
        curExpression = expression;
        resultExpression = null;
        expression.accept(this);
        if (resultExpression == null)
            return expression;
        else
            return resultExpression;
    }


    @Override
    public void visit(AssignmentNode node) {
        node.setValue(splitExpression(node.getValue(), node));
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        node.setExpression(splitExpression(node.getExpression(), node));
        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        node.setR(splitExpression(node.getR(), node));
        node.setG(splitExpression(node.getG(), node));
        node.setB(splitExpression(node.getB(), node));
        node.setAlpha(splitExpression(node.getAlpha(), node));
        super.visit(node);
    }

    private void handleUnaryOperation(UnaryOperation unaryOperation) {

        unaryOperation.getOperand().accept(this);
        if (resultExpression != null)
            unaryOperation.setOperand(resultExpression);

        if (curExpression == unaryOperation) {
            // terminate without variable creation
            resultExpression = null;
            return;
        }

        Variable targetVariable = createNewTempVariable();
        curNode.insertBefore(new AssignmentNode(graph,targetVariable,unaryOperation));

        resultExpression = targetVariable;
    }

    private void handleBinaryOperation(BinaryOperation binaryOperation) {

        binaryOperation.getLeft().accept(this);
        if (resultExpression != null)
            binaryOperation.setLeft(resultExpression);

        binaryOperation.getRight().accept(this);
        if (resultExpression != null)
            binaryOperation.setRight(resultExpression);

        
        if (curExpression == binaryOperation) {
            // terminate without variable creation
            resultExpression = null;
            return;
        }
         
         

        Variable targetVariable = createNewTempVariable();
        curNode.insertBefore(new AssignmentNode(graph,targetVariable,binaryOperation));

        resultExpression = targetVariable;
    }


    @Override
    public void visit(Subtraction node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(Addition node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(Division node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(InnerProduct node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(Multiplication node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(MathFunctionCall node) {
        handleUnaryOperation(node);
    }

    @Override
    public void visit(Variable node) {
        resultExpression = null;
    }

    @Override
    public void visit(MultivectorComponent node) {
        resultExpression = null;
    }

    @Override
    public void visit(Exponentiation node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(FloatConstant node) {
        resultExpression = null;
    }

    @Override
    public void visit(OuterProduct node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(BaseVector node) {
        resultExpression = null;
    }

    @Override
    public void visit(Negation node) {
        handleUnaryOperation(node);
    }

    @Override
    public void visit(Reverse node) {
        handleUnaryOperation(node);
    }

    @Override
    public void visit(LogicalOr node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(LogicalAnd node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(LogicalNegation node) {
        handleUnaryOperation(node);
    }

    @Override
    public void visit(Equality node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(Inequality node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(Relation relation) {
        handleBinaryOperation(relation);
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
    }



}
