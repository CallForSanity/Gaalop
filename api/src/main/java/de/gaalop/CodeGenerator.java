package de.gaalop;

import de.gaalop.cfg.ControlFlowGraph;

import java.util.Set;

/**
 * This interface provides methods to generate code from
 * a control flow graph.
 * 
 * Please note: Implementing classes MUST be thread safe.
 * 
 * @author Sebastian Hartte
 * @version 1.0
 * @since 1.0
 * @see OutputFile
 */
public interface CodeGenerator {
	/**
	 * Generates a set of files for a given control flow graph.
	 * 
	 * @param in The control flow graph to generated code from.
	 * @return A set of generated files. This set will not be modified.
     * @throws CodeGeneratorException If any error occurs during the code generation phase.
	 */
	Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException;
}
