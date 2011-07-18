package de.gaalop.segmenter;

import java.util.Set;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * The CodeTranslator doesn't optimize the code. It just translates it into another language. 
 * @author thomaskanold
 *
 */
public class CodeTranslate implements CodeSegment {

	private String text;
	private CodeParser parser;
	private CodeGenerator codeGenerator;

	public CodeTranslate(CodeParser parser,
			CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
		this.parser = parser; 
		text = new String();
	}

	@Override
	public String getString() {
		return text;
	}

	@Override
	public void prozess() throws CodeParserException, CodeGeneratorException {
		
		ControlFlowGraph graph = parser.parseFile(getInput());
		Set<OutputFile> outputs = codeGenerator.generate(graph);
		text = "";
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
