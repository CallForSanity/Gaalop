package de.gaalop.segmenter;


import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParserException;
import de.gaalop.OptimizationException;

/**
 * Leave the code as is. 
 * 
 * @author thomaskanold
 *
 */
public class CodeDelete implements CodeSegment{

	
	public CodeDelete() {

	}
	
	@Override
	public void addString(String text) {

	}

	@Override
	public void prozess() throws CodeParserException,
			CodeGeneratorException, OptimizationException {	

	}

	@Override
	public String getString() {	
		return "";
	}

	@Override
	public void clear() {
	}

}
