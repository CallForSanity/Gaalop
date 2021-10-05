package de.gaalop.visitors;

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
 * Implements a expression visitor, 
 * where every visit method calls the "super type" of the expression
 * @author Christian Steinmetz
 */
public abstract class ExpressionTypeVisitor implements ExpressionVisitor {

    /**
     * This method is called while traversing the DFG tree.
     * It handles the actions to be done for a binary operation
     * @param node The binary operation
     */
    protected abstract void visitBinaryOperation(BinaryOperation node);

    /**
     * This method is called while traversing the DFG tree.
     * It handles the actions to be done for a unary operation
     * @param node The unary operation
     */
    protected abstract void visitUnaryOperation(UnaryOperation node);

    /**
     * This method is called while traversing the DFG tree.
     * It handles the actions to be done for a terminal.
     * A terminal is an expression that has no child elements
     * @param node The terminal expression
     */
    protected abstract void visitTerminal(Expression node);

    @Override
    public void visit(Subtraction node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Addition node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Division node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(InnerProduct node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Multiplication node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(MathFunctionCall node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(Variable node) {
        visitTerminal(node);
    }

    @Override
    public void visit(MultivectorComponent node) {
        visitTerminal(node);
    }

    @Override
    public void visit(Exponentiation node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(FloatConstant node) {
        visitTerminal(node);
    }

    @Override
    public void visit(OuterProduct node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(BaseVector node) {
        visitTerminal(node);
    }

    @Override
    public void visit(Negation node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(Reverse node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(LogicalOr node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(LogicalAnd node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(LogicalNegation node) {
        visitUnaryOperation(node);
    }

    @Override
    public void visit(Equality node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Inequality node) {
        visitBinaryOperation(node);
    }

    @Override
    public void visit(Relation relation) {
        visitBinaryOperation(relation);
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("Macros should have been inlined");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macro "+ node.getName() +" should have been inlined!");
    }
}
