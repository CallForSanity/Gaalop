package de.gaalop.gapp.visitor;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;

/**
 * Defines an abstract class, which traverses the ControlFlowGraph.
 * Subclasses must implement the GAPPVisitor methods,
 * which will be called by the traverse of this class.
 *
 * @author christian
 */
public abstract class CFGGAPPVisitor extends EmptyControlFlowVisitor implements GAPPVisitor {

    @Override
    public void visit(AssignmentNode node) {
        if (node.getGAPP() != null)
            node.getGAPP().accept(this, null);
        super.visit(node);
    }
    

}
