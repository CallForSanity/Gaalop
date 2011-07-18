package de.gaalop.dfg;

/**
 * This is the base class for all nodes that model operations taking one operand.
 */
public abstract class UnaryOperation extends Expression {

    private Expression operand;

    /**
     * Constructs a new unary operation with one operand.
     *
     * @param operand A dataflow graph modeling the operand for the operation.
     */
    public UnaryOperation(Expression operand) {
        super();
        this.operand = operand;
    }

    /**
     * Gets the operand of this operation.
     *
     * @return The dataflow graph modelling the operand.
     */
    public Expression getOperand() {
        return operand;
    }

    /**
     * @return true
     */
    @Override
    public boolean isComposite() {
        return true;
    }
    
    @Override
    public void replaceExpression(Expression old, Expression newExpression) {
    	if (old == operand) {
    		operand = newExpression;
    	} else {
    		operand.replaceExpression(old, newExpression);
    	}
    }

    /**
     * Compares two unary operations for equality.
     * <p/>
     * Two unary operations are equal if and only if their class is the same and their operand is equal.
     *
     * @param o The other object.
     * @return True if and only if this and the other object are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnaryOperation that = (UnaryOperation) o;

        if (!operand.equals(that.operand)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return operand.hashCode();
    }

    public void setOperand(Expression operand) {
        this.operand = operand;
    }

    
}
