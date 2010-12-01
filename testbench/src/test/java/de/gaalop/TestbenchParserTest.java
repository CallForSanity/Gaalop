package de.gaalop;

import java.util.Arrays;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import de.gaalop.testbench.CoeffReader;
import de.gaalop.testbench.TestbenchLexer;
import de.gaalop.testbench.TestbenchParser;

public class TestbenchParserTest {
	
	public static void main(String[] args) {		
		String string = "0.9e-009^e1 + 0.98e+010^e2 - 0.189039^e3 + 0.98^e + 1^e0";
		ANTLRStringStream input = new ANTLRStringStream(string);
		TestbenchLexer lexer = new TestbenchLexer(input);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		TestbenchParser parser = new TestbenchParser(tokenStream);
		try {
			CoeffReader reader = parser.line();
			System.out.println(Arrays.toString(reader.getCoeffs()));
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
	}

}
