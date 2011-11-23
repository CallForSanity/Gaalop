package de.gaalop.productComputer.transformer;

import de.gaalop.api.dfg.IllegalExpressionVisitor;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;

/**
 * Finds similar operations in Expression graphs and stores them in a ParallelObject instance.
 * Additions and Substractions are collected and Muliplications are collected.
 * Other Expression types are transformed in the ParallelObjects data structure
 * @author Christian Steinmetz
 */
public class ExpressionCollectorIndexer extends IllegalExpressionVisitor {

    private BladeIndexer indexer;
    private TCExpression resultValue;

    public ExpressionCollectorIndexer(BladeIndexer indexer) {
        this.indexer = indexer;
    }

    public TCExpression getResultValue() {
        return resultValue;
    }

    @Override
    public void visit(Subtraction node) {
        resultValue = SignedSummandsGetterIndexer.getSignedSummands(node, indexer);
    }

    @Override
    public void visit(Addition node) {
        resultValue = SignedSummandsGetterIndexer.getSignedSummands(node, indexer);
    }

    @Override
    public void visit(Multiplication node) {
        resultValue = FactorsGetterIndexer.getFactors(node, indexer);
    }

    @Override
    public void visit(FloatConstant node) {
        resultValue = new TCConstant(node.getValue());
    }

    @Override
    public void visit(Negation node) {
        resultValue = null;
        node.getOperand().accept(this);
        TCExpression[] expressions = new TCExpression[]{new TCConstant(-1), resultValue};
        resultValue = new TCProduct(expressions);
    }

    @Override
    public void visit(Variable node) {
        int index = Integer.parseInt(node.getName().substring(1));
        resultValue = indexer.getBlade(index);
    }
    
}
