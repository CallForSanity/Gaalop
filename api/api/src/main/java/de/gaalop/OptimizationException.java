package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * This exception class encapsulates errors in an optimization strategy.
 */
public class OptimizationException extends CompilationException {

  private static final long serialVersionUID = -6886051625518541467L;
  
  private final ControlFlowGraph graph;

  public OptimizationException(String message, ControlFlowGraph graph) {
    super(message);
    this.graph = graph;
  }

  public OptimizationException(String message, Throwable cause, ControlFlowGraph graph) {
    super(message, cause);
    this.graph = graph;
  }

  /**
   * Gets the graph that was in use when the error ocurred.
   * 
   * @return The control flow graph responsible for the error.
   */
  public ControlFlowGraph getGraph() {
    return graph;
  }
}
