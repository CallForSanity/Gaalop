package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import java.util.HashMap;

/**
 * Collects all AssignmentNodes in a ControlFlowGraph
 * and stores them in a map.
 * @author Christian Steinmetz
 */
public class AssignmentNodeMapCollector extends EmptyControlFlowVisitor {

    private HashMap<Variable, Expression> assignmentNodes = new HashMap<Variable, Expression>();

    public HashMap<Variable, Expression> getAssignmentNodes() {
        return assignmentNodes;
    }

    @Override
    public void visit(AssignmentNode node) {
        assignmentNodes.put(node.getVariable(), node.getValue());
        super.visit(node);
    }
    
}
