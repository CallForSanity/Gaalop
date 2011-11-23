package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * This exception class encapsulates errors in an algebra strategy.
 */
public class AlgebraException extends CompilationException {
  
  private final ControlFlowGraph graph;

  public AlgebraException(String message, ControlFlowGraph graph) {
    super(message);
    this.graph = graph;
  }

  public AlgebraException(String message, Throwable cause, ControlFlowGraph graph) {
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
