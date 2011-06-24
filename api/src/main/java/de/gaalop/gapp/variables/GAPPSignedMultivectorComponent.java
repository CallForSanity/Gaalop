package de.gaalop.gapp.variables;

/**
 * Represents a signed multivector component in the GAPP IR.
 *
 * @author christian
 */
public class GAPPSignedMultivectorComponent extends GAPPMultivectorComponent {

    private byte sign;

    public GAPPSignedMultivectorComponent(GAPPMultivector parentMultivector, int bladeIndex, byte sign) {
        super(parentMultivector, bladeIndex);
        this.sign = sign;
    }

    public GAPPSignedMultivectorComponent(GAPPMultivector parentMultivector, int bladeIndex) {
        super(parentMultivector, bladeIndex);
        sign = 1;
    }

    @Override
    public String prettyPrint() {
        return ((sign == -1) ? "-" : "") + super.prettyPrint();
    }

    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }
    
}
