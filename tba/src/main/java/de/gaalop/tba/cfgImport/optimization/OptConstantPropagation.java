package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;

/**
 *
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
