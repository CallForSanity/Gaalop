package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class MemWrite extends BinaryOperation {

    @Override
    public String getDisplayLabel() {
        return "MemWrite";
    }

    @Override
    public void addUse(Operation op) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int getExecutionOrdinal() {
        int max = Math.max(lhs.getExecutionOrdinal(), rhs.getExecutionOrdinal());
        return max;
    }

    public void setData(Operation op){
        setRHS(op);
    }

    public void setAddress(Operation op) {
        setLHS(op);
    }

    public Operation getData() {
        return getRhs();
    }

    public Operation getAddress() {
        return getLhs();
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }




}
