package de.gaalop.gapp.visitor;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;

/**
 * Defines an abstract class, which traverses the ControlFlowGraph 
 * and calls all gapp members which are not null.
 * Subclasses must implement the GAPPVisitor methods,
 * which will be called by the traverse of this class.
 *
 * @author Christian Steinmetz
 */
public abstract class CFGGAPPVisitor extends EmptyControlFlowVisitor implements GAPPVisitor {

    @Override
    public void visit(AssignmentNode node) {
        if (node.getGAPP() != null)
            node.getGAPP().accept(this, null);
        super.visit(node);
    }
    
}
