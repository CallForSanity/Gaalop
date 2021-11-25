package de.gaalop.cfg;

import de.gaalop.StringList;
import java.util.HashSet;

/**
 * Collects and stores all output variables (the variables that are inside a StoreResultNode) of a Control Flow Graph.
 * @author Christian Steinmetz
 */
public class OutputsCollector extends EmptyControlFlowVisitor {
    
    public HashSet<String> outputs = new HashSet<>();
    
    @Override
    public void visit(StoreResultNode node) {
        outputs.add(node.getValue().getName());
        super.visit(node); 
    }   
    
    /**
     * Collects all all output variables and returns them as a HashSet<String>.
     * @param graph The control flow graph
     * @return The set of output variables
     */
    public static HashSet<String> getOutputsFromGraph(ControlFlowGraph graph) {
        OutputsCollector visitor = new OutputsCollector();
        graph.accept(visitor);
        return visitor.outputs;
    }
    
    /**
     * Returns a sorted list of output variables in a given Control Flow Graph
     * @param graph The control flow graph
     * @return The sorted (alphabetical with ignoring case) list of output variables
     */
    public static StringList getOutputsFromGraphAsSortedList(ControlFlowGraph graph) {
        StringList result = new StringList(getOutputsFromGraph(graph));
        result.sortIgnoringCase();
        return result;
    }
}
