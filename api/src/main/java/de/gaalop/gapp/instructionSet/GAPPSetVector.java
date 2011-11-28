package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.PairSetOfVariablesAndIndices;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.util.LinkedList;

/**
 * Represents the setVector command in the GAPP IR.
 */
public class GAPPSetVector extends GAPPBaseInstruction {

    private GAPPVector destination;
    private LinkedList<PairSetOfVariablesAndIndices> entries;

    public GAPPSetVector(GAPPVector destination, LinkedList<PairSetOfVariablesAndIndices> entries) {
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

    public LinkedList<PairSetOfVariablesAndIndices> getEntries() {
        return entries;
    }

    public void setEntries(LinkedList<PairSetOfVariablesAndIndices> entries) {
        this.entries = entries;
    }

    public void setDestination(GAPPVector destination) {
        this.destination = destination;
    }
}
