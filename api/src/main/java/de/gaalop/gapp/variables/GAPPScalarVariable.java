package de.gaalop.gapp.variables;

/**
 * Represents a scalar in the GAPP IR.
 *
 * @author christian
 */
public class GAPPScalarVariable extends GAPPVariable {

    public GAPPScalarVariable(String name) {
        super(name);
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitScalarVariable(this, arg);
    }

}
