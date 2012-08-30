package de.gaalop.testbenchTbaGapp.tbaNew.framework;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class CFGInterpreter extends EmptyControlFlowVisitor implements ExpressionVisitor {
    
    private HashMap<Variable, Double> mapVariables;
    private HashSet<Variable> outputVariables;
    
    private double resultValue;
    
    public CFGInterpreter(HashMap<Variable, Double> inputVariables) {
        mapVariables = new HashMap<Variable, Double>(inputVariables);
        outputVariables = new HashSet<Variable>();
    }

    public HashMap<Variable, Double> getMapVariables() {
        return mapVariables;
    }

    public HashSet<Variable> getOutputVariables() {
        return outputVariables;
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(this);
        mapVariables.put(node.getVariable(), resultValue);
        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {
        outputVariables.add(node.getValue());
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        node.getExpression().accept(this);
        super.visit(node);
    }

    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        double left = resultValue;
        node.getRight().accept(this);
        resultValue = left-resultValue;
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        double left = resultValue;
        node.getRight().accept(this);
        resultValue = left+resultValue;
    }

    @Override
    public void visit(Division node) {
        node.getLeft().accept(this);
        double left = resultValue;
        node.getRight().accept(this);
        resultValue = left/resultValue;
    }

    @Override
    public void visit(InnerProduct node) {
        throw new IllegalStateException("Inner Products should have been removed by OptimizationStrategy.");
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        double left = resultValue;
        node.getRight().accept(this);
        resultValue = left*resultValue;
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.getOperand().accept(this);
        //Apply MathFunction to operand
        switch (node.getFunction()) {
            case ABS:
                resultValue = Math.abs(resultValue);
                break;
            case ACOS:
                resultValue = Math.acos(resultValue);
                break;
            case ASIN:
                resultValue = Math.asin(resultValue);
                break;
            case ATAN:
                resultValue = Math.atan(resultValue);
                break;    
            case CEIL:
                resultValue = Math.ceil(resultValue);
                break;  
            case COS:
                resultValue = Math.cos(resultValue);
                break; 
            case EXP:
                resultValue = Math.exp(resultValue);
                break; 
            case FACT:
                int n = (int) Math.floor(resultValue);
                resultValue = 1;
                for (int i=2;i<n;i++)
                    resultValue *= i;
                break; 
            case FLOOR:
                resultValue = Math.floor(resultValue);
                break; 
            case INVERT:
                throw new IllegalStateException("Invert Function is not jet implemented for CFGInterpreter.");
            case LOG:
                resultValue = Math.log(resultValue);
                break; 
            case SIN:
                resultValue = Math.sin(resultValue);
                break; 
            case SQRT:
                resultValue = Math.sqrt(resultValue);
                break; 
            case TAN:
                resultValue = Math.tan(resultValue);
                break;     
        }
    }

    @Override
    public void visit(Variable node) {
        if (mapVariables.containsKey(node)) {
            resultValue = mapVariables.get(node);
        } else
            throw new IllegalStateException("Variable nodes should have been removed by TBA.");
    }

    @Override
    public void visit(MultivectorComponent node) {
        resultValue = mapVariables.get(node);
    }

    @Override
    public void visit(Exponentiation node) {
        node.getLeft().accept(this);
        double left = resultValue;
        node.getRight().accept(this);
        resultValue = Math.pow(left, resultValue);
    }

    @Override
    public void visit(FloatConstant node) {
        resultValue = node.getValue();
    }

    @Override
    public void visit(OuterProduct node) {
        throw new IllegalStateException("OuterProduct nodes should have been removed by TBA.");
    }

    @Override
    public void visit(BaseVector node) {
        throw new IllegalStateException("BaseVector nodes should have been removed by TBA.");
    }

    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);
        resultValue *= -1;
    }

    @Override
    public void visit(Reverse node) {
        throw new IllegalStateException("Reverse nodes should have been removed by TBA.");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new IllegalStateException("Logical operations are not implemented in CFGInterpreter.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new IllegalStateException("Logical operations are not implemented in CFGInterpreter.");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new IllegalStateException("Logical operations are not implemented in CFGInterpreter.");
    }

    @Override
    public void visit(Equality node) {
        throw new IllegalStateException("Logical operations are not implemented in CFGInterpreter.");
    }

    @Override
    public void visit(Inequality node) {
        throw new IllegalStateException("Logical operations are not implemented in CFGInterpreter.");
    }

    @Override
    public void visit(Relation relation) {
        throw new IllegalStateException("Logical operations are not implemented in CFGInterpreter.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new IllegalStateException("FunctionArgument nodes should have been removed by TBA.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new IllegalStateException("MacroCalls should have been removed by TBA.");
    }

}
