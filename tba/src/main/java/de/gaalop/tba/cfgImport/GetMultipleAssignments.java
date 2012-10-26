package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Variable;
import java.util.HashSet;

/**
 * Visitor for getting a variable, which has multiple assignments in a ControlFlowGraph
 * @author Christian Steinmetz
 */
public class GetMultipleAssignments extends EmptyControlFlowVisitor {

    private Variable multipleAssignmentsVariable = null;
    private HashSet<Variable> assigned = new HashSet<Variable>();

    public Variable getMultipleAssignmentsVariable() {
        return multipleAssignmentsVariable;
    }

    /**
     * Returns a variable, which has multiple assignments in a given ControlFlowGraph
     * @param graph The ControlFlowGraph
     * @return The variable, which has multiple assignments in a given ControlFlowGraph, null if there are no multiple assignments
     */
    public static Variable getMulipleAssignments(ControlFlowGraph graph) {
        GetMultipleAssignments g = new GetMultipleAssignments();
        graph.accept(g);
        return g.getMultipleAssignmentsVariable();
    }

    @Override
    public void visit(AssignmentNode node) {
        Variable curVariable = node.getVariable();

        if (assigned.contains(curVariable)) {
            multipleAssignmentsVariable = curVariable;
            return;
        } else {
            assigned.add(curVariable);
        }

        super.visit(node);
    }
}
