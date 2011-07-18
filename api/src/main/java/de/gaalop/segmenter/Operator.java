package de.gaalop.segmenter;

import java.util.List;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParserException;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;

/**
 * Does optimization and generates the code if neccessary.
 * @author thomaskanold
 *
 */

public class Operator {

	private List<CodeSegment> splitted;

	public Operator(List<CodeSegment> splitted) {
		this.splitted = splitted;
	}

	public List<CodeSegment> getCodeSegments() throws CodeParserException, CodeGeneratorException, OptimizationException {

		return process(splitted);
	}

	private List<CodeSegment> process(List<CodeSegment> splitted) throws CodeParserException, CodeGeneratorException, OptimizationException {
		for (CodeSegment cs : splitted) {
			cs.prozess();
		}
		return splitted;
	}

}
