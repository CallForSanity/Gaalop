package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class FromInnerLoop extends Input {

    private ToOuterLoop source;

    @Override
    public String getDisplayLabel() {
        return "InnerLoop OUTPUT " +source.getOutputBitsize();
    }

    public void setSource(ToOuterLoop source) {
        this.source = source;
    }

    @Override
    public int getOutputBitsize() {
        return source.getOutputBitsize();
    }

    @Override
    public int getDelay() {
        return 0;
    }
    
    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }




}
