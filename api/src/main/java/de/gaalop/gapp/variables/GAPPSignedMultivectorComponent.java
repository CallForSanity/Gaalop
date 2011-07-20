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
        setSign(sign);
    }

    public GAPPSignedMultivectorComponent(GAPPMultivector parentMultivector, int bladeIndex) {
        super(parentMultivector, bladeIndex);
        setSign((byte) 1);
    }

    @Override
    public String prettyPrint() {
        return ((sign == -1) ? "-" : "") + super.prettyPrint();
    }

    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        //ensure that the sign is either +1 or -1
        if (sign == 1 || sign == -1) {
            this.sign = sign;
        } else {
            System.err.println("sign is neither -1 nor +1");
        }
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitSignedMultivectorComponent(this, arg);
    }
    
}
