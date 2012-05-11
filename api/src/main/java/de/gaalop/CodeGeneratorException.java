package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * This exception is thrown whenever an error occurs during the code generation process.
 */
public class CodeGeneratorException extends CompilationException {

  private static final long serialVersionUID = -3328688175390798389L;

  private final ControlFlowGraph graph;

  public CodeGeneratorException(ControlFlowGraph graph, String message) {
    super(message);
    this.graph = graph;
  }

  public CodeGeneratorException(ControlFlowGraph graph, String message, Throwable cause) {
    super(message, cause);
    this.graph = graph;
  }

  public ControlFlowGraph getGraph() {
    return graph;
  }
}
