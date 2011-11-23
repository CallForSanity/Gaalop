package de.gaalop.algebra;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.SequentialNode;
import java.util.LinkedList;

/**
 * Removes the DefVars statement from a Control Flow Graph
 * @author Christian Steinmetz
 */
public class RemoveDefVars extends EmptyControlFlowVisitor {

    private LinkedList<SequentialNode> toRemove = new LinkedList<SequentialNode>();

    private RemoveDefVars() {
    }

    /**
     * Removes the DefVars statement from a given Control Flow Graph
     * @param graph The Control Flow Graph
     */
    public static void removeDefVars(ControlFlowGraph graph) {
        RemoveDefVars visitor = new RemoveDefVars();
        graph.accept(visitor);
        for (SequentialNode node: visitor.toRemove)
            graph.removeNode(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        toRemove.add(node);
        super.visit(node);
    }

}
