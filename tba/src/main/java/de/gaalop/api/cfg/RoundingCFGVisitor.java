package de.gaalop.api.cfg;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.api.dfg.RoundingDFGVisitor;

/**
 * Implements a control flow graph visitor, which rounds float constants in the graph
 * @author Christian Steinmetz
 */
public class RoundingCFGVisitor extends EmptyControlFlowVisitor {
    
    private final int numberOfDigits;

    public RoundingCFGVisitor(int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }
    
    @Override
    public void visit(AssignmentNode node) {
        node.setValue(RoundingDFGVisitor.round(node.getValue(), numberOfDigits));
    }

    @Override
    public void visit(ExpressionStatement node) {
        node.setExpression(RoundingDFGVisitor.round(node.getExpression(), numberOfDigits));
    }

}
