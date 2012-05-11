package de.gaalop.dfg;

/**
 * This class represents a node of a dataflow graph.
 * 
 * @author Sebastian Hartte
 */
public abstract class Expression {

	/**
	 * Creates a deep copy of this expression and returns it. We do not use the clone method of the Object class here,
	 * because by definition it only creates shallow copies.
	 * 
	 * @return A new expression object that is equal to this one, but that represents a deep copy of this object.
	 */
	public abstract Expression copy();

	/**
	 * Indicates that this expression is composed of other expressions.
	 * 
	 * @return True if this expression is composite.
	 */
	public abstract boolean isComposite();

	/**
	 * Replaces the old expression recursively by the given new one, iff the old one can be found in the DFG hierarchy.
	 * 
	 * @param old old expression to be replace
	 * @param newExpression new expression to replace old one
	 */
	public abstract void replaceExpression(Expression old, Expression newExpression);

	/**
	 * This method must be implemented by all <em>concrete</em> classes that implement this interface. It should call a
	 * visit method on the visitor that has one parameter, which has the type of the concrete implementing class.
	 * <p/>
	 * See the Visitor Pattern for more details.
	 * 
	 * @param visitor The object that the visit method should be called on.
	 */
	public abstract void accept(ExpressionVisitor visitor);

}
