package de.gaalop.dfg;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
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

/**
 * Implements a base class for a remover for an Expression.
 * If the member resultExpr isn't null then the associated subexpression is replaced with the resultExpr.
 * @author christian
 */
public class ExpressionRemover extends EmptyControlFlowVisitor implements ExpressionVisitor {

    protected Expression resultExpr = null;

    @Override
    public void visit(AssignmentNode node) {

        node.getValue().accept(this);
        if (resultExpr != null) node.setValue(resultExpr);
        resultExpr = null;

        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {

        node.getR().accept(this);
        if (resultExpr != null) node.setR(resultExpr);
        resultExpr = null;

        node.getG().accept(this);
        if (resultExpr != null) node.setG(resultExpr);
        resultExpr = null;

        node.getB().accept(this);
        if (resultExpr != null) node.setB(resultExpr);
        resultExpr = null;

        node.getAlpha().accept(this);
        if (resultExpr != null) node.setAlpha(resultExpr);
        resultExpr = null;

        super.visit(node);
    }

    private void handleUnaryOperation(UnaryOperation node) {
        node.getOperand().accept(this);
        if (resultExpr != null) node.setOperand(resultExpr);
        resultExpr = null;
    }

    private void handleBinaryOperation(BinaryOperation node) {
        node.getLeft().accept(this);
        if (resultExpr != null) node.setLeft(resultExpr);
        resultExpr = null;
        node.getRight().accept(this);
        if (resultExpr != null) node.setRight(resultExpr);
        resultExpr = null;
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
    }

    @Override
    public void visit(MultivectorComponent node) {
    }

    @Override
    public void visit(Exponentiation node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(FloatConstant node) {
    }

    @Override
    public void visit(OuterProduct node) {
        handleBinaryOperation(node);
    }

    @Override
    public void visit(BaseVector node) {
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
    }

    @Override
    public void visit(MacroCall node) {
    }

}
