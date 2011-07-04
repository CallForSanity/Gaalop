package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.tba.cfgImport.ContainsControlFlow;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;

/**
 *
 * @author christian
 */
public class OptConstantPropagation implements OptimizationStrategyWithModifyFlag {

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra) {
        // TODO chs: After implementing Control Flow in Optimization ConstantPropagation, remove this statement.
        if (ContainsControlFlow.containsControlFlow(graph))  {
            System.err.println("Due to Control Flow Existence in Source, Optimization ConstantPropagation isn't assigned on graph!");
            return false;
        }

        ConstantPropagation propagation = new ConstantPropagation();
        graph.accept(propagation);
        return propagation.isGraphModified();
    }

}
