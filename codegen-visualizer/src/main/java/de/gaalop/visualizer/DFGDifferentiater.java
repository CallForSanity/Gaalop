package de.gaalop.visualizer;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
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
import de.gaalop.dfg.Variable;

/**
 * Differentiates an Expression directly in Gaalop with respect to an Multivector component
 * @author Christian Steinmetz
 */
public class DFGDifferentiater implements ExpressionVisitor {
    
    private MultivectorComponent diffVar;
    private Expression result;
    private final FloatConstant zero = new FloatConstant(0);

    private DFGDifferentiater(MultivectorComponent diffVar) {
        this.diffVar = diffVar;
    }

    /**
     * Differentiates an Expression directly in Gaalop with respect to an Multivector component
     * @param exp The expression to be differentiated
     * @param diffVar The Multivector component
     * @return The differentiated expression 
     */
    public static Expression differentiate(Expression exp, MultivectorComponent diffVar) {
        DFGDifferentiater dFGDifferentiater = new DFGDifferentiater(diffVar);
        exp.accept(dFGDifferentiater);
        return dFGDifferentiater.result;
    }

    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        Expression left = result;
        node.getRight().accept(this);
        
        if (left==zero) {
            if (result!=zero)
                result = new Negation(result);
        } else {
            result = (result==zero)
                    ? left
                    : new Subtraction(left, result);
        }
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        Expression left = result;
        node.getRight().accept(this);
        if (left!=zero) {
            result = (result==zero)
                    ? left
                    : new Addition(left, result);
        }
    }

    @Override
    public void visit(Division node) {
        node.getLeft().accept(this);
        Expression left = result;
        node.getRight().accept(this);
        Expression right = result;
        result = new Division(
                new Subtraction(
                    new Multiplication(left, node.getRight()),
                    new Multiplication(node.getLeft(), right)
                ),
                new Multiplication(node.getRight(), node.getRight())
                );
        //TODO chs implement zero simplifiyings and check derivation
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        Expression left = result;
        node.getRight().accept(this);
        Expression right = result;
        result = new Addition(
                new Multiplication(left, node.getRight()),
                new Multiplication(node.getLeft(), right)
                );
        //TODO chs implement zero simplifiyings
    }
    
    @Override
    public void visit(Exponentiation node) {
        throw new UnsupportedOperationException("Not supported yet.");//TODO chs implement
    }

    @Override
    public void visit(MathFunctionCall node) {
        throw new UnsupportedOperationException("Not supported yet.");//TODO chs implement
    }

    @Override
    public void visit(Variable node) {
        result = zero; //TODO chs Be careful! This assumes, that differentation with respect to a variable (not MultivectorComponent) is never done!
    }

    @Override
    public void visit(MultivectorComponent node) {
        result = (node.equals(diffVar))
                ? new FloatConstant(1)
                : zero;
    }

    @Override
    public void visit(FloatConstant node) {
        result = zero;
    }
    
    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);
        result = new Negation(result);
    }

    //================ Illegal or not yet implemented methods ==================
    
    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException("Inner products should have been removed by TBA");
    }
    
    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException("Outer products should have been removed by TBA");
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException("Base vectors should have been removed by TBA");
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException("Reverses should have been removed by TBA");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new IllegalStateException("Logical ORs are not implemented yet");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new IllegalStateException("Logical ANDs are not implemented yet");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new IllegalStateException("Logical Negations are not implemented yet");
    }

    @Override
    public void visit(Equality node) {
        throw new IllegalStateException("Equalities are not implemented yet");
    }

    @Override
    public void visit(Inequality node) {
        throw new IllegalStateException("Inequalities are not implemented yet");
    }

    @Override
    public void visit(Relation relation) {
        throw new IllegalStateException("Relations are not implemented yet");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("FunctionArguments should have been inlined by an Algebra plugin");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("Macro Calls should have been inlined by an Algebra plugin");
    }

}
