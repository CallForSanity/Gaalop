package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This expression type represents the Wedge operator in geometric algebra.
 *
 * @author Sebastian Hartte
 */
public final class OuterProduct extends BinaryOperation {

    /**
     * Constructs a new node that represents <code>left ^ right</code>.
     *
     * @param left  The left hand side of the outer product.
     * @param right The right hand side of the outer product.
     */
    public OuterProduct(Expression left,
                        Expression right) {
        super(left, right);
    }

    /**
     * Converts this node into a human readable representation.
     *
     * @return The string "left ^ right" where both left and right have been converted to strings using their
     *         toString methods.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " ^ " + bracketComposite(getRight());
    }

    @Override
    public Expression copy() {
    	return new OuterProduct(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls the {@link de.gaalop.dfg.ExpressionVisitor#visit(OuterProduct)} method on a visitor.
     *
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}
