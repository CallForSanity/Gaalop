package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.OptimizationException;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.LoggingListenerGroup;

/**
 * This interface describes a strategy for source-to-source compilation which
 * consists of a simple graph to graph transformation on the control flow graph
 * that has been produced by the frontend.
 * 
 * Additionally a flag is given back, which signals a modification in the given graph.
 */
public interface OptimizationStrategyWithModifyFlag {

    /**
     * Transforms a graph by applying optimization or analysis operations.Returns a flag, which signals a modification in the given graph.
     *
     * @param graph The control flow graph to operate on.
     * @param usedAlgebra The underlying algebra
     * @param listeners
     * @return <value>true</value> if the graph has been modified, <value>false</value> otherwise
     */
    boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra, LoggingListenerGroup listeners) throws OptimizationException;
}
