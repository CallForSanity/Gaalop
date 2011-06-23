/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

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
 *
 * @author christian
 */
public class DFGImporter implements ExpressionVisitor {

    @Override
    public void visit(Subtraction node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Addition node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Division node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(InnerProduct node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Multiplication node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MathFunctionCall node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Variable node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MultivectorComponent node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Exponentiation node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(FloatConstant node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(OuterProduct node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BaseVector node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Negation node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Reverse node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Equality node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Inequality node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Relation relation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
