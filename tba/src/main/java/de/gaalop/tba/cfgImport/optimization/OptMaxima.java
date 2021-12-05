package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.OptimizationException;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.Plugin;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaOptimizer;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;
import de.gaalop.LoggingListenerGroup;

/**
 * Facade class for the optimization using maxima
 * @author Christian Steinmetz
 */
public class OptMaxima implements OptimizationStrategyWithModifyFlag {

    private MaximaOptimizer transformer;

    public OptMaxima(String commandMaxima, Plugin plugin) {
        transformer = new MaximaOptimizer(new ProcessBuilderMaximaConnection(commandMaxima), plugin);
    }

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra, LoggingListenerGroup listeners) throws OptimizationException {
        
        transformer.transformGraph(graph, listeners);
        
        return false; // don't do a second step, if it was not necessary for other optimizations
    }
}
