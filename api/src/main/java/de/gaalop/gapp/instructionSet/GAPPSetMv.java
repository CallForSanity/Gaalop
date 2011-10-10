package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSetOfVariables;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Represents the setMv command in the GAPP IR.
 */
public class GAPPSetMv extends GAPPBaseInstruction {

    private GAPPMultivector destination;
    private GAPPSetOfVariables source;
    private PosSelectorset selectorsDest;
    private Selectorset selectorsSrc;

    public GAPPSetMv(GAPPMultivector destination, GAPPSetOfVariables source, PosSelectorset selectorsDest, Selectorset selectorsSrc) {
        this.destination = destination;
        this.source = source;
        this.selectorsDest = selectorsDest;
        this.selectorsSrc = selectorsSrc;
        assert (selectorsDest.size() == selectorsSrc.size());

    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitSetMv(this, arg);
    }

    public GAPPMultivector getDestination() {
        return destination;
    }

    public PosSelectorset getSelectorsDest() {
        return selectorsDest;
    }

    public Selectorset getSelectorsSrc() {
        return selectorsSrc;
    }

    public GAPPSetOfVariables getSource() {
        return source;
    }

    public void setDestination(GAPPMultivector destination) {
        this.destination = destination;
    }

    public void setSelectorsDest(PosSelectorset selectorsDest) {
        this.selectorsDest = selectorsDest;
    }

    public void setSelectorsSrc(Selectorset selectorsSrc) {
        this.selectorsSrc = selectorsSrc;
    }

    public void setSource(GAPPSetOfVariables source) {
        this.source = source;
    }

}
