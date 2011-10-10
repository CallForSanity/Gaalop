package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.variables.GAPPMultivector;

/**
 * Represents the resetMv command in the GAPP IR.
 *
 * A member "size" stores the number of elements
 * of the multivector that should be created
 */
public class GAPPResetMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;
    private int size;

    public GAPPResetMv(GAPPMultivector destinationMv, int size) {
        this.destinationMv = destinationMv;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
