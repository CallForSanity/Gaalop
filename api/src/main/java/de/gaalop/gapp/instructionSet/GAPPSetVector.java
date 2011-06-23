package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSignedMultivectorComponent;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.util.Vector;

/**
Composes the multivector part (vector) part	from dest
selected elements. sel0 , sel1 , up to sel31 , are a blade
selectors. Parts and blade selectors are explained below.
 */
public class GAPPSetVector extends GAPPBaseInstruction {

    private GAPPVector destination;
    private GAPPMultivector sourceMv;
    private Selectorset selectorsSrc;

    public GAPPSetVector() {
    }

    public GAPPSetVector(GAPPVector destination, Vector<GAPPSignedMultivectorComponent> selectors) {
        this.destination = destination;
        destination.slots = selectors;
    }

    public GAPPSetVector(GAPPVector destination) {
        this.destination = destination;
    }

    public void add(GAPPSignedMultivectorComponent toAdd) {
        destination.slots.add(toAdd);
    }

    @Override
    public void accept(GAPPVisitor visitor, Object arg) {
        visitor.visitSetVector(this, arg);
    }

    public GAPPVector getDestination() {
        return destination;
    }

    public Selectorset getSelectorsSrc() {
        return selectorsSrc;
    }

    public GAPPMultivector getSourceMv() {
        return sourceMv;
    }

    public void setDestination(GAPPVector destination) {
        this.destination = destination;
    }

    public void setSelectorsSrc(Selectorset selectorsSrc) {
        this.selectorsSrc = selectorsSrc;
    }

    public void setSourceMv(GAPPMultivector sourceMv) {
        this.sourceMv = sourceMv;
    }


}
