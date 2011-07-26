package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;

/**
 * Facade class for the constant propagation optimization
 * @author christian
 */
public class OptConstantPropagation implements OptimizationStrategyWithModifyFlag {

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra) {
        ConstantPropagation propagation = new ConstantPropagation();
        graph.accept(propagation);
        return propagation.isGraphModified();
    }

}
