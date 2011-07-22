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

    public GAPPVector(String name) {
        super(name);
    }
    
    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitVector(this, arg);
    }


}
