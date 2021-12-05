package datapath.graph;

import datapath.graph.operations.Operation;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author jh
 */
public class EqualizeBackedges extends Optimization {

    public EqualizeBackedges(Graph graph) {
        super(graph);
    }

    @Override
    public void perform() {
        SortedSet<UseEdge> backedges =  new TreeSet<UseEdge>(collectBackedges());
        if(backedges.size() < 2)
            return;
        assert backedges.first().isBackedge();
        assert backedges.last().isBackedge();
        if(backedges.first().distance() == backedges.last().distance())
            return;
        int longestDistance = backedges.last().distance();
        System.out.println("must equalize backedges");
        System.out.println("longest distance: "+longestDistance);
        System.out.println("shortest distance: "+backedges.first().distance());
        for(UseEdge backedge : backedges){
            if(backedge.distance() < longestDistance) {
                int diff = longestDistance - backedge.distance();
                int newSchedule = backedge.getSource().getSchedule() + diff;
                backedge.getSource().setSchedule(newSchedule);
            }
        }
    }

    private Set<UseEdge> collectBackedges() {
        HashSet<UseEdge> backedges = new HashSet();
        for(Operation source : graph.getOperations()){
            for(Operation target : source.getUse()) {
                if(Graph.isBackEdge(source, target)){
                    UseEdge backedge = new UseEdge(source, target);
                    backedges.add(backedge);
                }

            }
        }
        return backedges;
    }

}
