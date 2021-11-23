package de.gaalop.gapp.variables;

/**
 * Represents a multivector component in the GAPP IR.
 *
 * @author Christian Steinmetz
 */
public class GAPPMultivectorComponent extends GAPPVariable {

    protected int bladeIndex;

    public GAPPMultivectorComponent(String parentName, int bladeIndex) {
        super(parentName);
        this.bladeIndex = bladeIndex;
    }

    @Override
    public String prettyPrint() {
        return name + "[" + bladeIndex + "]";
    }

    public int getBladeIndex() {
        return bladeIndex;
    }

    public void setBladeIndex(int bladeIndex) {
        this.bladeIndex = bladeIndex;
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitMultivectorComponent(this, arg);
    }
}
