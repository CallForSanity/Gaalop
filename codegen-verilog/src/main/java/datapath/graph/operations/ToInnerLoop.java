package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class ToInnerLoop extends Output {

    private FromOuterLoop target;

    @Override
    public String getDisplayLabel() {
        return "Inner Loop INPUT";
    }

    public void setTarget(FromOuterLoop target) {
        this.target = target;
    }

    @Override
    public int getOutputBitsize() {
        return getData().getOutputBitsize();
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }




}
