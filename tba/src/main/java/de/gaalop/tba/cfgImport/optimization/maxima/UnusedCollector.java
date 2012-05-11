package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Variable;
import java.util.LinkedList;

/**
 * Collects all unused variables in a control flow graph
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

    /**
     * Determines, if a variable name is used
     * @param name The name of the variable
     * @return <value>true</value> if it is used, <value>false</value> otherwise
     */
    private boolean isUsed(String name) {
        for (Variable var : usedVariables) {
            if (var.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(AssignmentNode node) {
        if (!isUsed(node.getVariable().getName())) {
            unusedNodes.add(node);
        }
        super.visit(node);
    }
}
