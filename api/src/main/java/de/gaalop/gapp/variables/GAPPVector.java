package de.gaalop.gapp.variables;

import java.util.LinkedList;

/**
 * Represents a vector in the GAPP IR.
 *
 * A vector contains components, which are not organized in fixed order
 *
 * @author Christian Steinmetz
 */
public class GAPPVector extends GAPPVariable {

    private LinkedList<GAPPValueHolder> slots;

    public GAPPVector(String name) {
        super(name);
        slots = new LinkedList<GAPPValueHolder>();
    }

    public GAPPVector(String name, LinkedList<GAPPValueHolder> slots) {
        super(name);
        this.slots = slots;
    }

    public LinkedList<GAPPValueHolder> getSlots() {
        return slots;
    }

    public void setSlots(LinkedList<GAPPValueHolder> slots) {
        this.slots = slots;
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitVector(this, arg);
    }


}
