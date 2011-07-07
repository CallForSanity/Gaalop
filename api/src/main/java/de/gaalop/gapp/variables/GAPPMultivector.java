package de.gaalop.gapp.variables;

import java.util.Vector;

/**
 * Represents a multivector in the GAPP IR.
 *
 * A multivector contains components, which are organized in a fixed blade list.
 *
 * @author christian
 */
public class GAPPMultivector extends GAPPVariableBase {

    private Vector<GAPPMultivectorComponent> components;

    public GAPPMultivector(String name) {
        super(name);
        components = new Vector<GAPPMultivectorComponent>();
    }

    public Vector<GAPPMultivectorComponent> getComponents() {
        return components;
    }

    public void setComponents(Vector<GAPPMultivectorComponent> components) {
        this.components = components;
    }


}
