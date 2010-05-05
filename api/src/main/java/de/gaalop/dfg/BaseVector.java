package de.gaalop.dfg;

/**
 * This class represents a base vector of the algebra. The base vectors are usually indexed from 1 to n where n is the
 * dimension of the algebra. In order to support other base vectors, e.g. for the conformal geometric algebra with
 * e<sub>0</sub> or e<sub>&#8734;</sub>, the index is stored as a string.
 * <p/>
 * Base vectors are usually named e<sub>1</sub> to e<sub>n</sub>.
 */
public final class BaseVector extends Expression {

	/** We use a string in order to support indices like einf. */
	private String index;

	/**
	 * Constructs a new Base Vector with the given index. The index is not restricted to a subset of values but should
	 * not be negative.
	 * 
	 * @param index The index of this base vector.
	 */
	public BaseVector(int index) {
		this.index = Integer.toString(index);
	}

	/**
	 * Constructs a new Base Vector with the given string index.
	 * 
	 * @param index index of the base vector, e.g. inf for e<sub>&#8734;</sub>
	 */
	public BaseVector(String index) {
		this.index = index;
	}

	/**
	 * Gets the index of this base vector.
	 * 
	 * @return An integer between 1 and the dimension of the underlying algebra. Can be 0 or inf, too.
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * Tries to estimate the ordering of the given base vector. For vectors with integer index, the output is the
	 * integer itself. For string representations like e_0 or e_inf of the conformal geometric algebra, the
	 * corresponding ordering is used. Other cases are not supported currently. <br>
	 * For 5D conformal algebra, we assume the following ordering: <e1, e2, e3, e_inf, e0>
	 * 
	 * @return order of base vector in blade list
	 */
	public int getOrder() {
		try {
			int i = Integer.parseInt(index);
			if (i == 0) {
				return 5;
			} else {
				return i;
			}
		} catch (NumberFormatException e) {
			if ("inf".equals(index)) {
				return 4;
			}
			throw e;
		}
	}

	@Override
	public Expression copy() {
		return new BaseVector(this.index);
	}

	/**
	 * @return false
	 */
	@Override
	public boolean isComposite() {
		return false;
	}

	/**
	 * Calls the {@link ExpressionVisitor#visit(BaseVector)} method on a visitor object.
	 * 
	 * @param visitor The visitor object to call the method on.
	 */
	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Converts this base vector to a human readable string.
	 * 
	 * @return The string "ei" where <code>i</code> is the index of this base vector as returned by
	 *         <code>getIndex</code>.
	 */
	@Override
	public String toString() {
		return "e" + index;
	}

	/**
	 * Checks two BaseVector objects for equality.
	 * 
	 * @param o The other object.
	 * @return True if this and the other object are of the same class and the base vector index of this and the other
	 *         object are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BaseVector that = (BaseVector) o;

		if (index != that.index)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return index.hashCode();
	}
}
