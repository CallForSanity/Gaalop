package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author Jens
 */
public class LoopEnd extends NaryOperation {

    @Override
    public String getDisplayLabel() {
        return "LOOP END";
    }

    @Override
    public boolean isHardwareOperation() {
        return true;
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }




}
