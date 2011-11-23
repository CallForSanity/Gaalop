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
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;

/**
 * Implements a ExpressionVisitor that finds the direct factors of a given expression
 * @author Christian Steinmetz
 */
public class FactorsGetterIndexer extends IllegalExpressionVisitor {

    private BladeIndexer indexer;
    private TCProduct factors = new TCProduct(new TCExpression[0]);

    private FactorsGetterIndexer(BladeIndexer indexer) { //Make usage of static getFactors method mandatory
        this.indexer = indexer;
    }

    /**
     * Returns the direct factors of a given expression
     * @param expression The expression
     * @return The direct factors
     */
    public static TCProduct getFactors(Expression expresssion, BladeIndexer indexer) {
        FactorsGetterIndexer getter = new FactorsGetterIndexer(indexer);
        expresssion.accept(getter);
        return getter.factors;
    }

    /**
     * Calls the ExpressionCollector which creates a ParallelObject
     * @param expression The expression applied on the ExpressionCollector
     * @return The ParallelObject representation of the expression
     */
    private TCExpression callExpressionCollector(Expression expression) {
        ExpressionCollectorIndexer collector = new ExpressionCollectorIndexer(indexer);
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
