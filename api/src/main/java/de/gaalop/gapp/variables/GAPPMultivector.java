package de.gaalop.gapp.variables;

/**
 * Represents a multivector in the GAPP IR.
 *
 * @author Christian Steinmetz
 */
public class GAPPMultivector extends GAPPSetOfVariables {

    public GAPPMultivector(String name) {
        super(name);
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitMultivector(this, arg);
    }
}
