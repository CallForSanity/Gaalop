package de.gaalop.gapp.importing;

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
import de.gaalop.gapp.importing.parallelObjects.Product;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;

/**
 * Implements a ExpressionVisitor that finds the direct factors of a given expression
 * @author Christian Steinmetz
 */
public class FactorsGetter implements ExpressionVisitor {

    private Product factors = new Product();

    private FactorsGetter() { //Make usage of static getFactors method mandatory
    }

    /**
     * Returns the direct factors of a given expression
     * @param expression The expression
     * @return The direct factors
     */
    public static Product getFactors(Expression expression) {
        FactorsGetter getter = new FactorsGetter();
        expression.accept(getter);
        return getter.factors;
    }

    /**
     * Calls the ExpressionCollector which creates a ParallelObject
     * @param expression The expression applied on the ExpressionCollector
     * @return The ParallelObject representation of the expression
     */
    private ParallelObject callExpressionCollector(Expression expression) {
        ExpressionCollector collector = new ExpressionCollector();
        expression.accept(collector);
        return collector.getResultValue();
    }

    @Override
    public void visit(Subtraction node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Addition node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Division node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(MathFunctionCall node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(MultivectorComponent node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Exponentiation node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(FloatConstant node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Negation node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Variable node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    // ============================ Logical methods ============================
    @Override
    public void visit(LogicalOr node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(LogicalAnd node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(LogicalNegation node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Equality node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Inequality node) {
        factors.getFactors().add(callExpressionCollector(node));
    }

    @Override
    public void visit(Relation relation) {
        factors.getFactors().add(callExpressionCollector(relation));
    }

    // ========================= Illegal visit methods =========================
    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException("InnerProducts should have been removed by TBA.");
    }

    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException("OuterProducts should have been removed by TBA.");
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException("BaseVectors should have been removed by TBA.");
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException("Reverses should have been removed by TBA.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("FunctionArguments should have been removed by CLUCalc Parser.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("FunctionArguments should have been removed by CLUCalc Parser.");
    }
}
