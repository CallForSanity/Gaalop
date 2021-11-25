package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.*;
import de.gaalop.visualizer.ia_math.IAMath;
import de.gaalop.visualizer.ia_math.RealInterval;
import java.util.HashMap;

/**
 * Evaluates the control flow graph with interval arithmetic
 * @author Christian Steinmetz
 */
public class IntervalEvaluater implements ExpressionVisitor {

    private HashMap<MultivectorComponent, RealInterval> values;
    
    public IntervalEvaluater(HashMap<MultivectorComponent, RealInterval> values) {
        this.values = values;
    }

    public HashMap<MultivectorComponent, RealInterval> getValues() {
        return values;
    }

    private RealInterval result;
    
    public void evaluate(CodePiece codePiece) {
        for (AssignmentNode node: codePiece)
            visit(node);
    }
    
    @Override
    public void visit(Subtraction node) {
        node.getLeft().accept(this);
        RealInterval left = result;
        node.getRight().accept(this);
        result = IAMath.sub(left, result);
    }

    @Override
    public void visit(Addition node) {
        node.getLeft().accept(this);
        RealInterval left = result;
        node.getRight().accept(this);
        result = IAMath.add(left, result);
    }

    @Override
    public void visit(Division node) {
        node.getLeft().accept(this);
        RealInterval left = result;
        node.getRight().accept(this);
        result = IAMath.div(left,result);
    }

    @Override
    public void visit(Multiplication node) {
        node.getLeft().accept(this);
        RealInterval left = result;
        node.getRight().accept(this);
        result = IAMath.mul(left,result);
    }

    @Override
    public void visit(Negation node) {
        node.getOperand().accept(this);
        result = IAMath.uminus(result);
    }

    @Override
    public void visit(MathFunctionCall node) {
        node.getOperand().accept(this);
        switch (node.getFunction()) {
            case ABS:
                result = new RealInterval(Math.min(result.lo(),result.hi()),Math.max(result.lo(),result.hi()));
                break;
            case ACOS:
                result = IAMath.acos(result);
                break;
            case ASIN:
                result = IAMath.asin(result);
                break;
            case ATAN:
                result = IAMath.atan(result);
                break;
            case CEIL:
                result = new RealInterval(Math.ceil(result.lo()),Math.ceil(result.hi()));
                break;
            case COS:
                result = IAMath.cos(result);
                break;
            case EXP:
                result = IAMath.exp(result);
                break;
            case FACT:
                int n = (int) result.lo();
                double r = 1.0;
                for (int i = 2; i <= (int) n; i++) {
                    r *= i;
                }
                result = new RealInterval(r);
                break;
            case FLOOR:
                result = new RealInterval(Math.floor(result.lo()),Math.floor(result.hi()));
                break;
            case LOG:
                result = IAMath.log(result);
                break;
            case SIN:
                result = IAMath.sin(result);
                break;
            case SQRT:
                result = new RealInterval(Math.sqrt(result.lo()),Math.sqrt(result.hi()));
                break;
            case TAN:
                result = IAMath.tan(result);
                break;
        }
    }

    @Override
    public void visit(MultivectorComponent node) {
        RealInterval r = values.get(node);
        result = new RealInterval(r.lo(),r.hi());
    }

    @Override
    public void visit(Exponentiation node) {
        node.getLeft().accept(this);
        RealInterval left = result;
        node.getRight().accept(this);
        
        double hi = result.hi();
        if (Math.abs(hi-result.lo()) < 0.001 && Math.abs(hi-((int) hi)) < 0.001 && hi>=0) {
            result = new RealInterval(1);
            for (int i=0;i<((int) hi);i++) 
                result = IAMath.mul(result, left);
        } else
            result = IAMath.power(left, result);
    }

    @Override
    public void visit(FloatConstant node) {
        result = new RealInterval(node.getValue());
    }
    
    public void visit(AssignmentNode node) {
        node.getValue().accept(this);
        values.put((MultivectorComponent) node.getVariable(), result);
    }

    @Override
    public void visit(Variable node) {
        MultivectorComponent m = new MultivectorComponent(node.getName(), 0);
        if (!values.containsKey(m)) {
            System.err.println("Interval Evaluater: Kein Wert gefunden! "+m.toString());
            return;
        }
        RealInterval r = values.get(m);
        if (r != null)
            result = new RealInterval(r.lo(),r.hi());
        else {
            result = null;
            System.err.println("Interval Evaluater: Kein Wert 2 gefunden! "+m.toString());
        }
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
