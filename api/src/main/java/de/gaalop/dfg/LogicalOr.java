package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class models the logical or operation of two (binary) values.
 */
public final class LogicalOr extends BinaryOperation {

    /**
     * Constructs a new node that models <code>left || right</code>.
     * 
     * @param left The left operand of the or operation.
     * @param right The right operand of the or operation.
     */
    public LogicalOr(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Converts this node into a human readable string.
     *
     * @return The string "<code>left</code> || <code>right</code>", where left and right are the results of the toString
     * methods of the left and right operand.
     */
    @Override
    public String toString() {
        return bracketComposite(getLeft()) + " || " + bracketComposite(getRight());
    }

    @Override
    public Expression copy() {
        return new LogicalOr(getLeft().copy(), getRight().copy());
    }

    /**
     * Calls the {@link ExpressionVisitor#visit(LogicalOr)} method on a visitor object.
     *
     * @param visitor The visitor object to call the method on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    
}
