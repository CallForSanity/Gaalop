package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class models the equality relation of two values.
 */
public final class Equality extends BinaryOperation {

    /**
     * Constructs a new node that models <code>left == right</code>.
     * 
     * @param left The left operand of the equality.
     * @param right The right operand of the equality.
     */
    public Equality(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Converts this node into a human readable string.
     *
     * @return The string "<code>left</code> == <code>right</code>", where left and right are the results of the toString
     * methods of the left and right operand.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " == " + bracketComposite(getRight());
    }

    @Override
    public Expression copy() {
    	return new Equality(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls the {@link ExpressionVisitor#visit(Equality)} method on a visitor object.
     *
     * @param visitor The visitor object to call the method on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    
}
