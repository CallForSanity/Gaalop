package de.gaalop.algebra;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.Macro;
import java.util.HashMap;

/**
 * Collects all Macros in a ControlFlowGraph
 * @author Christian Steinmetz
 */
public class MacrosVisitor extends EmptyControlFlowVisitor {

    private HashMap<String, Macro> macrosMap;

    private MacrosVisitor() {   //private constructor for making the usage of the static methods mandatory
    }

    /**
     * Collects all macros in a given ControlFlowGraph and stores them in a new Map
     * @param graph The ControlFlowGraph
     * @return The new Map
     */
    public static HashMap<String, Macro> getAllMacros(ControlFlowGraph graph) {
        MacrosVisitor visitor = new MacrosVisitor();
        visitor.macrosMap = new HashMap<String, Macro>();
        graph.accept(visitor);
        return visitor.macrosMap;
    }

    /**
     * Collects all macros in a given ControlFlowGraph and adds them to a Map
     * @param graph The ControlFlowGraph
     * @param mapToUse The map to use for adding
     */
    public static void getAllMacros(ControlFlowGraph graph, HashMap<String, Macro> mapToUse) {
        MacrosVisitor visitor = new MacrosVisitor();
        visitor.macrosMap = mapToUse;
        graph.accept(visitor);
    }

    @Override
    public void visit(Macro node) {
        macrosMap.put(node.getName(), node);
        super.visit(node);
    }

}
