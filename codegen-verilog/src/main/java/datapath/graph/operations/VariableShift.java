package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 * Shift with variable shift amount
 * @author jh
 */
public class VariableShift extends BinaryOperation {

    private ShiftMode mode;

    public VariableShift(ShiftMode mode) {
        this.mode = mode;
    }

    @Override
    public String getDisplayLabel() {
        return "<<";
    }

    public void setShift(Operation op) {
        setRHS(op);
    }

    public void setData(Operation op) {
        setLHS(op);
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    public ShiftMode getMode() {
        return mode;
    }
}
