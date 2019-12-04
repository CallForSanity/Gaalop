package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.LoggingListenerGroup;

/**
 * Facade class for the constant propagation optimization
 * @author Christian Steinmetz
 */
public class OptConstantPropagation implements OptimizationStrategyWithModifyFlag {

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra, LoggingListenerGroup listeners) {
        ConstantPropagation propagation = new ConstantPropagation();
        graph.accept(propagation);
        return propagation.isGraphModified();
    }
}
