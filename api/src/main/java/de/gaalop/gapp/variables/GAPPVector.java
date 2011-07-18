package de.gaalop.gapp.variables;

import java.util.Vector;

/**
 * Represents a vector in the GAPP IR.
 *
 * A vector contains components, which are not organized in fixed order
 *
 * @author christian
 */
public class GAPPVector extends GAPPVariable {

    public Vector<GAPPSignedMultivectorComponent> slots;

    public GAPPVector(String name, Vector<GAPPSignedMultivectorComponent> slots) {
        super(name);
        this.slots = slots;
    }

    public GAPPVector(String name) {
        super(name);
        this.slots = new Vector<GAPPSignedMultivectorComponent>();
    }

    public GAPPSignedMultivectorComponent getComponent(int index) {
        return slots.get(index);
    }

    public void add(GAPPSignedMultivectorComponent comp) {
        slots.add(comp);
    }

    public String prettyPrint() {
        return name;
    }

    public int getSize() {
        return slots.size();
    }

    public void setSize(int size) {
        slots.setSize(size);
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitVector(this, arg);
    }




    
}
