/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.api.cfg;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import java.util.LinkedList;

/**
 * Collects all AssignmentNodes in a ControlFlowGraph
 * and stores them in a list.
 * @author christian
 */
public class AssignmentNodeCollector extends EmptyControlFlowVisitor {

    private LinkedList<AssignmentNode> assignmentNodes = new LinkedList<AssignmentNode>();

    public LinkedList<AssignmentNode> getAssignmentNodes() {
        return assignmentNodes;
    }

    

    @Override
    public void visit(AssignmentNode node) {
        assignmentNodes.add(node);
        super.visit(node);
    }



}
