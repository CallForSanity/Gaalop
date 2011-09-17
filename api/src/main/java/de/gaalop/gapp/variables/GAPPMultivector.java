package de.gaalop.gapp.variables;

/**
 * Represents a multivector in the GAPP IR.
 *
 * @author Christian Steinmetz
 */
public class GAPPMultivector extends GAPPVariable {

    private GAPPValueHolder[] blades;

    public GAPPMultivector(String name, GAPPValueHolder[] blades) {
        super(name);
        this.blades = blades;
    }

    public GAPPValueHolder[] getBlades() {
        return blades;
    }

    public void setBlades(GAPPValueHolder[] blades) {
        this.blades = blades;
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitMultivector(this, arg);
    }

}
