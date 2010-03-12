package de.gaalop.dfg;

/**
 * This interface represents a node of a dataflow graph.
 *
 * @author Sebastian Hartte
 */
public interface Expression {
    /**
     * Creates a deep copy of this expression and returns it. We do not use the clone method of the Object
     * class here, because by definition it only creates shallow copies.
     *
     * @return A new expression object that is equal to this one, but that represents a deep copy of this object.
     */
    Expression copy();

    /**
     * Indicates that this expression is composed of other expressions.
     *
     * @return True if this expression is composite.
     */
    boolean isComposite();

    /**
     * This method must be implemented by all <em>concrete</em> classes that implement this interface. It should
     * call a visit method on the visitor that has one parameter, which has the type of the concrete implementing
     * class.
     * <p/>
     * See the Visitor Pattern for more details.
     *
     * @param visitor The object that the visit method should be called on.
     */
    void accept(ExpressionVisitor visitor);
}
