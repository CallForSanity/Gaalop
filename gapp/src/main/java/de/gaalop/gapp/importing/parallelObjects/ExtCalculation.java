package de.gaalop.gapp.importing.parallelObjects;

import de.gaalop.gapp.instructionSet.CalculationType;

/**
 *
 * @author Christian Steinmetz
 */
public class ExtCalculation extends ParallelObject {

    private CalculationType type;
    private ParallelObject operand1;
    private ParallelObject operand2;

    public ExtCalculation(CalculationType type, ParallelObject operand1, ParallelObject operand2) {
        this.type = type;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public ParallelObject getOperand1() {
        return operand1;
    }

    public ParallelObject getOperand2() {
        return operand2;
    }

    public CalculationType getType() {
        return type;
    }

    public void setOperand1(ParallelObject operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(ParallelObject operand2) {
        this.operand2 = operand2;
    }

    public void setType(CalculationType type) {
        this.type = type;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitExtCalculation(this, arg);
    }

    @Override
    public String toString() {
        return type+","+operand1+","+operand2;
    }

}
