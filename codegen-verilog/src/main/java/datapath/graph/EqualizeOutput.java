package datapath.graph;

import datapath.graph.operations.ParentOutput;

/**
 * This optimization sets the schedule of all data output operations on the
 * to the last cycle of the of scheduled graph.
 * @author jh
 */
public class EqualizeOutput extends Optimization {

    public EqualizeOutput(Graph graph) {
        super(graph);
    }

    @Override
    public void perform() {
        int lastOp = graph.getLatestSchedule();

        for(ParentOutput out :graph.getOutput()){
            out.setSchedule(lastOp);
        }
    }

}
