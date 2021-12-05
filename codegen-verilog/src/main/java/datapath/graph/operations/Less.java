package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class Less extends BinaryOperation {

    @Override
    public String getDisplayLabel() {
        return "LESS";
    }

    @Override
    public int getOutputBitsize() {
        return 1;
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }




}
