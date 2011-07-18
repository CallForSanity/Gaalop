package de.gaalop.gapp.variables;

import java.util.Vector;

/**
 * Represents a multivector in the GAPP IR.
 *
 * A multivector contains components, which are organized in a fixed blade list.
 *
 * @author christian
 */
public class GAPPMultivector extends GAPPVariable {

    public GAPPMultivector(String name) {
        super(name);
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitMultivector(this, arg);
    }


}
