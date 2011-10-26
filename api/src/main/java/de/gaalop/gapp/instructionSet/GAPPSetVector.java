package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Represents the setVector command in the GAPP IR.
 */
public class GAPPSetVector extends GAPPBaseInstruction {

    private GAPPVector destination;
    private GAPPMultivector source;
    private Selectorset selectorsSrc;

    public GAPPSetVector(GAPPVector destination, GAPPMultivector source, Selectorset selectorsSrc) {
        this.destination = destination;
        this.source = source;
        this.selectorsSrc = selectorsSrc;
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitSetVector(this, arg);
    }

    public GAPPVector getDestination() {
        return destination;
    }

    public Selectorset getSelectorsSrc() {
        return selectorsSrc;
    }

    public GAPPMultivector getSource() {
        return source;
    }

    public void setDestination(GAPPVector destination) {
        this.destination = destination;
    }

    public void setSelectorsSrc(Selectorset selectorsSrc) {
        this.selectorsSrc = selectorsSrc;
    }

    public void setSource(GAPPMultivector source) {
        this.source = source;
    }
}
