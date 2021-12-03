package de.gaalop.tba.baseChange;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.ReplaceVisitor;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Renames variables and MultivectorComponents using a prefix, 
 * if they are contained as keys in a map of relevant nodes
 * @author Christian Steinmetz
 */
public class Renamer extends ReplaceVisitor {
    
    private HashMap<String, LinkedList<AssignmentNode>> relevantNodes;
    private final String prefix;

    public Renamer(HashMap<String, LinkedList<AssignmentNode>> relevantNodes, String prefix) {
        this.relevantNodes = relevantNodes;
        this.prefix = prefix;
    }

    @Override
    public void visit(Variable node) {
        result = (relevantNodes.containsKey(node.getName())) 
                ? new Variable(prefix+node.getName()) 
                : node;
    }

    @Override
    public void visit(MultivectorComponent node) {
        result = (relevantNodes.containsKey(node.getName())) 
                ? new MultivectorComponent(prefix+node.getName(), node.getBladeIndex()) 
                : node;
    }

}
