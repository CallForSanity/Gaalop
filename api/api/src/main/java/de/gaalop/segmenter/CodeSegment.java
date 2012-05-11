package de.gaalop.segmenter;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParserException;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;

public interface CodeSegment {
	
	void clear();
	
	void prozess() throws CodeParserException, CodeGeneratorException, OptimizationException;
	
	String getString();

	void addString(String text);
}
