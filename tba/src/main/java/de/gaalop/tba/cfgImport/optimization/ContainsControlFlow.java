/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;

/**
 *
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

    public static boolean containsControlFlow(ControlFlowGraph graph) {
        ContainsControlFlow c = new ContainsControlFlow();
        graph.accept(c);
        return c.isContainsControlFlow();
    }

}
