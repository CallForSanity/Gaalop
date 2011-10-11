package de.gaalop.gapp.visitor;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;

/**
 * Defines an abstract class, which traverses the ControlFlowGraph 
 * and calls all GAPP members, which are not null.
 * Subclasses must implement only the GAPPVisitor methods,
 * which will be called while traversing
 *
 * @author Christian Steinmetz
 */
public abstract class CFGGAPPVisitor extends EmptyControlFlowVisitor implements GAPPVisitor {

    @Override
    public void visit(AssignmentNode node) {
        if (node.getGAPP() != null) {
            node.getGAPP().accept(this, null);
        }
        super.visit(node);
    }
}
