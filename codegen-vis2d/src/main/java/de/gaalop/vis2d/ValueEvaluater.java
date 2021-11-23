package de.gaalop.vis2d;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class ValueEvaluater extends EmptyControlFlowVisitor implements ExpressionVisitor {
    
    private HashMap<String, double[]> values = new HashMap<String, double[]>();
    
    private double resultValue;

    private ValueEvaluater() {
    }

    public static HashMap<String, double[]> evaluateValues(ControlFlowGraph graph) {
        ValueEvaluater valueEvaluater = new ValueEvaluater();
        graph.accept(valueEvaluater);
        return valueEvaluater.values;
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(this);
        
        MultivectorComponent mvC = (MultivectorComponent) node.getVariable();
        double[] arrValues;
        if (values.containsKey(mvC.getName())) 
            arrValues = values.get(mvC.getName());
        else {
            arrValues = new double[16];
            Arrays.fill(arrValues, 0);
            values.put(mvC.getName(), arrValues);
        }
        arrValues[mvC.getBladeIndex()] = resultValue;
        
        super.visit(node);
    }

    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        double leftValue = resultValue;
        node.getRight().accept(this);
        resultValue = leftValue-resultValue;
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        double leftValue = resultValue;
        node.getRight().accept(this);
        resultValue = leftValue+resultValue;
    }

    @Override
    public void visit(Division node) {
        node.getLeft().accept(this);
        double leftValue = resultValue;
        node.getRight().accept(this);
        resultValue = leftValue/resultValue;
    }

    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException("InnerProducts should have been removed by TBA");
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        double leftValue = resultValue;
        node.getRight().accept(this);
        resultValue = leftValue*resultValue;
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.accept(this);
        double operandValue = resultValue;
        switch (node.getFunction()) {
            case ABS:
                resultValue = Math.abs(operandValue);
                break;
            case ACOS:
                resultValue = Math.acos(operandValue);
                break;
            case ASIN:
                resultValue = Math.asin(operandValue);
                break;
            case ATAN:
                resultValue = Math.atan(operandValue);
                break;
            case CEIL:
                resultValue = Math.ceil(operandValue);
                break;
            case COS:
                resultValue = Math.cos(operandValue);
                break;
            case EXP:
                resultValue = Math.exp(operandValue);
                break;
            case FACT:
                resultValue = 1;
                for (int i=2;i<=(int) operandValue;i++)
                    resultValue *= i;
                break;
            case FLOOR:
                resultValue = Math.floor(operandValue);
                break;
            case LOG:
                resultValue = Math.log(operandValue);
                break;
            case SIN:
                resultValue = Math.sin(operandValue);
                break;
            case SQRT:
                resultValue = Math.sqrt(operandValue);
                break;
            case TAN:
                resultValue = Math.tan(operandValue);
                break;
            case INVERT:
                System.err.println("Inversions are not yet implemented in Vis2dCodeGen");
                break;
        }
    }

    @Override
    public void visit(Variable node) {
        throw new IllegalStateException("Variables are not yet allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(MultivectorComponent node) {
        resultValue = values.get(node.getName())[node.getBladeIndex()];
    }

    @Override
    public void visit(Exponentiation node) {
        node.getLeft().accept(this);
        double leftValue = resultValue;
        node.getRight().accept(this);
        resultValue = Math.pow(leftValue,resultValue);
    }

    @Override
    public void visit(FloatConstant node) {
        resultValue = node.getValue();
    }

    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException("OuterProducts should have been removed by TBA");
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException("BaseVector should have been removed by TBA");
    }

    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);
        resultValue = -resultValue;
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException("Reverse should have been removed by TBA");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new IllegalStateException("LogicalOrs are not allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new IllegalStateException("LogicalAnds are not allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new IllegalStateException("LogicalNegations are not allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(Equality node) {
        throw new IllegalStateException("Equalities are not allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(Inequality node) {
        throw new IllegalStateException("Inequalities are not allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(Relation relation) {
        throw new IllegalStateException("Relations are not allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("FunctionArguments are not allowed in Vis2dCodeGen");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("MacroCalls are not allowed in Vis2dCodeGen");
    }
    
}
