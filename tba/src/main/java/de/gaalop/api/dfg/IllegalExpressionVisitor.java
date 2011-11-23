package de.gaalop.api.dfg;

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
 * This ExpressionVisitor defines all visitMethods as illegal methods
 * for clarity reasons
 * @author Christian Steinmetz
 */
public class IllegalExpressionVisitor implements ExpressionVisitor {

    @Override
    public void visit(Subtraction node) {
        throw new IllegalStateException("Subtractions are not allowed");
    }

    @Override
    public void visit(Addition node) {
        throw new IllegalStateException("Additions are not allowed");
    }

    @Override
    public void visit(Division node) {
        throw new IllegalStateException("Divisions are not allowed");
    }

    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException("InnerProducts are not allowed");
    }

    @Override
    public void visit(Multiplication node) {
        throw new IllegalStateException("Multiplications are not allowed");
    }

    @Override
    public void visit(MathFunctionCall node) {
        throw new IllegalStateException("MathFunctionCalls are not allowed");
    }

    @Override
    public void visit(Variable node) {
        throw new IllegalStateException("Variables are not allowed");
    }

    @Override
    public void visit(MultivectorComponent node) {
        throw new IllegalStateException("MultivectorComponents are not allowed");
    }

    @Override
    public void visit(Exponentiation node) {
        throw new IllegalStateException("Exponentiations are not allowed");
    }

    @Override
    public void visit(FloatConstant node) {
        throw new IllegalStateException("FloatConstants are not allowed");
    }

    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException("OuterProducts are not allowed");
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException("BaseVectors are not allowed");
    }

    @Override
    public void visit(Negation node) {
        throw new IllegalStateException("Negations are not allowed");
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException("Reverses are not allowed");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new IllegalStateException("LogicalOrs are not allowed");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new IllegalStateException("LogicalAnds are not allowed");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new IllegalStateException("LogicalNegations are not allowed");
    }

    @Override
    public void visit(Equality node) {
        throw new IllegalStateException("Equalitys are not allowed");
    }

    @Override
    public void visit(Inequality node) {
        throw new IllegalStateException("Inequalitys are not allowed");
    }

    @Override
    public void visit(Relation relation) {
        throw new IllegalStateException("Relations are not allowed");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("FunctionArguments are not allowed");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("MacroCalls are not allowed");
    }

}
