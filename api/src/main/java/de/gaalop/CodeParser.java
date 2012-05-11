package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * Classes that implement this interface parse InputFiles and produce Control Dataflow Graphs.
 */
public interface CodeParser {
    /**
     * Parses an input file and produces a control dataflow graph that represents the
     * algorithm implemented by the code in <code>input</code>.
     * @param input The file that contains the input code for this code parser.
     * @return A control dataflow graph.
     * @throws CodeParserException If any error occurs during the parsing of <code>input</code>.
     */
    ControlFlowGraph parseFile(InputFile input) throws CodeParserException;
}
