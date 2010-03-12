package de.gaalop.dfg;

/**
 * This class represents a floating point value that is constant.
 *
 * It is often found as a leaf node in data flow graphs.
 * 
 */
public final class FloatConstant implements Expression {

    private final float value;

    /**
     * Constructs a new floating point constant node.
     *
     * @param value The encapsulated floating point value.
     */
    public FloatConstant(float value) {
        super();
        this.value = value;
    }

    @Override
    public Expression copy() {
        return new FloatConstant(value); // FIXME: improve by returning this (immutable)?
    }

    /**
     * @return false
     */
    @Override
    public boolean isComposite() {
        return false;
    }

    /**
     * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(FloatConstant)} on a visitor.
     *
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Gets the floating point value represented by this node.
     * @return The value encapsulated in this node.
     */
    public float getValue() {
        return value;
    }

    /**
     * Converts this node to a human readable string.
     *
     * @return The result of {@link Float#toString(float)} for the value returned by <code>getValue</code>.
     */
    public String toString() {
        return Float.toString(value);
    }

    /**
     * Compares two objects for equality.
     *
     * This object is equal to another object if and only if the other object has the
     * same class as this object and the encapsulated floating point value is the same.
     *
     * @param o The other object.
     * @return True if both objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloatConstant that = (FloatConstant) o;

        if (Float.compare(that.value, value) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (value != +0.0f ? Float.floatToIntBits(value) : 0);
    }
}
