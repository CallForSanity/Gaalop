package de.gaalop.algebra;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.MacroCall;
import de.gaalop.visitors.DFGTraversalVisitor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * This class checks a ControlFlowGraph on recursions. 
 * A recursion can be achieved by nested macro calls and
 * results generally in endless loops in Algebra Stage.
 * 
 * @author CSteinmetz15
 */
public class RecursionChecker {
    
    private final ControlFlowGraph graph;
    private final HashMap<StringIntContainer, Macro> macros;
    private final HashSet<StringIntContainer> curContainers = new HashSet<>();

    private RecursionChecker(ControlFlowGraph graph, HashMap<StringIntContainer, Macro> macros) {
        this.graph = graph;
        this.macros = macros;
    }

    /**
     * Checks a given macros set in a ControlFlowGraph on recursions. 
     * A recursion can be achieved by nested macro calls and
     * results generally in endless loops in Algebra Stage.
     * @param graph The graph to be checked
     * @param macros The set of macros
     * @throws OptimizationException Thrown whether a recursion is found.
     */
    public static void check(ControlFlowGraph graph, HashMap<StringIntContainer, Macro> macros) throws OptimizationException {
        RecursionChecker checker = new RecursionChecker(graph, macros);
        for (StringIntContainer container: macros.keySet()) {
            checker.curContainers.clear();
            checker.checkContainer(container);
        }
    }
    
    /**
     * Check a certain macro container on recursions.
     * @param container The macro container
     * @throws OptimizationException Thrown whether a recursion is found.
     */
    private void checkContainer(StringIntContainer container) throws OptimizationException {
        // Did we already visited this macro container? 
        if (curContainers.contains(container)) 
            // If yes, we detected a recursion and throw and exception
            throw new OptimizationException("Macro "+container.getName()+"(with "+container.getNumber()+" parameters) contains a recursion. Please remove the recursion!", graph);
        
        // Otherwise this is not yet a recursion, but a "new" macro container.
        curContainers.add(container);
        
        // Find macro calls and do a check on the macros calls using this recursive function (Yeah, right! We use a recursive method in order to detect recursions :-) ).
        Macro macro = macros.get(container);
        
        // E.g. math functions will produce empty macro bodies, as they are buitin functions. Prevent the investigation for them.
        if (macro != null) {
            MacroCallCollector collector = new MacroCallCollector();
            for (SequentialNode node: macro.getBody()) 
                node.accept(collector);

            for (StringIntContainer child: collector.macroCalls) 
                checkContainer(child);
        }
        
        // No recursions found, remove this container to allow later (allowed) macro calls of this container.
        curContainers.remove(container);
    }
    
    /**
     * Collects all macro calls in a list.
     * Notice that this visitor does not propagate to successors of nodes! 
     * This is intended for macro bodies, as they are represented by a List of SequentialNodes.
     */
    private class MacroCallCollector extends DFGTraversalVisitor implements ControlFlowVisitor {
    
        public LinkedList<StringIntContainer> macroCalls = new LinkedList<StringIntContainer>();

        @Override
        public void visit(MacroCall macroExpr) {
            macroCalls.add(new StringIntContainer(macroExpr.getName(), macroExpr.getArguments().size()));
        }

        @Override
        public void visit(AssignmentNode node) {
            node.getValue().accept(this);
        }

        @Override
        public void visit(ExpressionStatement node) {
            node.getExpression().accept(this);
        }

        @Override
        public void visit(ColorNode node) {
            node.getR().accept(this);
            node.getG().accept(this);
            node.getB().accept(this);
            node.getAlpha().accept(this);
        }

        @Override
        public void visit(StartNode node) {
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
        public void visit(EndNode node) {
        }
    };
}
