package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author Jens
 */
public class HWOutput extends ParentOutput {

    @Override
    public String getDisplayLabel() {
       return "HWOutput "+getName();
    }
    
    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }


}
