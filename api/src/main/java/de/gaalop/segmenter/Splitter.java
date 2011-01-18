package de.gaalop.segmenter;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeParser;
import de.gaalop.InputFile;
import de.gaalop.OptimizationStrategy;
import de.gaalop.segmenter.antlr.CDLexer;

/**
 * Splits an input file into String segments according to the set pragma.
 * @author thomaskanold
 *
 */
public class Splitter {

	private InputFile input;
	private CodeParser codeParser;
    private OptimizationStrategy optimizer;
	private CodeGenerator generator;

	public Splitter(InputFile input, CodeParser codeParser, OptimizationStrategy optimizer,
	CodeGenerator generator) {
		this.input = input;
		this.codeParser = codeParser;
		this.optimizer = optimizer;
		this.generator = generator;
	}

	public List<CodeSegment> getCodeSegments() {
		return process(input);
	}

	private List<CodeSegment> process(InputFile input) {

		
        ANTLRStringStream inputStream = new ANTLRStringStream(input.getContent());		

		CDLexer lexer = new CDLexer(inputStream, codeParser, optimizer, generator);		
		for(int i = 1; i < input.getContent().length(); i++) {
			try {
            org.antlr.runtime.Token token = lexer.nextToken();
			} catch (Exception e) {break;}
		}
			
		List <CodeSegment> segments = lexer.machine.getCodeSegments();		
		System.out.println(segments.size());
		System.out.println(((CodeSegment)segments.get(0)).getString());			
		
		return segments;
	}
}
