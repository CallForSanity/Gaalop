package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class models the divison of two values.
 */
public final class Division extends BinaryOperation {

    /**
     * Constructs a division node that models <code>left / right</code>.
     *
     * @param left  A dataflow graph modelling the numerator of the division.
     * @param right A dataflow graph modelling the denominator of the division.
     */
    public Division(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Converts this node to a human readable string.
     *
     * @return The string "left / right" where left and right are the string returned by the toString method of the
     *         left and right operands.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " / " + bracketComposite(getRight());
    }

    @Override
    public Expression copy() {
        return new Division(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls the {@link de.gaalop.dfg.ExpressionVisitor#visit(Division)} method on a visitor.
     *
     * @param visitor The visitor object to call the method on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}
