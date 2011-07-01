package de.gaalop.tba.cfgImport.optimization;

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
 * Implements a visitor that checks if the visited expression is a float constant
 * @author christian
 */
public class FloatConstantChecker implements ExpressionVisitor  {

    private boolean floatConstant = false;

    public boolean isFloatConstant() {
        return floatConstant;
    }

    @Override
    public void visit(Subtraction node) {
        
    }

    @Override
    public void visit(Addition node) {
        
    }

    @Override
    public void visit(Division node) {
        
    }

    @Override
    public void visit(InnerProduct node) {
        
    }

    @Override
    public void visit(Multiplication node) {
        
    }

    @Override
    public void visit(MathFunctionCall node) {
        
    }

    @Override
    public void visit(Variable node) {
        
    }

    @Override
    public void visit(MultivectorComponent node) {
        
    }

    @Override
    public void visit(Exponentiation node) {
        
    }

    @Override
    public void visit(FloatConstant node) {
        floatConstant = true;
    }

    @Override
    public void visit(OuterProduct node) {
        
    }

    @Override
    public void visit(BaseVector node) {
        
    }

    @Override
    public void visit(Negation node) {
        
    }

    @Override
    public void visit(Reverse node) {
        
    }

    @Override
    public void visit(LogicalOr node) {
        
    }

    @Override
    public void visit(LogicalAnd node) {
        
    }

    @Override
    public void visit(LogicalNegation node) {
        
    }

    @Override
    public void visit(Equality node) {
        
    }

    @Override
    public void visit(Inequality node) {
        
    }

    @Override
    public void visit(Relation relation) {
        
    }

    @Override
    public void visit(FunctionArgument node) {
        
    }

    @Override
    public void visit(MacroCall node) {
        
    }



}
