package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSignedMultivectorComponent;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Represents the addMv command in the GAPP IR.
 *
 * Description from the paper:
 * Composes the vector (part of a multivector) partdest
 * from selected elements. sel0 , sel1 , up to sel31 , are a
 * blade selectors. Parts and blade selectors are explained
 * below.
 */
public class GAPPSetVector extends GAPPBaseInstruction {

    private GAPPVector destination;
    private GAPPMultivector sourceMv;
    private Selectorset selectorsSrc;

    public GAPPSetVector(GAPPVector destination, GAPPMultivector sourceMv, Selectorset selectorsSrc) {
        this.destination = destination;
        this.sourceMv = sourceMv;
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
