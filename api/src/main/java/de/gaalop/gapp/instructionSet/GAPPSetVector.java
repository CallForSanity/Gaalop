package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.SetVectorArgument;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.util.LinkedList;

/**
 * Represents the setVector command in the GAPP IR.
 */
public class GAPPSetVector extends GAPPBaseInstruction {

    private GAPPVector destination;
    private LinkedList<SetVectorArgument> entries;

    public GAPPSetVector(GAPPVector destination, LinkedList<SetVectorArgument> entries) {
        this.destination = destination;
        this.entries = entries;
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitSetVector(this, arg);
    }

    public GAPPVector getDestination() {
        return destination;
    }

    public LinkedList<SetVectorArgument> getEntries() {
        return entries;
    }

    public void setEntries(LinkedList<SetVectorArgument> entries) {
        this.entries = entries;
    }
    
    public void setDestination(GAPPVector destination) {
        this.destination = destination;
    }
}
