package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selectorset;
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
    private Selectorset used1;
    private Selectorset used2;

    public GAPPCalculateMv() {
    }

    public GAPPCalculateMv(CalculationType type, GAPPMultivector target, GAPPMultivector operand1, GAPPMultivector operand2, Selectorset used1, Selectorset used2) {
        this.type = type;
        this.target = target;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.used1 = used1;
        this.used2 = used2;
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

    public Selectorset getUsed1() {
        return used1;
    }

    public Selectorset getUsed2() {
        return used2;
    }

    public void setUsed1(Selectorset affected) {
        this.used1 = affected;
    }

    public void setUsed2(Selectorset used2) {
        this.used2 = used2;
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
