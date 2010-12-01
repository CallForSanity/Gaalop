package datapath.graph;

import datapath.graph.operations.LoopEnd;
import datapath.graph.operations.Operation;
import datapath.graph.operations.Predicate;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jens
 */
public class GreedySchedule extends Schedule {

    @Override
    public void schedule(Graph graph) {
        s(graph);
    }

    private void s(Graph graph) {
        HashSet<Operation> notScheduled = new HashSet<Operation>();
        HashSet<Predicate> predicates = new HashSet<Predicate>();
        notScheduled.addAll(graph.getOperations());
        while (!notScheduled.isEmpty()) {
            HashSet<Operation> next = nextReady(notScheduled);
            for (Operation op : next) {
                op.setSchedule(allInputReady(op));
                if(op instanceof Predicate)
                    predicates.add((Predicate)op);
            }
            notScheduled.removeAll(next);
        }
        setEnd(graph.getOperations());
        setPredicateDelay(predicates);
    }

    @Override
    public void scheduleAll(Graph graph) {
        for (Graph g : graph.getInnerLoops()) {
            scheduleAll(g);
        }
        schedule(graph);
        (new EqualizeBackedges(graph)).perform();
        (new EqualizeOutput(graph)).perform();
        (new BalanceOutput(graph)).perform();
        StageControllerBuilder.addStageController(graph);
    }

    private HashSet<Operation> nextReady(HashSet<Operation> notScheduled) {
        HashSet<Operation> ready = new HashSet<Operation>();
        for (Operation op : notScheduled) {
            Set<Operation> dependsOn = op.dependsOnOperations(false);
            dependsOn.addAll(op.getPredicates());
            dependsOn.retainAll(notScheduled);
            if (dependsOn.size() == 0) {
                ready.add(op);
            }
        }
        if (ready.size() > 0) {
            return ready;
        }
        throw new RuntimeException("queue not empty but nothing ready");
    }

    private int allInputReady(Operation op) {
        int readyTime = 0;
        HashSet<Operation> input = new HashSet<Operation>();
        input.addAll(op.dependsOnOperations(false));
        input.addAll(op.getPredicates());
        for (Operation inp : input) {
        	assert inp != null;
            assert inp.isFixedDelay() : "This scheduler cannot handle operations with variable delay";
            
            readyTime = Math.max(readyTime, inp.getSchedule() + inp.getDelay() + inp.getStageDelay());
        }
        return readyTime;
    }

    private void setPredicateDelay(Set<Predicate> predicates) {
        for(Predicate p : predicates){
            assert p.getUse().size() == 1;
            int schedule = p.getUse().iterator().next().getSchedule();
            System.out.println("setting predicate "+p+" for " +p.getUse().iterator().next() + " to schedule " +schedule);
            p.setSchedule(schedule);
        }
    }

    private void setEnd(Set<Operation> operations) {
        int lastOp = 0;
        LoopEnd end = null;
        for(Operation op : operations){
            lastOp = Math.max(lastOp, op.getSchedule());
            if(op instanceof LoopEnd)
                end = (LoopEnd)op;
        }
        assert end != null;
        end.setSchedule(lastOp);
        System.out.println("setting end to schedule "+lastOp);
    }
}
