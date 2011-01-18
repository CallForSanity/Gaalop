package de.gaalop.segmenter;

import java.util.Set;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * The CodeHide doesn't optimize the code. It doesn't translate it. It returns the raw code with the 
 * difference that it stands for a piece of code that can be used for following Optimization parts.
 * @author thomaskanold
 *
 */
public class CodeHide implements CodeSegment {

	private String inText, outText;
	private CodeParser parser;
    private OptimizationStrategy optimizationStrategy;
    private	CodeGenerator codeGenerator;
    
    
	public CodeHide(CodeParser parser, OptimizationStrategy optimizationStrategy,
			CodeGenerator codeGenerator) {
		this.optimizationStrategy = optimizationStrategy;
		this.codeGenerator = codeGenerator;
		this.parser = parser;
		inText = new String();
		outText = new String();		
	}

	@Override
	public String getString() {
		return inText;
	}

	@Override
	public void prozess() throws CodeParserException, CodeGeneratorException, OptimizationException {
		// we do nothing
	}

	@Override
	public void addString(String text) {
		this.inText += text;
	}
	
	private InputFile getInput() {
		return new InputFile("optimized", inText);
	}

	@Override
	public void clear() {
		inText = outText = new String();
	}

}
