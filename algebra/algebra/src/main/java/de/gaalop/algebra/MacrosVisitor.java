package de.gaalop.algebra;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.SequentialNode;
import java.util.HashMap;
import java.util.List;

/**
 * Collects all Macros in a ControlFlowGraph
 * @author Christian Steinmetz
 */
public class MacrosVisitor extends EmptyControlFlowVisitor {

    private HashMap<StringIntContainer, Macro> macrosMap;

    private MacrosVisitor() {   //private constructor for making the usage of the static methods mandatory
    }

    /**
     * Collects all macros in a given ControlFlowGraph and stores them in a new Map
     * @param graph The ControlFlowGraph
     * @return The new Map
     */
    public static HashMap<StringIntContainer, Macro> getAllMacros(ControlFlowGraph graph) {
        MacrosVisitor visitor = new MacrosVisitor();
        visitor.macrosMap = new HashMap<StringIntContainer, Macro>();
        graph.accept(visitor);
        return visitor.macrosMap;
    }

    /**
     * Collects all macros in a given ControlFlowGraph and adds them to a Map
     * @param graph The ControlFlowGraph
     * @param mapToUse The map to use for adding
     */
    public static void getAllMacros(ControlFlowGraph graph, HashMap<StringIntContainer, Macro> mapToUse) {
        MacrosVisitor visitor = new MacrosVisitor();
        visitor.macrosMap = mapToUse;
        graph.accept(visitor);
    }

    private int getMaxCountOfUsedArguments(List<SequentialNode> body) {
        int max = 0;
        for (SequentialNode node: body) {
            if (node instanceof AssignmentNode) {
                AssignmentNode aNode = (AssignmentNode) node;
                max = Math.max(max, MaxFunctionArgumentNumberGetter.getMaxFunctionArgumentNumber(aNode.getVariable()));
                max = Math.max(max, MaxFunctionArgumentNumberGetter.getMaxFunctionArgumentNumber(aNode.getValue()));
            }
        }
        return max;
    }

    @Override
    public void visit(Macro node) {
        int argsCount = Math.max(getMaxCountOfUsedArguments(node.getBody()),MaxFunctionArgumentNumberGetter.getMaxFunctionArgumentNumber(node.getReturnValue()));
        macrosMap.put(new StringIntContainer(node.getName(), argsCount), node);
        super.visit(node);
    }

}
