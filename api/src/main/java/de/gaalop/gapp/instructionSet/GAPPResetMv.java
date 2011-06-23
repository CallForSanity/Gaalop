package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.variables.GAPPMultivector;

/**
Zeros all blades of multivector mvdest .
 */
public class GAPPResetMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;

    public GAPPResetMv() {
    }

    public GAPPResetMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
    }

    @Override
    public void accept(GAPPVisitor visitor, Object arg) {
        visitor.visitResetMv(this, arg);
    }

    public GAPPMultivector getDestinationMv() {
        return destinationMv;
    }

    public void setDestinationMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
    }

    
}
