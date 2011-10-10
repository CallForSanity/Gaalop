package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.variables.GAPPVector;

/**
 * Represents the assignVector command in the GAPP IR.
 *
 * Description:
 * Assigns scalar values or variables val0 , val1 , . . . , valn to a vector.
 */
public class GAPPAssignVector extends GAPPBaseInstruction {

    private GAPPVector destination;
    private Variableset values;

    public GAPPAssignVector(GAPPVector destination, Variableset values) {
        this.destination = destination;
        this.values = values;
    }

    public GAPPAssignVector(GAPPVector destination) {
        this.destination = destination;
        this.values = new Variableset();
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitAssignVector(this, arg);
    }

    public GAPPVector getDestination() {
        return destination;
    }

    public Variableset getValues() {
        return values;
    }

    public void setDestination(GAPPVector destination) {
        this.destination = destination;
    }

    public void setValues(Variableset values) {
        this.values = values;
    }

}
