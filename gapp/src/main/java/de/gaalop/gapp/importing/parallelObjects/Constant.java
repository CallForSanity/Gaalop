package de.gaalop.gapp.importing.parallelObjects;

/**
 * Represents a constant with a value
 * @author Christian Steinmetz
 */
public class Constant extends ParallelObject {

    private double value;

    public Constant(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitConstant(this, arg);
    }

    @Override
    public String toString() {
        return (isNegated() ? "!" : "") + Double.toString(value);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
