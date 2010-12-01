package de.gaalop.dfg;

/**
 * This class represents the reverse in the clifford algebra. This operation reverses the order of the blades in a
 * multivector.
 */
public class Reverse extends UnaryOperation {
    /**
     * Constructs a new node that represents the reverse of an operand.
     *
     * @param operand A dataflow graph modelling the operand for the operation.
     */
    public Reverse(Expression operand) {
        super(operand);
    }

    @Override
    public Expression copy() {
        return new Reverse(getOperand().copy());
    }

    /**
     * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(Reverse)} on a visitor.
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Converts this operation into a human readable string representation.
     *
     * @return The string "~(operand)" where operand has been converted to a string using its
     * toString method.
     */
    @Override
    public String toString() {
        return "~(" + getOperand() + ")";
    }
}
