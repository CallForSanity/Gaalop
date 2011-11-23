package de.gaalop.visitors;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import java.util.LinkedList;

/**
 * Collect all nodes from a ControlFlowGraph
 * @author Christian Steinmetz
 */
public class NodeCollector extends EmptyControlFlowVisitor {

    private LinkedList<Node> nodes = new LinkedList<Node>();

    private NodeCollector() {
    }

    /**
     * Collect all nodes from a given ControlFlowGraph
     * @param graph The ControlFlowGraph
     * @return The collected nodes
     */
    public static LinkedList<Node> collectNodes(ControlFlowGraph graph) {
        NodeCollector collector = new NodeCollector();
        graph.accept(collector);
        return collector.nodes;
    }

    @Override
    public void visit(StartNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(AssignmentNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(IfThenElseNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(BlockEndNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(LoopNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(BreakNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(Macro node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(EndNode node) {
        nodes.add(node);
        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        nodes.add(node);
        super.visit(node);
    }



}
