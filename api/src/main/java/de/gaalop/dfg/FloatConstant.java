package de.gaalop.dfg;

/**
 * This class represents a floating point value that is constant.
 * 
 * It is often found as a leaf node in data flow graphs.
 * 
 */
public final class FloatConstant extends Expression {

	private final double value;

	/**
	 * String representation of the value. This is usefull in case it is an parsed value, that the number of post comma digits
	 * specify the precision, e.g. 2.0 means 1 digit precision, while 2.000 means 3 digits. in case the value is not a parsed
	 * value (and results not from a string) the String is generated.
	 */
	private final String valueString;

	/**
	 * Constructs a new floating point constant node.
	 * 
	 * @param value The encapsulated floating point value.
	 */
	public FloatConstant(String value) {
		super();
		this.value = Float.parseFloat(value);
		valueString = value;
	}

	/**
	 * Constructs a new floating point constant node.
	 * 
	 * @param value The encapsulated floating point value.
	 */
	public FloatConstant(double value) {
		super();
		this.value = value;
		valueString = null;
	}

	/**
	 * Constructs a new floating point constant node.
	 * 
	 * @param value The encapsulated floating point value.
	 * @param valueStr String representation of the value. Can be null
	 */
	public FloatConstant(double value, String valueStr) {
		super();
		this.value = value;
		this.valueString = valueStr;
	}

	@Override
	public Expression copy() {
            return new FloatConstant(value, valueString); // FIXME: improve by returning this (immutable)?
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
	
	@Override
	public void replaceExpression(Expression old, Expression newExpression) {
		// nothing to replace
	}

	/**
	 * Gets the floating point value represented by this node.
	 * 
	 * @return The value encapsulated in this node.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Converts this node to a human readable string.
	 * 
	 * @return The result of {@link Double#toString(double)} for the value returned by <code>getValue</code>.
	 */
        @Override
	public String toString() {
		return (valueString == null) ? Double.toString(value) : valueString;
	}

	/**
	 * Compares two objects for equality.
	 * 
	 * This object is equal to another object if and only if the other object has the same class as this object and the
	 * encapsulated floating point value is the same.
	 * 
	 * @param o The other object.
	 * @return True if both objects are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FloatConstant that = (FloatConstant) o;

		if (Double.compare(that.value, value) != 0) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (value != +0.0d ? (int) Double.doubleToLongBits(value) : 0);
	}
}
