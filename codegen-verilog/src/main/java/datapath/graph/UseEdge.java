package datapath.graph;

import datapath.graph.operations.Operation;

/**
 *
 * @author jh
 */
public class UseEdge implements Comparable<UseEdge> {

    private Operation source;
    private Operation target;

    public UseEdge(Operation source, Operation target) {
        this.source = source;
        this.target = target;
    }

    public int distance() {
        return Graph.getDistance(source, target);
    }

    public boolean isBackedge() {
        return Graph.isBackEdge(source, target);
    }

    public int compareTo(UseEdge o) {
        int thisDistance = distance();
        int otherDistance = o.distance();

        if (thisDistance > otherDistance) {
            return 1;
        }
        if (thisDistance < otherDistance) {
            return -1;
        }
        if (source.getNumber() > o.source.getNumber() || target.getNumber() >
                o.target.getNumber()) {
            return 1;
        }
        if (source.getNumber() < o.source.getNumber() || target.getNumber() <
                o.target.getNumber()) {
            return -1;
        }
        return 0;
    }

    public Operation getSource() {
        return source;
    }

    public Operation getTarget() {
        return target;
    }
}
