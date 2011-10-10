package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Performs calculations on multivector parts, defined by two selectorsets
 * @author Christian Steinmetz
 */
public class GAPPCalculateMv extends GAPPBaseInstruction {

    private CalculationType type;
    private GAPPMultivector target;
    private GAPPMultivector operand1;
    private GAPPMultivector operand2;

    public GAPPCalculateMv(CalculationType type, GAPPMultivector target, GAPPMultivector operand1, GAPPMultivector operand2) {
        this.type = type;
        this.target = target;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
    
    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitCalculateMv(this, arg);
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
