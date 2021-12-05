package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class FromOuterLoop extends ParentInput {

    private ToInnerLoop source;

    @Override
    public String getDisplayLabel() {
        return "OuterLoop INPUT " +debugMessage + source ;
    }


    public void setSource(ToInnerLoop source) {
        this.source = source;
    }

    @Override
    public int getOutputBitsize() {
        return source.getOutputBitsize();
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Output getSource() {
        return source;
    }


}
