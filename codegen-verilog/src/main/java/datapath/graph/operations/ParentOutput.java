package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class ParentOutput extends Output {
   public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }
}
