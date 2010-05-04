package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 * Adds/removes leading bits to/of an operation.
 * Required for bitdwidth extension.
 * @author jh
 */
public class BitwidthTransmogrify extends UnaryOperation {

    @Override
    public int getDelay() {
        // combinatorical op
        return 0;
    }

    @Override
    public int getStageDelay() {
        // combinatorical op
        return 0;
    }

    @Override
    public String getDisplayLabel() {
        if ((getType() != null) && (getData().getType() != null)) {
            return new String("BitwidthTransmogrify " +
                    getData().getType().getBitsize() + "->" +
                    getType().getBitsize());
        } else {
            return new String("BitwidthTransmogrify ");
        }
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }
}
