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
public class CodeRaw implements CodeSegment{
	
	private String text;
	
	public CodeRaw() {
		text = new String();
	}
	
	@Override
	public void addString(String text) {
		this.text += text;
	}

	@Override
	public void prozess() throws CodeParserException,
			CodeGeneratorException, OptimizationException {	
		// yes we wanna do nothing here
	}

	@Override
	public String getString() {	
		return text;
	}

	@Override
	public void clear() {
		text = new String();
	}

}
