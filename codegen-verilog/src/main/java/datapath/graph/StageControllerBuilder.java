package datapath.graph;

import datapath.graph.operations.Operation;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author jh
 */
public class StageControllerBuilder {

    private Graph graph;
    private ArrayList<Stage> stages;

    private StageControllerBuilder(Graph graph) {
        this.graph = graph;
    }

    public static void addStageController(Graph graph) {
        StageControllerBuilder builder = new StageControllerBuilder(graph);
        builder.build();
    }

    private void build() {
        collectStages();
    }

    private void collectStages() {
        int numStages = graph.getLatestSchedule() + 1;
        stages = new ArrayList<Stage>(numStages);
        for (int i = 0; i < numStages; i++) {
            stages.add(new Stage());
        }
        assert stages.size() == numStages;

        for(Operation op : graph.getOperations())
            stages.get(op.getSchedule()).addOperation(op);

        assert stages.get(numStages-1).numOperations() > 0;
    }

    private class Stage {

        HashSet<Operation> operations;

        Stage() {
            operations = new HashSet<Operation>();
        }

        void addOperation(Operation op){
            operations.add(op);
        }

        int numOperations() {
            return operations.size();
        }
    }
}
