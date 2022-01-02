package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class Nop extends UnaryOperation {

    @Override
    public int getOutputBitsize() {
        if(getType() != null) {
            return getType().getBitsize();
        }
        return getData().getOutputBitsize();
    }

    @Override
    public int getExecutionOrdinal() {
        return getData().getExecutionOrdinal();
    }

    @Override
    public String getDisplayLabel() {
        return "NOP";
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }
}
