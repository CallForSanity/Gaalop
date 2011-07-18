package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Variable;
import java.util.HashSet;

/**
 * Visitor for checking of multiple assignments on one variable in a ControlFlowGraph
 * @author christian
 */
public class ContainsMulipleAssignments extends EmptyControlFlowVisitor {

    private boolean containsMultipleAssignments = false;
    private String varName;

    public String getVarName() {
        return varName;
    }

    private HashSet<Variable> assigned = new HashSet<Variable>();

    public boolean isContainsMultipleAssignments() {
        return containsMultipleAssignments;
    }

    /**
     * Determines, if there are multiple assignments on one variable in a given ControlFlowGraph
     * @param graph The ControlFlowGraph, which should be checked
     * @return <value>true</value> if the graph contains multiple assignments on one variable, <value>false</value> otherwise
     */
    public static boolean containsMulipleAssignments(ControlFlowGraph graph) {
        ContainsMulipleAssignments c = new ContainsMulipleAssignments();
        graph.accept(c);
        if(c.getVarName() != null)
            System.out.println(c.getVarName());
        return c.isContainsMultipleAssignments();
    }

    @Override
    public void visit(AssignmentNode node) {
        Variable curVariable = node.getVariable();

        if (assigned.contains(curVariable)) {
            containsMultipleAssignments = true;
            varName = curVariable.toString();
            return;
        } else {
            assigned.add(curVariable);
        }

        super.visit(node);
    }

    
}
