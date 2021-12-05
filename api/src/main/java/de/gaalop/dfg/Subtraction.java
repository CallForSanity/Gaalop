package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class models the subtraction of two values.
 */
public final class Subtraction extends BinaryOperation {

    /**
     * Constructs a new subtraction node that models <code>left - right</code>.
     *
     * @param left  The left hand side operand of the subtraction.
     * @param right The right hand side operand of the subtraction.
     */
    public Subtraction(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Converts this node to a human readable representation.
     *
     * @return The string "<code>left - right</code>" where left and right have been converted to
     *         strings using their toString methods.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " - " + bracketComposite(getRight());
    }

    @Override
    public Expression copy() {
        return new Subtraction(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(Subtraction)} on a visitor object.
     *
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}
