package de.gaalop.gapp.variables;

import java.util.Vector;

public class GAPPMultivector extends GAPPVariableBase {

    private Vector<GAPPMultivectorComponent> components;

    public GAPPMultivector(String name) {
        this.name = name;
    }

    public Vector<GAPPMultivectorComponent> getComponents() {
        return components;
    }

    public void setComponents(Vector<GAPPMultivectorComponent> components) {
        this.components = components;
    }

    public String prettyPrint() {
        return name;
    }
}
