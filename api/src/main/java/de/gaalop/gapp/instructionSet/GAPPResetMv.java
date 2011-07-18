package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.variables.GAPPMultivector;

/**
 * Represents the resetMv command in the GAPP IR.
 *
 * Description from the paper:
 * Zeros all blades of multivector mvdest .
 */
public class GAPPResetMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;

    public GAPPResetMv() {
    }

    public GAPPResetMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitResetMv(this, arg);
    }

    public GAPPMultivector getDestinationMv() {
        return destinationMv;
    }

    public void setDestinationMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
    }

    
}
