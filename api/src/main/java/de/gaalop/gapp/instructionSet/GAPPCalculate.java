package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Performs a calculation on a scalar part of a multivector
 * @author christian
 */
public class GAPPCalculate extends GAPPBaseInstruction {

    private CalculationType type;
    private GAPPMultivector target;
    private GAPPMultivector operand1;
    private GAPPMultivector operand2;

    public GAPPCalculate(GAPPMultivector target, GAPPMultivector operand1, CalculationType type, GAPPMultivector operand2) {
        this.type = type;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.target = target;
    }
    
    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitCalculate(this, arg);
    }

    public GAPPMultivector getOperand1() {
        return operand1;
    }

    public GAPPMultivector getOperand2() {
        return operand2;
    }

    public CalculationType getType() {
        return type;
    }

    public GAPPMultivector getTarget() {
        return target;
    }

    public void setOperand1(GAPPMultivector operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(GAPPMultivector operand2) {
        this.operand2 = operand2;
    }

    public void setType(CalculationType type) {
        this.type = type;
    }

    public void setTarget(GAPPMultivector target) {
        this.target = target;
    }

    

}
