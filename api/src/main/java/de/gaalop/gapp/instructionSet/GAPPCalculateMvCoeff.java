package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Performs calculations on multivectors and results in a mutlivector component
 * @author Christian Steinmetz
 */
public class GAPPCalculateMvCoeff extends GAPPBaseInstruction {

    private CalculationType type;
    private GAPPMultivectorComponent destination;
    private GAPPMultivector operand1;
    private GAPPMultivector operand2;

    public GAPPCalculateMvCoeff(CalculationType type, GAPPMultivectorComponent destination, GAPPMultivector operand1, GAPPMultivector operand2) {
        this.type = type;
        this.destination = destination;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitCalculateMvCoeff(this, arg);
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

    public GAPPMultivectorComponent getDestination() {
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

    public void setDestination(GAPPMultivectorComponent destination) {
        this.destination = destination;
    }
}
