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
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.Sum;

/**
 * Returns the direct summands of an expression
 * @author Christian Steinmetz
 */
public class SignedSummandsGetter implements ExpressionVisitor {

    private Sum summands = new Sum();
    private boolean curSignPositive = true;

    //private constructor to make using of the static facade method mandatory
    private SignedSummandsGetter() {
    }

    /**
     * Returns the direct summands of a given expression
     * @param expresssion The expression
     * @return The direct summands
     */
    public static Sum getSignedSummands(Expression expresssion) {
        SignedSummandsGetter getter = new SignedSummandsGetter();
        expresssion.accept(getter);
        return getter.summands;
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

    private void handleNodes(Expression node) {
        ParallelObject object = callExpressionCollector(node);
        if (!curSignPositive) {
            object.negate();
        }
        summands.getSummands().add(object);
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

    // ============================ Logical methods ============================
    @Override
    public void visit(LogicalOr node) {
        handleNodes(node);
    }

    @Override
    public void visit(LogicalAnd node) {
        handleNodes(node);
    }

    @Override
    public void visit(LogicalNegation node) {
        handleNodes(node);
    }

    @Override
    public void visit(Equality node) {
        handleNodes(node);
    }

    @Override
    public void visit(Inequality node) {
        handleNodes(node);
    }

    @Override
    public void visit(Relation relation) {
        handleNodes(relation);
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
