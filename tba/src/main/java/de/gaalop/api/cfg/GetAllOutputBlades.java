package de.gaalop.api.cfg;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.FindStoreOutputNodes;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.VariableComponent;
import java.util.HashSet;

/**
 * Implements a class which extracts all output blades
 * @author christian
 */
public class GetAllOutputBlades {

    /**
     * Returns all output blades of a given graph
     * @param graph The graph
     * @param usedAlgebra The used algebra
     * @return The output blades
     */
    public static HashSet<VariableComponent> getAllOutputBlades(ControlFlowGraph graph, UseAlgebra usedAlgebra) {

        HashSet<VariableComponent> result = new HashSet<VariableComponent>();

         // mark output vars as tabu
        for (String output: graph.getPragmaOutputVariables()) {
            String[] parts = output.split("\\$");
            result.add(new VariableComponent(parts[0],Integer.parseInt(parts[1]), null));
        }

        FindStoreOutputNodes storeResultNodes = new FindStoreOutputNodes();
        graph.accept(storeResultNodes);
        for (StoreResultNode curSRNode: storeResultNodes.getNodes()) {
            String name = curSRNode.getValue().getName();
            int bladeCount = usedAlgebra.getBladeCount();
            for (int blade=0;blade<bladeCount;blade++)
                result.add(new VariableComponent(name,blade, null));
        }

        return result;
    }

}
