package de.gaalop.algebra;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.MacroCall;
import de.gaalop.visitors.DFGTraversalVisitor;

/**
 * Counts the macrocalls in a control flow graph
 * @author Christian Steinmetz
 */
public class MacroCallCounter extends EmptyControlFlowVisitor {

    private int count = 0;

    private MacroCallCounter() { //private constructor for making the usage of the static methods mandatory
    }

    /**
     * Counts the macrocalls in a control flow graph
     * @param graph The graph
     * @return The number of macrocalls in the given graph
     */
    public static int countMacroCallsInGraph(ControlFlowGraph graph) {
        MacroCallCounter counter = new MacroCallCounter();
        graph.accept(counter);
        return counter.count;
    }

    private ExpressionVisitor dfgVisitor = new DFGTraversalVisitor() {
        @Override
        public void visit(MacroCall node) {
            count++;
        }
    };

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(dfgVisitor);
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        node.getExpression().accept(dfgVisitor);
        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        node.getR().accept(dfgVisitor);
        node.getG().accept(dfgVisitor);
        node.getB().accept(dfgVisitor);
        node.getAlpha().accept(dfgVisitor);
        super.visit(node);
    }
    
    

}
