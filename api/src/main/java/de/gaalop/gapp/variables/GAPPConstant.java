package de.gaalop.gapp.variables;

/**
 * Class to store a constant with a float value
 * @author Christian Steinmetz
 */
public class GAPPConstant extends GAPPValueHolder {

    private double value;

    public GAPPConstant(double value) {
        this.value = value;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String prettyPrint() {
        return Double.toString(value);
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitConstant(this, arg);
    }
}
