/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
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
