package datapath.graph;

import datapath.graph.operations.Operation;
import java.util.Comparator;

/**
 *
 * @author jh
 */
public class SortByNumber implements Comparator<Operation> {

    public int compare(Operation o1, Operation o2) {
        return (new Integer(o1.getNumber())).compareTo(o2.getNumber());
    }
}
