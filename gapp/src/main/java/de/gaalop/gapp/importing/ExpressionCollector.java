package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
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
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Negation;
import de.gaalop.dfg.OuterProduct;
import de.gaalop.dfg.Relation;
import de.gaalop.dfg.Reverse;
import de.gaalop.dfg.Subtraction;
import de.gaalop.dfg.UnaryOperation;
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParVariable;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.instructionSet.CalculationType;

/**
 * Finds similar operations in Expression graphs and stores them in a ParallelObject instance.
 * Additions and Substractions are collected and Muliplications are collected.
 * Other Expression types are transformed in the ParallelObjects data structure
 * @author Christian Steinmetz
 */
public class ExpressionCollector implements ExpressionVisitor {

    private ParallelObject resultValue;

    public ParallelObject getResultValue() {
        return resultValue;
    }

    @Override
    public void visit(Subtraction node) {
        resultValue = SignedSummandsGetter.getSignedSummands(node);
    }

    @Override
    public void visit(Addition node) {
        resultValue = SignedSummandsGetter.getSignedSummands(node);
    }

    @Override
    public void visit(Multiplication node) {
        resultValue = FactorsGetter.getFactors(node);
    }

    @Override
    public void visit(FloatConstant node) {
        resultValue = new Constant(node.getValue());
    }

    @Override
    public void visit(MultivectorComponent node) {
        resultValue = new MvComponent(node);
    }

    @Override
    public void visit(Division node) {
        createExtCalculationFromBinaryOperation(node, CalculationType.DIVISION);
    }

    /**
     * Transforms a MathFunction into a CalculationType
     * @param function The MathFunction to transform
     * @return The CalculationType
     */
    private CalculationType transformFunction(MathFunction function) {
        switch (function) {
            case ABS:
                return CalculationType.ABS;
            case ACOS:
                return CalculationType.ACOS;
            case ASIN:
                return CalculationType.ASIN;
            case ATAN:
                return CalculationType.ATAN;
            case CEIL:
                return CalculationType.CEIL;
            case COS:
                return CalculationType.COS;
            case EXP:
                return CalculationType.EXP;
            case FACT:
                return CalculationType.FACT;
            case FLOOR:
                return CalculationType.FLOOR;
            case LOG:
                return CalculationType.LOG;
            case SIN:
                return CalculationType.SIN;
            case SQRT:
                return CalculationType.SQRT;
            case TAN:
                return CalculationType.TAN;
            case INVERT:
                return CalculationType.INVERT;
            default:
                System.err.println("Unknown MathFunction: " + function);
                return null;
        }
    }

    @Override
    public void visit(MathFunctionCall node) {
        createExtCalculationFromUnaryOperation(node, transformFunction(node.getFunction()));
    }

    @Override
    public void visit(Exponentiation node) {
        createExtCalculationFromBinaryOperation(node, CalculationType.EXPONENTIATION);
    }

    @Override
    public void visit(Negation node) {
        resultValue = null;
        node.getOperand().accept(this);
        resultValue.negate();
    }

    /**
     * Creates a extended calculation from an unary operation
     * @param node The unary operation node
     * @param type The calculation type
     */
    private void createExtCalculationFromUnaryOperation(UnaryOperation node, CalculationType type) {
        resultValue = null;
        node.getOperand().accept(this);
        resultValue = new ExtCalculation(type, resultValue, null);
    }

    /**
     * Creates a extended calculation from an binary operation
     * @param node The binary operation node
     * @param type The calculation type
     */
    private void createExtCalculationFromBinaryOperation(BinaryOperation node, CalculationType type) {
        resultValue = null;
        node.getLeft().accept(this);
        ParallelObject left = resultValue;
        resultValue = null;
        node.getRight().accept(this);
        ParallelObject right = resultValue;

        resultValue = new ExtCalculation(type, left, right);
    }

    @Override
    public void visit(Variable node) {
        resultValue = new ParVariable(node.getName());
    }

    // ========================= Illegal visit methods =========================
    @Override
    public void visit(LogicalOr node) {
        throw new IllegalStateException("Logical functions are not allowed.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new IllegalStateException("Logical functions are not allowed.");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new IllegalStateException("Logical functions are not allowed.");
    }

    @Override
    public void visit(Equality node) {
        throw new IllegalStateException("Logical functions are not allowed.");
    }

    @Override
    public void visit(Inequality node) {
        throw new IllegalStateException("Logical functions are not allowed.");
    }

    @Override
    public void visit(Relation relation) {
        throw new IllegalStateException("Logical functions are not allowed.");
    }

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
