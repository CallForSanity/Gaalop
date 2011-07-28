package de.gaalop.gapp.variables;

/**
 * Represents a multivector component in the GAPP IR.
 *
 * @author christian
 */
public class GAPPMultivectorComponent extends GAPPVariable {

    protected GAPPMultivector parent;
    protected int bladeIndex;

    public GAPPMultivectorComponent(GAPPMultivector parent, int bladeIndex) {
        super(parent.getName());
        this.parent = parent;
        this.bladeIndex = bladeIndex;
    }

    @Override
    public String prettyPrint() {
        return parent.getName() + "{" + bladeIndex + "}";
    }
/*
    public GAPPMultivector getParent() {
        return parent;
    }

    public void setParent(GAPPMultivector parent) {
        this.parent = parent;
    }
*/
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
