package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class represents the algebraic product of the underlying algebra. In the simplest case of two
 * scalar values, this corresponds to the normal multiplication operation.
 */
public final class Multiplication extends BinaryOperation {

    /**
     * Constructs a multiplication node that represents <code>left * right</code>.
     *
     * @param left  The left hand side operand.
     * @param right The right hand side operand.
     */
    public Multiplication(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Converts this multiplication to a human readable string representation.
     *
     * @return The string "<code>left * right</code>" where left and right have both been converted to
     *         strings using their toString methods.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " * " + bracketComposite(getRight());
    }

    @Override
    public Expression copy() {
    	return new Multiplication(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(Multiplication)} on a visitor.
     *
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}
