package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class models the inner product as defined by the underlying algebra.
 */
public final class InnerProduct extends BinaryOperation {

    /**
     * Constructs a new node representing the inner product between two operands.
     *
     * @param left The left hand side of the inner product.
     * @param right The right hand side of the inner product.
     */
    public InnerProduct(Expression left,
                        Expression right) {
        super(left, right);
    }

    /**
     * Converts this node into a human readable string representation.
     * @return Returns the string "<code>left . right</code>" where left and right are the two operands converted
     * to strings using their toString methods.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " . " + bracketComposite(getRight());
    }

    @Override
    public Expression copy() {
    	return new InnerProduct(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls the {@link de.gaalop.dfg.ExpressionVisitor#visit(InnerProduct)} method on a visitor.
     *
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}
