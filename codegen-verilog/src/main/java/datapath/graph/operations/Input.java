package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class Input extends NaryOperation {
     private String name;
 
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override 
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

}
