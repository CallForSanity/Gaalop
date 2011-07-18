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
 * The CodeTranslator doesn't optimize the code. It just translates it into another language. 
 * @author thomaskanold
 *
 */
public class CodeOptimize implements CodeSegment {

	private String text;
	private CodeParser parser;
    private OptimizationStrategy optimizationStrategy;
    private	CodeGenerator codeGenerator;
    
    
	public CodeOptimize(CodeParser parser, OptimizationStrategy optimizationStrategy,
			CodeGenerator codeGenerator) {
		this.optimizationStrategy = optimizationStrategy;
		this.codeGenerator = codeGenerator;
		this.parser = parser;
		text = new String();
	}

	@Override
	public String getString() {
		return text;
	}

	@Override
	public void prozess() throws CodeParserException, CodeGeneratorException, OptimizationException {
		
		ControlFlowGraph graph = parser.parseFile(getInput());
		optimizationStrategy.transform(graph);
		Set<OutputFile> outputs = codeGenerator.generate(graph);
		text = new String();
		for (OutputFile opf: outputs) {
			text += opf.getContent();
		}
	}

	@Override
	public void addString(String text) {
		this.text += text;
	}
	
	private InputFile getInput() {
		return new InputFile("optimized", text);
	}

	@Override
	public void clear() {
		text = new String();	
	}

}
