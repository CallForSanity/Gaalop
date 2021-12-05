package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class LoopInit extends NaryOperation {

    @Override
    public String getDisplayLabel() {
        return "LOOP INIT";
    }

    @Override
    public int getOutputBitsize() {
        return 1;
    }

    @Override
    public boolean isHardwareOperation() {
        return true;
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getDelay() {
        return 0;
    }


}
