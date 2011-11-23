package de.gaalop.productComputer.transformer;

import de.gaalop.api.dfg.IllegalExpressionVisitor;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.tba.Algebra;

/**
 * Implements a ExpressionVisitor that finds the direct factors of a given expression
 * @author Christian Steinmetz
 */
public class FactorsGetter extends IllegalExpressionVisitor {

    private Algebra algebra;
    private TCProduct factors = new TCProduct(new TCExpression[0]);

    private FactorsGetter(Algebra algebra) { //Make usage of static getFactors method mandatory
        this.algebra = algebra;
    }

    /**
     * Returns the direct factors of a given expression
     * @param expression The expression
     * @return The direct factors
     */
    public static TCProduct getFactors(Expression expresssion, Algebra algebra) {
        FactorsGetter getter = new FactorsGetter(algebra);
        expresssion.accept(getter);
        return getter.factors;
    }

    /**
     * Calls the ExpressionCollector which creates a ParallelObject
     * @param expression The expression applied on the ExpressionCollector
     * @return The ParallelObject representation of the expression
     */
    private TCExpression callExpressionCollector(Expression expression) {
        ExpressionCollector collector = new ExpressionCollector(algebra);
        expression.accept(collector);
        return collector.getResultValue();
    }

    @Override
    public void visit(Subtraction node) {
        factors.add(callExpressionCollector(node));
    }

    @Override
    public void visit(Addition node) {
        factors.add(callExpressionCollector(node));
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(FloatConstant node) {
        factors.add(callExpressionCollector(node));
    }

    @Override
    public void visit(Negation node) {
        factors.add(callExpressionCollector(node));
    }

    @Override
    public void visit(Variable node) {
        factors.add(callExpressionCollector(node));
    }

    @Override
    public void visit(OuterProduct node) {
        factors.add(callExpressionCollector(node));
    }

    @Override
    public void visit(BaseVector node) {
        factors.add(callExpressionCollector(node));
    }

}
