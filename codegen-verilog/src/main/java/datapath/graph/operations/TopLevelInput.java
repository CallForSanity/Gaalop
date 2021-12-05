package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author Jens
 */
public class TopLevelInput extends ParentInput {

    private HWInput source;

    public void setSource(HWInput source){
        this.source = source;
    }

    @Override
    public Operation getSource() {
        return source;
    }

    @Override
    public int getDelay() {
        // virtual operation -> no delay
        return 0;
    }

    @Override
    public int getStageDelay() {
        //return super.getStageDelay();
        return 0;
    }



    @Override
    public String getDisplayLabel() {
        return "TopLevelInput "+getName();
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getOutputBitsize() {
        if(getType() != null){
            return getType().getBitsize();
        }
        return source.getOutputBitsize();
    }






}
