package de.gaalop.visitors;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Equality;
import de.gaalop.dfg.Exponentiation;
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
import de.gaalop.dfg.Variable;

/**
 * Implements an ExpressionVisitor, that traverses all expressions
 * @author Christian Steinmetz
 */
public class DFGTraversalVisitor implements ExpressionVisitor {

    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(Division node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(InnerProduct node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.getOperand().accept(this);
    }

    @Override
    public void visit(Variable node) {
    }

    @Override
    public void visit(MultivectorComponent node) {
    }

    @Override
    public void visit(Exponentiation node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(FloatConstant node) {
    }

    @Override
    public void visit(OuterProduct node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(BaseVector node) {
    }

    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);
    }

    @Override
    public void visit(Reverse node) {
        node.getOperand().accept(this);
    }

    @Override
    public void visit(LogicalOr node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(LogicalAnd node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(LogicalNegation node) {
        node.getOperand().accept(this);
    }

    @Override
    public void visit(Equality node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(Inequality node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(Relation relation) {
        relation.getLeft().accept(this);
        relation.getRight().accept(this);
    }

    @Override
    public void visit(FunctionArgument node) {
    }

    @Override
    public void visit(MacroCall node) {
    }
}
