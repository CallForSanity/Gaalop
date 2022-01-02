package de.gaalop.dfg;

/**
 * This class represents the negation of a value.
 */
public final class Negation extends UnaryOperation {
    /**
     * Constructs a new node representing <code>- operand</code>
     * @param operand A dataflow graph modelling the operand that should be negated.
     */
    public Negation(Expression operand) {
        super(operand);
    }

    @Override
    public Expression copy() {
    	return new Negation(getOperand().copy());
    }

    /**
     * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(Negation)} on a visitor.
     *
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Converts this negation node into a human readable representation.
     *
     * @return The string "<code>-(operand)</code>" where operand has been converted to a string using its toString
     * method.
     */
    @Override
    public String toString() {
        return "-(" + getOperand() + ")";
    }
}
