package de.gaalop.gapp.variables;

/**
 * Represents a scalar in the GAPP IR.
 *
 * A multivector contains components, which are organized in a fixed blade list.
 *
 * @author christian
 */
public class GAPPScalarVariable extends GAPPVariable {

    public GAPPScalarVariable(String name) {
        super(name);
    }

    @Override
    public String prettyPrint() {
        return name;
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitScalarVariable(this, arg);
    }

}
