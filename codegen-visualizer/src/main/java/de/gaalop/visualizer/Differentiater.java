package de.gaalop.visualizer;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.MultivectorComponent;
import java.util.LinkedList;

/**
 * Defines the ability to differentiate a list of AssignmentNodes with respect to a given variable name
 * @author Christian
 */
public interface Differentiater {
    
    /**
     * Differentiates a list of AssignmentNodes with respect to a given variable
     * @param toDerive The list of AssignmentNodes
     * @param variable The variable
     * @return A list of AssignmentNodes that represents the derivated code
     */
    public LinkedList<AssignmentNode> differentiate(LinkedList<AssignmentNode> toDerive, MultivectorComponent variable);
    
}
