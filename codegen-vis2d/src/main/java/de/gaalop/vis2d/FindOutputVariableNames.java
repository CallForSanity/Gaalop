package de.gaalop.vis2d;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.StoreResultNode;
import java.util.LinkedList;

/**
 * Collects the names of all output variables in a control flow graph
 * @author Christian Steinmetz
 */
public class FindOutputVariableNames extends EmptyControlFlowVisitor {
    
    private LinkedList<String> variableNames = new LinkedList<>();

    private FindOutputVariableNames() {
    }

    @Override
    public void visit(StoreResultNode node) {
        variableNames.add(node.getValue().getName());
        super.visit(node); 
    }
    
    /**
     * Collects the names of all output variables in a control flow graph and returns the list
     * @param graph The control flow graph
     * @return The names of all output variables in the given control flow graph
     */
    public static LinkedList<String> getVariableNames(ControlFlowGraph graph) {
        FindOutputVariableNames findOutputVariableNames = new FindOutputVariableNames();
        graph.accept(findOutputVariableNames);
        return findOutputVariableNames.variableNames;
    }
    
    
    
}
