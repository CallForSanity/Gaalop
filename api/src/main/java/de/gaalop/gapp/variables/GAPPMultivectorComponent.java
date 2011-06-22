package de.gaalop.gapp.variables;

public class GAPPMultivectorComponent extends GAPPVariableBase {

    protected GAPPMultivector parentMultivector;
    protected int bladeIndex;

    public String prettyPrint() {
        return parentMultivector.getName() + "{" + bladeIndex + "}";
    }

    public GAPPMultivector getParentMultivector() {
        return parentMultivector;
    }

    public int getBladeIndex() {
        return bladeIndex;
    }
}
