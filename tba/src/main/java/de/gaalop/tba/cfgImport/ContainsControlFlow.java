package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;

/**
 * Visitor for checking of Control Flow in a ControlFlowGraph
 * @author christian
 */
public class ContainsControlFlow extends EmptyControlFlowVisitor {

    private boolean containsControlFlow = false;

    public boolean isContainsControlFlow() {
        return containsControlFlow;
    }

    @Override
    public void visit(IfThenElseNode node) {
        containsControlFlow = true;
    }

    @Override
    public void visit(LoopNode node) {
        containsControlFlow = true;
    }

    @Override
    public void visit(BreakNode node) {
        containsControlFlow = true;
    }

    @Override
    public void visit(BlockEndNode node) {
        containsControlFlow = true;
    }

    /**
     * Determines, if a given ControlFlowGraph contains Control Flow
     * @param graph The ControlFlowGraph, which should be checked
     * @return <value>true</value> if the graph contains Control Flow, <value>false</value> otherwise
     */
    public static boolean containsControlFlow(ControlFlowGraph graph) {
        ContainsControlFlow c = new ContainsControlFlow();
        graph.accept(c);
        return c.isContainsControlFlow();
    }

}
