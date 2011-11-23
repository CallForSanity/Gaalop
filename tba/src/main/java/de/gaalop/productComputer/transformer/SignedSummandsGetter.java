package de.gaalop.productComputer.transformer;

import de.gaalop.api.dfg.IllegalExpressionVisitor;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Division;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.productComputer.dataStruct.TCSum;
import de.gaalop.tba.Algebra;

/**
 * Returns the direct summands of an expression
 * @author Christian Steinmetz
 */
public class SignedSummandsGetter extends IllegalExpressionVisitor {

    private Algebra algebra;
    private TCSum summands = new TCSum(new TCExpression[0]);
    private boolean curSignPositive = true;

    //private constructor to make using of the static facade method mandatory
    private SignedSummandsGetter(Algebra algebra) {
        this.algebra = algebra;
    }

    /**
     * Returns the direct summands of a given expression
     * @param expresssion The expression
     * @return The direct summands
     */
    public static TCSum getSignedSummands(Expression expresssion, Algebra algebra) {
        SignedSummandsGetter getter = new SignedSummandsGetter(algebra);
        expresssion.accept(getter);
        return getter.summands;
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
        node.getLeft().accept(this);
        curSignPositive = !curSignPositive;
        node.getRight().accept(this);
        curSignPositive = !curSignPositive;
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    /**
     * Calls the expression collector
     * @param node The node
     */
    private void handleNodes(Expression node) {
        TCExpression object = callExpressionCollector(node);

        if (!curSignPositive) {
            TCExpression[] expressions = new TCExpression[]{new TCConstant(-1), object};
            object = new TCProduct(expressions);
        }
        summands.add(object);
    }

    @Override
    public void visit(Division node) {
        handleNodes(node);
    }

    @Override
    public void visit(Multiplication node) {
        handleNodes(node);
    }

    @Override
    public void visit(MathFunctionCall node) {
        handleNodes(node);
    }

    @Override
    public void visit(MultivectorComponent node) {
        handleNodes(node);
    }

    @Override
    public void visit(Variable node) {
        handleNodes(node);
    }

    @Override
    public void visit(Exponentiation node) {
        handleNodes(node);
    }

    @Override
    public void visit(FloatConstant node) {
        handleNodes(node);
    }

    @Override
    public void visit(Negation node) {
        handleNodes(node);
    }

}
