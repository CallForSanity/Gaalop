package datapath.graph.operations;

import datapath.graph.Graph;
import datapath.graph.OperationVisitor;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jh
 */
public class Loop extends AryOperation {

    /**
     * The Graph for the loop
     */
    private Graph graph;

    public Loop(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public void replace(Operation oldOp, Operation newOp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDisplayLabel() {
        return "LOOP";
    }

    @Override
    public int getExecutionOrdinal() {
        int max = -1;
        for (Operation op : operands) {
            max = Math.max(max, op.getExecutionOrdinal());
        }
        for (Predicate p : getPredicates()) {
            max = Math.max(max, p.getExecutionOrdinal());
        }
        return max;
    }

    @Override
    public Set<Operation> dependsOnOperations(boolean includeBackedges) {
        HashSet<Operation> ops = new HashSet<Operation>();
        ops.addAll(operands);
        return ops;
    }

    @Override
    public int getOutputBitsize() {
        return 0;
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }




}
