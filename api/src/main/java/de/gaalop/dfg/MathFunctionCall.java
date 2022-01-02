package de.gaalop.dfg;

/**
 * This operation represents a mathematical function with no side effects and takes on operand.
 */
public final class MathFunctionCall extends UnaryOperation {

	private MathFunction function;

	/**
	 * Constructs a new node that represents the application of a mathematical function.
	 * 
	 * @param operand The single parameter for the function.
	 * @param function The type of mathematical function this node represents.
	 */
	public MathFunctionCall(Expression operand, MathFunction function) {
		super(operand);
		this.function = function;
	}

	/**
	 * Gets the type of mathematical function this node represents.
	 * 
	 * @return An enumeration literal identifying the mathematical function.
	 */
	public MathFunction getFunction() {
		return function;
	}

	@Override
	public Expression copy() {
            return new MathFunctionCall(getOperand().copy(), function);
	}

	/**
	 * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(MathFunctionCall)} on a visitor.
	 * 
	 * @param visitor The object that the visit method should be called on.
	 */
	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Converts this node to a human readable string representation.
	 * 
	 * @return The string "<code>function(operand)</code>" where function and operand are both converted to strings using their
	 * toString methods.
	 */
        @Override
	public String toString() {
		return function.toString().toLowerCase() + "(" + getOperand() + ")";
	}

	/**
	 * Compares two math function calls for equality.
	 * 
	 * Math function calls are equal if and only if they have the same class, their operands are equal and the function type is
	 * equal.
	 * 
	 * @param o The other object.
	 * @return True if and only if this object is equal to the other object.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		MathFunctionCall that = (MathFunctionCall) o;

		if (function != that.function) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (function != null ? function.hashCode() : 0);
		return result;
	}
}
