package de.gaalop.api.cfg;

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
import de.gaalop.cfg.Node;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;

/**
 * Returns the type of a ControlFlowGraph node
 * @author Christian Steinmetz
 */
public class CFGNodeTypeGetter implements ControlFlowVisitor {

    private CFGNodeType type;
    private static final CFGNodeTypeGetter getter = new CFGNodeTypeGetter();

    /**
     * Returns the type of a ControlFlowGraphNode
     * @param node The node
     * @return The type of the given node
     */
    public static CFGNodeType getTypeOfCFGNode(Node node) {
        if (node == null) {
            return null;
        }

        node.accept(getter);
        return getter.type;
    }

    @Override
    public void visit(StartNode node) {
        type = CFGNodeType.StartNode;
    }

    @Override
    public void visit(AssignmentNode node) {
        type = CFGNodeType.AssignmentNode;
    }

    @Override
    public void visit(StoreResultNode node) {
        type = CFGNodeType.StoreResultNode;
    }

    @Override
    public void visit(IfThenElseNode node) {
        type = CFGNodeType.IfThenElseNode;
    }

    @Override
    public void visit(BlockEndNode node) {
        type = CFGNodeType.BlockEndNode;
    }

    @Override
    public void visit(LoopNode node) {
        type = CFGNodeType.LoopNode;
    }

    @Override
    public void visit(BreakNode node) {
        type = CFGNodeType.BreakNode;
    }

    @Override
    public void visit(Macro node) {
        type = CFGNodeType.Macro;
    }

    @Override
    public void visit(ExpressionStatement node) {
        type = CFGNodeType.ExpressionStatement;
    }

    @Override
    public void visit(EndNode node) {
        type = CFGNodeType.EndNode;
    }

    @Override
    public void visit(ColorNode node) {
        type = CFGNodeType.ColorNode;
    }
}
