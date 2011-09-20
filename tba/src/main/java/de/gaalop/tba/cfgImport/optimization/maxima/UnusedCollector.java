/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Variable;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class UnusedCollector extends EmptyControlFlowVisitor {

    private LinkedList<AssignmentNode> unusedNodes = new LinkedList<AssignmentNode>();
    private LinkedList<Variable> usedVariables;

    public UnusedCollector(LinkedList<Variable> usedVariables) {
        this.usedVariables = usedVariables;
    }

    public LinkedList<AssignmentNode> getUnusedNodes() {
        return unusedNodes;
    }

    private boolean isUsed(String name) {
        for (Variable var: usedVariables)
            if (var.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public void visit(AssignmentNode node) {
        if (!isUsed(node.getVariable().getName()))
            unusedNodes.add(node);
        super.visit(node);
    }


    

}
