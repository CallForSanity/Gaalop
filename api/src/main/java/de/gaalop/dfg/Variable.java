package de.gaalop.dfg;

/**
 * This class represents a dataflow graph node that models a variable.
 */
public class Variable extends Expression {

	protected String name;

	/**
	 * Define the value range of this variable. In case it is not know, it is set to null
	 */
	protected String minValue = null;

	public String getMaxValue() {
		return maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	/**
	 * Define the value range of this variable. In case it is not know, it is set to null
	 */
	protected String maxValue = null;

	private boolean global;

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	/**
	 * Constructs a new node modeling a named variable.
	 * 
	 * @param name The name of the variable.
	 */
	public Variable(String name) {
		if (name.startsWith("::") && name.length() > 2) {
			this.global = true;
			this.name = name.substring(2);
		} else {
			this.name = name;
		}
	}
	
	public boolean globalAccess() {
		return global;
	}

	/**
	 * Gets the name of the variable.
	 * 
	 * @return The string containing this variables name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Converts this variable node into a human readable string.
	 * 
	 * @return A string containing the name of this variable.
	 */
        @Override
	public String toString() {
		return name;
	}

        @Override
	public Variable copy() {
		Variable v = new Variable(this.name);
		v.global = global;
		v.setMaxValue(this.getMaxValue());
		v.setMinValue(this.getMinValue());
		return v;
	}

	/**
	 * @return false
	 */
	@Override
	public boolean isComposite() {
		return false;
	}

	/**
	 * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(Variable)} on a visitor.
	 * 
	 * @param visitor The object that the visit method should be called on.
	 */
	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceExpression(Expression old, Expression newExpression) {

		if (old instanceof Variable && newExpression instanceof Variable) {
			Variable oldVar = (Variable) old;
			Variable newVar = (Variable) newExpression;
			if (oldVar.getName().equals(name) && oldVar.getMinValue().equals(minValue) && oldVar.getMaxValue().equals(maxValue)) {
				name = newVar.getName();
				minValue = newVar.getMinValue();
				maxValue = newVar.getMaxValue();
			}
		}
	}

	/**
	 * Compares two variables for equality.
	 * 
	 * Two variables are equal if and only if the two variables have the same class and their name is equal.
	 * 
	 * @param o The other object.
	 * @return True if and only if this object is equal to the other object.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Variable variable = (Variable) o;

		if (!name.equals(variable.name))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
