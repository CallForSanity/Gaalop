package de.gaalop.gapp.importing.parallelObjects;

/**
 * Represents a constant with a value
 * @author Christian Steinmetz
 */
public class Constant extends ParallelObject {

    private float value;

    public Constant(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitConstant(this, arg);
    }

    @Override
    public String toString() {
        return (isNegated() ? "!" : "") + Float.toString(value);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
