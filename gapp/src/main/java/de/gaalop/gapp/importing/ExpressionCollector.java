package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.irZwei.Factors;
import de.gaalop.gapp.importing.irZwei.SignedSummand;
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
import de.gaalop.dfg.Variable;
import de.gaalop.gapp.importing.irZwei.ExpressionContainer;
import de.gaalop.gapp.importing.irZwei.ExtCalculation;
import de.gaalop.gapp.importing.irZwei.GAPPValueHolderContainer;
import de.gaalop.gapp.importing.irZwei.ParallelObject;
import de.gaalop.gapp.importing.irZwei.Summands;
import de.gaalop.gapp.instructionSet.CalculationType;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;

/**
 *
 * @author Christian Steinmetz
 */
public class ExpressionCollector implements ExpressionVisitor {

    private ParallelObject resultValue;

    private void visitAdditionSubtraction(BinaryOperation node) {
        Summands summands = SignedSummandsGetter.getSignedSummands(node);
        for (SignedSummand summand: summands.getSummands()) {
            // SignedSummandsGetter returns only ExpressionContainer instances as ParallelObject!
            ExpressionContainer contSummand = (ExpressionContainer) summand.getParallelObject();
            resultValue = null;
            contSummand.getExpression().accept(this);
            summand.setParallelObject(resultValue);
        }

        resultValue = summands;
    }

    @Override
    public void visit(Subtraction node) {
        visitAdditionSubtraction(node);
    }

    @Override
    public void visit(Addition node) {
        visitAdditionSubtraction(node);
    }

    @Override
    public void visit(Multiplication node) {
        resultValue = null;
        node.getLeft().accept(this);
        ParallelObject left = resultValue;
        resultValue = null;
        node.getRight().accept(this);
        ParallelObject right = resultValue;

        Factors factors = new Factors();
        factors.add(left);
        factors.add(right);
        resultValue = factors;
    }

    @Override
    public void visit(FloatConstant node) {
        resultValue = new GAPPValueHolderContainer(new GAPPConstant(node.getValue()));
    }

    @Override
    public void visit(MultivectorComponent node) {
        resultValue = new GAPPValueHolderContainer(
                new GAPPMultivectorComponent(node.getName(), node.getBladeIndex())
                );
    }

    @Override
    public void visit(Division node) {
        resultValue = null;
        node.getLeft().accept(this);
        ParallelObject left = resultValue;
        resultValue = null;
        node.getRight().accept(this);
        ParallelObject right = resultValue;

        resultValue = new ExtCalculation(CalculationType.DIVISION, left, right);
    }

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
            default:
                System.err.println("Unknown MathFunction: "+function);
                return null;
        }
    }

    @Override
    public void visit(MathFunctionCall node) {
        resultValue = null;
        node.getOperand().accept(this);
        resultValue = new ExtCalculation(transformFunction(node.getFunction()), resultValue, null);
    }

    @Override
    public void visit(Exponentiation node) {
        resultValue = null;
        node.getLeft().accept(this);
        ParallelObject left = resultValue;
        resultValue = null;
        node.getRight().accept(this);
        ParallelObject right = resultValue;

        resultValue = new ExtCalculation(CalculationType.EXPONENTIATION, left, right);
    }

    @Override
    public void visit(Negation node) {
        resultValue = null;
        node.getOperand().accept(this);

        Summands summands = new Summands();
        summands.getSummands().add(new SignedSummand(false, resultValue));
        
        resultValue = summands;
    }

    // ============================ Logical methods ============================

    //TODO chs Add support to logical functions

    @Override
    public void visit(LogicalOr node) {
        throw new UnsupportedOperationException("Logical: Not supported yet.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new UnsupportedOperationException("Logical: Not supported yet.");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new UnsupportedOperationException("Logical: Not supported yet.");
    }

    @Override
    public void visit(Equality node) {
        throw new UnsupportedOperationException("Logical: Not supported yet.");
    }

    @Override
    public void visit(Inequality node) {
        throw new UnsupportedOperationException("Logical: Not supported yet.");
    }

    @Override
    public void visit(Relation relation) {
        throw new UnsupportedOperationException("Logical: Not supported yet.");
    }

    // ========================= Illegal visit methods =========================


    @Override
    public void visit(Variable node) {
        throw new IllegalStateException("Variables should have been removed by TBA.");
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
