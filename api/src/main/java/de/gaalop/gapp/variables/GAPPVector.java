package de.gaalop.gapp.variables;

import java.util.Vector;

public class GAPPVector extends GAPPVariableBase {

    public Vector<GAPPSignedMultivectorComponent> slots;

    public GAPPSignedMultivectorComponent getComponent(int index) {
        return slots.get(index);
    }

    public int getSize() {
        return slots.size();
    }

    public void setSize(int size) {
        slots.setSize(size);
    }

    public GAPPVector(String name, Vector<GAPPSignedMultivectorComponent> slots) {
        this.slots = slots;
        this.name = name;
    }

    public GAPPVector(String name) {
        this.slots = new Vector<GAPPSignedMultivectorComponent>();
        this.name = name;
    }

    public void add(GAPPSignedMultivectorComponent comp) {
        slots.add(comp);
    }

    public String prettyPrint() {
        return name;
    }

    public void incSize() {
        slots.add(new GAPPSignedMultivectorComponent());

    }
}
