package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * This interface describes a strategy for soure-to-source compilation which
 * consists of a simple graph to graph transformation on the control flow graph
 * that has been produced by the frontend.
 */
public interface OptimizationStrategy {

    /**
     * Transforms a graph by applying optimization or analysis operations.
     *
     * @param graph The control flow graph to operate on.
     * @throws OptimizationException If any error occurs during the transformation.
     */
    void transform(ControlFlowGraph graph) throws OptimizationException;

    void addProgressListener(LoggingListener progressListener);
}
