package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.variables.GAPPMultivector;

/**
 * Represents the addMv command in the GAPP IR.
 *
 * Description from the paper:
 * Adds the selected blades from multivector mvsrc to 
 * multivector mvdest . All non-selected blades remain
 * unmodiﬁed. This is restricted to one source and one destination
 * multivector.
 */
public class GAPPAddMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;
    private GAPPMultivector sourceMv;
    private Selectorset selectorsDest;
    private Selectorset selectorsSrc;

    public GAPPAddMv(GAPPMultivector destinationMv, GAPPMultivector sourceMv, Selectorset selectorsDest, Selectorset selectorsSrc) {
        this.destinationMv = destinationMv;
        this.sourceMv = sourceMv;
        this.selectorsDest = selectorsDest;
        this.selectorsSrc = selectorsSrc;
        assert (selectorsDest.size() == selectorsSrc.size());
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitAddMv(this, arg);
    }

    public GAPPMultivector getDestinationMv() {
        return destinationMv;

    }

    public Selectorset getSelectorsDest() {
        return selectorsDest;
    }

    public Selectorset getSelectorsSrc() {
        return selectorsSrc;
    }

    public GAPPMultivector getSourceMv() {
        return sourceMv;
    }

    public void setDestinationMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
    }

    public void setSelectorsDest(Selectorset selectorsDest) {
        this.selectorsDest = selectorsDest;
    }

    public void setSelectorsSrc(Selectorset selectorsSrc) {
        this.selectorsSrc = selectorsSrc;
    }

    public void setSourceMv(GAPPMultivector sourceMv) {
        this.sourceMv = sourceMv;
    }

    

}
