package de.gaalop.tba.baseChange;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Collects the AssignmentNodes to given set of relevant variables
 * @author Christian Steinmetz
 */
public class RelevantAssignmentNodeCollector extends EmptyControlFlowVisitor {
    
    public HashMap<String, LinkedList<AssignmentNode>> relevantNodes;

    public RelevantAssignmentNodeCollector(HashSet<String> relevantVars) {
        relevantNodes = new HashMap<>();
        for (String relevantVar: relevantVars)
            relevantNodes.put(relevantVar, new LinkedList<AssignmentNode>());
    }

    @Override
    public void visit(AssignmentNode node) {
        String varName = node.getVariable().getName();
        if (relevantNodes.containsKey(varName)) 
            relevantNodes.get(varName).add(node);
        super.visit(node);
    }

}
