package de.gaalop.dfg;

/**
 * This is the abstract base class for all operations that take two operands.
 */
public abstract class BinaryOperation extends Expression {

    private Expression left;
    private Expression right;

    /**
     * Constructs a new binary operation with two operands.
     *
     * @param left The left side operand.
     * @param right The right side operand.
     */
    public BinaryOperation(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    /**
     * Gets the left operand of this binary operation.
     *
     * @return The dataflow graph modelling the left operand of this operation.
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Gets the right operand of this binary operation.
     *
     * @return The dataflow graph modelling the right operand of this operation.
     */
    public Expression getRight() {
        return right;
    }
    
    @Override
    public void replaceExpression(Expression old, Expression newExpression) {
    	if (old == left) {
    		left = newExpression;
    	} else if (left.isComposite()) {
    		left.replaceExpression(old, newExpression);
    	}
    	
    	if (old == right) {
    		right = newExpression;
    	} else if (right.isComposite()) {
    		right.replaceExpression(old, newExpression);
    	}
    	
    }

    /**
     * @return true
     */
    @Override
    public boolean isComposite() {
        return true;
    }

    /**
     * Checks two binary operations for equality.
     *
     * Two binary operations are equal if their class is equal and both operands are equal.
     *
     * @param o The other object.
     * @return True if the binary operations are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryOperation that = (BinaryOperation) o;

        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        if (right != null ? !right.equals(that.right) : that.right != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

}
