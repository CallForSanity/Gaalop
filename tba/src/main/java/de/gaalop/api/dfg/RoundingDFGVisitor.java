package de.gaalop.api.dfg;

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
 * Implements an expression visitor, which rounds float constants
 * @author Christian Steinmetz
 */
public class RoundingDFGVisitor implements ExpressionVisitor {
    
    private Expression result;
    private int numberOfDigits;
    
    /**
     * Facade method for rounding float constants in this expression tree.
     * @param expressionToRound The expression to be rounded
     * @param numberOfDigits The number of significant digits to round to
     * @return The rounded expression
     */
    public static Expression round(Expression expressionToRound, int numberOfDigits) {
        RoundingDFGVisitor visitor = new RoundingDFGVisitor(numberOfDigits);

        if (expressionToRound != null) 
            expressionToRound.accept(visitor);
        
        if (visitor.result != null)
            return visitor.result;
        else
            return expressionToRound;
    }

    public RoundingDFGVisitor(int numberOfDigits) {
        result = null;
        this.numberOfDigits = numberOfDigits;
    }

    @Override
    public void visit(Subtraction node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Subtraction(leftResult, rightResult);
    }

    @Override
    public void visit(Addition node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Addition(leftResult, rightResult);
    }

    @Override
    public void visit(Division node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Division(leftResult, rightResult);
    }

    @Override
    public void visit(InnerProduct node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new InnerProduct(leftResult, rightResult);
    }

    @Override
    public void visit(Multiplication node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Multiplication(leftResult, rightResult);
    }

    @Override
    public void visit(MathFunctionCall node) {
        result = null;
        node.getOperand().accept(this);
        if (result != null)
            result = new MathFunctionCall(result, node.getFunction());
    }

    @Override
    public void visit(Variable node) {
        result = null;
    }

    @Override
    public void visit(MultivectorComponent node) {
        result = null;
    }

    @Override
    public void visit(Exponentiation node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Exponentiation(leftResult, rightResult);
    }

    @Override
    public void visit(FloatConstant node) {
        result = null;
        double multiplicator = Math.pow(10, numberOfDigits);
        result = new FloatConstant(Math.round(node.getValue() * multiplicator) / multiplicator);
    }

    @Override
    public void visit(OuterProduct node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new OuterProduct(leftResult, rightResult);
    }

    @Override
    public void visit(BaseVector node) {
        result = null;
    }

    @Override
    public void visit(Negation node) {
        result = null;
        node.getOperand().accept(this);
        if (result != null)
            result = new Negation(result);
    }

    @Override
    public void visit(Reverse node) {
        result = null;
        node.getOperand().accept(this);
        if (result != null)
            result = new Reverse(result);
    }

    @Override
    public void visit(LogicalOr node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new LogicalOr(leftResult, rightResult);
    }

    @Override
    public void visit(LogicalAnd node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new LogicalAnd(leftResult, rightResult);
    }

    @Override
    public void visit(LogicalNegation node) {
        result = null;
        node.getOperand().accept(this);
        if (result != null)
            result = new LogicalNegation(result);
    }

    @Override
    public void visit(Equality node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Equality(leftResult, rightResult);
    }

    @Override
    public void visit(Inequality node) {
        result = null;
        node.getLeft().accept(this);
        Expression leftResult = result;
        node.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Inequality(leftResult, rightResult);
    }

    @Override
    public void visit(Relation relation) {
        result = null;
        relation.getLeft().accept(this);
        Expression leftResult = result;
        relation.getRight().accept(this);
        Expression rightResult = result;
        
        if (leftResult != null || rightResult != null) 
            result = new Relation(leftResult, rightResult, relation.getType());
    }

    @Override
    public void visit(FunctionArgument node) {
        result = null;
    }

    @Override
    public void visit(MacroCall node) {
        result = null;
    }


}
