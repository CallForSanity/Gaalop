package de.gaalop.visitors;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;

/**
 *
 * @author Christian Steinmetz
 */
public class CFGReplaceVisitor extends EmptyControlFlowVisitor {

    private ReplaceVisitor visitor;

    public CFGReplaceVisitor(ReplaceVisitor visitor) {
        this.visitor = visitor;
    }

    @Override
    public void visit(AssignmentNode node) {
        
        node.setVariable((Variable) visitor.replace(node.getVariable()));
        node.setValue(visitor.replace(node.getValue()));

        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        node.setExpression(visitor.replace(node.getExpression()));
        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        node.setR(visitor.replace(node.getR()));
        node.setG(visitor.replace(node.getG()));
        node.setB(visitor.replace(node.getB()));
        node.setAlpha(visitor.replace(node.getAlpha()));
        super.visit(node);
    }

}
