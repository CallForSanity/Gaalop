package de.gaalop.tba.cfgImport.optimization.gcse;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;

/**
 * Implements a control flow visitor that finds an expression in a SequentialNode and replaces it
 * @author CSteinmetz15
 */
public class OccurenceReplacer implements ControlFlowVisitor {
    
    private Expression search;
    private Expression replacement;

    /**
     * Facade method for replacing occurences of an expression in a node
     * @param search The expression to search for
     * @param replacement The replacement for the found expression
     * @param node The node, where the expression is located in
     */
    public static void replaceOccurences(Expression search, Expression replacement, SequentialNode node) {
        OccurenceReplacer replacer = new OccurenceReplacer();
        replacer.search = search;
        replacer.replacement = replacement;
        node.accept(replacer);
    }

    @Override
    public void visit(StartNode node) {
    }

    @Override
    public void visit(AssignmentNode node) {
        if (node.getValue() == search) {
            node.setValue(replacement.copy());
        } else 
            node.getValue().replaceExpression(search, replacement.copy());
    }

    @Override
    public void visit(StoreResultNode node) {
    }

    @Override
    public void visit(IfThenElseNode node) {
        
    }

    @Override
    public void visit(BlockEndNode node) {
        
    }

    @Override
    public void visit(LoopNode node) {
        
    }

    @Override
    public void visit(BreakNode node) {
        
    }

    @Override
    public void visit(Macro node) {
        
    }

    @Override
    public void visit(ExpressionStatement node) {
        if (node.getExpression() == search) {
            node.setExpression(replacement.copy());
        } else 
            node.getExpression().replaceExpression(search, replacement.copy());
    }

    @Override
    public void visit(EndNode node) {
        
    }

    @Override
    public void visit(ColorNode node) {
        if (node.getR() == search) {
            node.setR(replacement.copy());
        } else 
            node.getR().replaceExpression(search, replacement.copy());
        
        if (node.getG() == search) {
            node.setG(replacement.copy());
        } else 
            node.getG().replaceExpression(search, replacement.copy());
        
        if (node.getB() == search) {
            node.setB(replacement.copy());
        } else 
            node.getB().replaceExpression(search, replacement.copy());
        
        if (node.getAlpha() == search) {
            node.setAlpha(replacement.copy());
        } else 
            node.getAlpha().replaceExpression(search, replacement.copy());
    }
}
