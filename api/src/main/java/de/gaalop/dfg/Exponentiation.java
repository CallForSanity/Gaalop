package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class represents the exponentiation operation.
 * <p/>
 * E.g. x^2 would be represented as a node of this class with the two operands:
 * <ol>
 * <li>The variable node x</li>
 * <li>The constant node 2</li>
 * </ol>
 */
public final class Exponentiation extends BinaryOperation {
    /**
     * Constructs a new exponentation node that models <code>left<sup>right</sup></code>.
     * @param left The basis of the exponentation.
     * @param right The exponent of the exponentation.
     */
    public Exponentiation(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression copy() {
        return new Exponentiation(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls the {@link de.gaalop.dfg.ExpressionVisitor#visit(Exponentiation)} method on a visitor.
     *
     * @param visitor The visitor to call the method on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Converts this node to a human readable string.
     *
     * @return The string "<code>left ^ right</code>" where left and right are the two operands converted to strings
     * using their toString methods.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " ^ " + bracketComposite(getRight());
    }
}
