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

    public GAPPVector(String name) {
        super(name);
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitVector(this, arg);
    }


}
