package datapath.graph;

import datapath.graph.operations.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Inserts ShiftRegisters balance the use of operation results in scheduled graphs
 * @author jh
 */
public class BalanceOutput extends Optimization {

    public BalanceOutput(Graph graph) {
        super(graph);
    }

    @Override
    public void perform() {
        for (Operation op : graph.getOperations().toArray(new Operation[0])) {
            balanceUse(op);
        }
    }

    private void balanceUse(Operation op) {
        Set<Operation> use = op.getUse();
        if (use.size() <= 1) {
            return;
        }
        SortedSet<UseEdge> uses = new TreeSet<UseEdge>();
        for(Operation x : use) {
            boolean added = uses.add(new UseEdge(op, x));
            assert added;
        }
        if(uses.first().distance() == uses.last().distance())
            return;
//        System.out.println("must balance:"+op);
//        System.out.println();
        Set<UseEdge> shortestDistance = edgesWithShortestDistance(uses);
        int dist = shortestDistance.iterator().next().distance();
        uses.removeAll(shortestDistance);

        Nop nop = new Nop();
        for(UseEdge e : uses){
            e.getTarget().replace(e.getSource(), nop);
        }
        nop.setSchedule(op.getSchedule()+op.getDelay()+dist);
        nop.setData(op);
        nop.setExecutionOrdinal(op.getExecutionOrdinal());
        graph.addOperation(nop);
        balanceUse(nop);
    }

    private Set<UseEdge> edgesWithShortestDistance(SortedSet<UseEdge> uses){
        HashSet<UseEdge> shortest = new HashSet(uses);
        UseEdge firstLonger = null;
        for(UseEdge e : uses){
            if(e.distance() > uses.first().distance()) {
                firstLonger = e;
                break;
            }
        }
        assert firstLonger != null;
        shortest.removeAll(uses.tailSet(firstLonger));
        return shortest;
    }

    
}
