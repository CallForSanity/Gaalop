package datapath.graph.display.dot;

import datapath.graph.operations.Operation;

/**
 *
 * @author jh
 */
public class DisplayNode extends datapath.graph.display.DisplayNode {

    public DisplayNode(Operation op) {
        super(op);
    }

    public String getDisplayLabel() {
        return op.getDisplayLabel();
    }

    public String getID() {
        return "op"+op.getNumber();
    }

    public Operation getOperation() {
      return op;
    }

}
