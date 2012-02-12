package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import java.util.HashMap;
import de.gaalop.dfg.*;


/**
 * Evaluates the control flow graph
 * @author Christian Steinmetz
 */
public class Evaluater extends EmptyControlFlowVisitor implements ExpressionVisitor {

    private HashMap<MultivectorComponent, Double> values;
    
    public Evaluater(HashMap<MultivectorComponent, Double> values) {
        this.values = values;
    }

    public HashMap<MultivectorComponent, Double> getValues() {
        return values;
    }

    private Double result;
    
    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        Double left = result;
        node.getRight().accept(this);
        result = left - result;
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        Double left = result;
        node.getRight().accept(this);
        result = left + result;
    }

    @Override
    public void visit(Division node) {
        node.getLeft().accept(this);
        Double left = result;
        node.getRight().accept(this);
        result = left / result;
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        Double left = result;
        node.getRight().accept(this);
        result = left * result;
    }

    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);
        result = -result;
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.getOperand().accept(this);
        switch (node.getFunction()) {
            case ABS:
                result = Math.abs(result);
                break;
            case ACOS:
                result = Math.acos(result);
                break;
            case ASIN:
                result = Math.asin(result);
                break;
            case ATAN:
                result = Math.atan(result);
                break;
            case CEIL:
                result = Math.ceil(result);
                break;
            case COS:
                result = Math.cos(result);
                break;
            case EXP:
                result = Math.exp(result);
                break;
            case FACT:
                int n = (int) result.doubleValue();
                result = 1.0;
                for (int i = 2; i <= (int) n; i++) {
                    result *= i;
                }
                break;
            case FLOOR:
                result = Math.floor(result);
                break;
            case LOG:
                result = Math.log(result);
                break;
            case SIN:
                result = Math.sin(result);
                break;
            case SQRT:
                result = Math.sqrt(result);
                break;
            case TAN:
                result = Math.tan(result);
                break;
        }
    }

    @Override
    public void visit(MultivectorComponent node) {
        result = values.get(node);
    }

    @Override
    public void visit(Exponentiation node) {
        node.getLeft().accept(this);
        Double left = result;
        node.getRight().accept(this);
        result = Math.pow(left,result);
    }

    @Override
    public void visit(FloatConstant node) {
        result = node.getValue();
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(this);
        values.put((MultivectorComponent) node.getVariable(), result);
        super.visit(node);
    }

    @Override
    public void visit(Variable node) {
        result = values.get(new MultivectorComponent(node.getName(), 0));
    }

    // ====================== Illegal methods ======================

    @Override
    public void visit(OuterProduct node) {
        throw new UnsupportedOperationException("OuterProducts should have been removed by TBA.");
    }

    @Override
    public void visit(BaseVector node) {
        throw new UnsupportedOperationException("BaseVectors should have been removed by TBA.");
    }



    @Override
    public void visit(Reverse node) {
        throw new UnsupportedOperationException("Reverses should have been removed by TBA.");
    }

    @Override
    public void visit(LogicalOr node) {
        throw new UnsupportedOperationException("LogicalOrs should have been removed by TBA.");
    }

    @Override
    public void visit(LogicalAnd node) {
        throw new UnsupportedOperationException("LogicalAnds should have been removed by TBA.");
    }

    @Override
    public void visit(LogicalNegation node) {
        throw new UnsupportedOperationException("LogicalNegations should have been removed by TBA.");
    }

    @Override
    public void visit(Equality node) {
        throw new UnsupportedOperationException("Equalities should have been removed by TBA.");
    }

    @Override
    public void visit(Inequality node) {
        throw new UnsupportedOperationException("Inequalities should have been removed by TBA.");
    }

    @Override
    public void visit(Relation relation) {
        throw new UnsupportedOperationException("Relations should have been removed by TBA.");
    }

    @Override
    public void visit(FunctionArgument node) {
        throw new UnsupportedOperationException("FunctionArguments should have been removed by TBA.");
    }

    @Override
    public void visit(MacroCall node) {
        throw new UnsupportedOperationException("MacroCalls should have been removed by TBA.");
    }

    @Override
    public void visit(InnerProduct node) {
        throw new UnsupportedOperationException("Inner products should have been removed by TBA.");
    }

}
