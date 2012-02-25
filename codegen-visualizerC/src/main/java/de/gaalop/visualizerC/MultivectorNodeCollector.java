package de.gaalop.visualizerC;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Collects all nodes in a control flow graph, which contain multivectors
 * @author christian
 */
public class MultivectorNodeCollector extends EmptyControlFlowVisitor {
    
    private HashMap<String, LinkedList<AssignmentNode>> multivectors = new HashMap<String, LinkedList<AssignmentNode>>();

    public static HashMap<String, LinkedList<AssignmentNode>> collect(ControlFlowGraph graph) {
        MultivectorNodeCollector collector = new MultivectorNodeCollector();
        graph.accept(collector);
        return collector.multivectors;
    }
    
    @Override
    public void visit(AssignmentNode node) {
        MultivectorComponent m = (MultivectorComponent) node.getVariable();
        String name = m.getName();
        if (!multivectors.containsKey(name)) 
            multivectors.put(name, new LinkedList<AssignmentNode>());
        
        multivectors.get(name).add(node);
        super.visit(node);
    }
    
    
    
}
