package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.cfgImport.VariablesCollector;
import java.util.LinkedList;

/**
 * This class provide a method for updating the LocalVariable-set in a graph
 * @author Christian Steinmetz
 */
public class UpdateLocalVariableSet {

    /**
     * Updates the LocalVariable-set in a graph
     * @param graph The graph
     */
    public static void updateVariableSets(ControlFlowGraph graph) {

        VariablesCollector collector = new VariablesCollector();
        graph.accept(collector);

        LinkedList<Variable> vars = new LinkedList<Variable>(graph.getLocalVariables());
        for (Variable v: vars)
            if (!collector.getVariables().contains(v.getName())) 
                graph.removeLocalVariable(v);

    }

}
