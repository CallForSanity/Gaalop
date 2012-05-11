package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Performs calculations on multivectors
 * @author Christian Steinmetz
 */
public class GAPPCalculateMv extends GAPPBaseInstruction {

    private CalculationType type;
    private GAPPMultivector destination;
    private GAPPMultivector operand1;
    private GAPPMultivector operand2;

    public GAPPCalculateMv(CalculationType type, GAPPMultivector destination, GAPPMultivector operand1, GAPPMultivector operand2) {
        this.type = type;
        this.destination = destination;
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

    public GAPPMultivector getDestination() {
        return destination;
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

    public void setDestination(GAPPMultivector destination) {
        this.destination = destination;
    }
}
