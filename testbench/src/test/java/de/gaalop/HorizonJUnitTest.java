package de.gaalop;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.gaalop.HorizonJUnitTest.*;
import de.gaalop.testbench.CoeffReader;
import de.gaalop.testbench.TestbenchGenerator;
import de.gaalop.testbench.TestbenchLexer;
import de.gaalop.testbench.TestbenchParser;

import static org.junit.Assert.*;

/**
 * Executes a suite of test comparing the original CLUCalc output with optimized CLUCalc and C++ outputs. <br />
 * <br />
 * <b>Note:</b> No not forget to clean and rebuild source files when editing CLUCalc files externally. Otherwise, cached
 * binary files could contain obsolete code.
 * 
 * @author Christian Schwinn
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(value = { 
		FileTests.class, 
})
public class HorizonJUnitTest {

	public static class OutputSet {
		
		private double[] cppValues;
		private double[] cluOriginalValues;
		private double[] cluOptimizedValues;
		
		public void setCPP(String result) {
			cppValues = parseString(result);
		}
		
		public void setCLUOriginal(String result) {
			cluOriginalValues = parseString(result);
		}
		
		public void setCLUOptimized(String result) {
			cluOptimizedValues = parseString(result);
		}
		
		public double[] getCppValues() {
			return cppValues;
		}
		
		public double[] getCluOriginalValues() {
			return cluOriginalValues;
		}
		
		public double[] getCluOptimizedValues() {
			return cluOptimizedValues;
		}
		
		private double[] parseString(String string) {
			ANTLRStringStream input = new ANTLRStringStream(string);
			TestbenchLexer lexer = new TestbenchLexer(input);
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			TestbenchParser parser = new TestbenchParser(tokenStream);
			try {
				CoeffReader reader = parser.line();
				return reader.getCoeffs();
			} catch (RecognitionException e) {
				e.printStackTrace();
			}
			throw new IllegalStateException("Line " + string + " could not be parsed.");
		}
	}
	
	public static class FileTests {
		
		private Random r = new Random(System.currentTimeMillis());
		
		private float nextFloat() {
			return r.nextFloat();
		}
		
		@Before
		public void init() {
			r.setSeed(System.currentTimeMillis());
			HorizonJUnitTest.init();
		}

		/**
		 * Tests the Horizon.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void horizon() throws Exception {
			String fileName = getClass().getResource("Horizon.clu").getFile();
			inputValues.put("mx", nextFloat());
			inputValues.put("my", nextFloat());
			inputValues.put("mz", nextFloat());
			inputValues.put("px", nextFloat());
			inputValues.put("py", nextFloat());
			inputValues.put("pz", nextFloat());
			inputValues.put("r", nextFloat());

			int outputMVs = 1;
			compare(fileName, outputMVs);
		}

	}

	final static String PATH = "C:/Users/Christian/Downloads/Testbench/";
	final static String INCLUDE = "C:/Program Files (x86)/CLUViz/v6_1/SDK/include";
	final static String LIBPATH = "C:/Program Files (x86)/CLUViz/v6_1/SDK/lib";

	static TestbenchGenerator generator;
	static 	Map<String, Float> inputValues = new HashMap<String, Float>();
	
	static void init() {
		inputValues.clear();
	}

	private static File compile() throws Exception {
		generator.run();
		generator.createCompileScript(INCLUDE, LIBPATH);
		return generator.compile();
	}

	private static Scanner run(File executable) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(executable.getAbsolutePath());
		pb.directory(executable.getParentFile());
		Process p = pb.start();
		return new Scanner(p.getInputStream());
	}

	private static List<OutputSet> parseResult(Scanner scanner, int numElements) {
		List<OutputSet> results = new ArrayList<OutputSet>();
		for (int i = 0; i < numElements; i++) {
			results.add(new OutputSet());
		}
		// read CPP header
		printNextLine(scanner);
		// read CPP outputs
		for (int i = 0; i < numElements; i++) {
			String vector = printNextLine(scanner);
			results.get(i).setCPP(vector);
		}
		// read CLU original header
		printNextLine(scanner);
		// read CLU original outputs
		for (int i = 0; i < numElements; i++) {
			String vector = printNextLine(scanner);
			results.get(i).setCLUOriginal(vector);
		}
		// read CLU opt header
		printNextLine(scanner);
		// read CLU opt outputs
		for (int i = 0; i < numElements; i++) {
			String vector = printNextLine(scanner);
			results.get(i).setCLUOptimized(vector);
		}

		return results;
	}

	private static String printNextLine(Scanner scanner) {
		String line = scanner.nextLine();
		System.out.println(line);
		return line;
	}

	static void compare(String fileName, int numVectors) throws Exception {
		generator = new TestbenchGenerator(fileName, PATH, inputValues);
		doCompare(numVectors);		
	}
	
	static void compare(String fileName, String contents, int numVectors) throws Exception {
		generator = new TestbenchGenerator(fileName, contents, PATH, inputValues);
		doCompare(numVectors);		
	}
	
	private static void doCompare(int numVectors) throws Exception {
		File exe = compile();
		Scanner scanner = run(exe);
		List<OutputSet> results = parseResult(scanner, numVectors);
		compareMultivectors(results);
	}

	private static void compareMultivectors(List<OutputSet> actualList) {
		for (int i = 0; i < actualList.size(); i++) {
			OutputSet actual = actualList.get(i);
			for (int element = 0; element < 32; element++) {
				double cluOriginal = actual.getCluOriginalValues()[element];
				double cluOptimized = actual.getCluOptimizedValues()[element];
				double cpp = actual.getCppValues()[element];

				double epsilon = getEpsilon(cluOriginal);
				System.out.println("Using epsilon " + epsilon + " for " + cluOriginal);
				assertEquals(cluOriginal, cluOptimized, epsilon);
				String comparison = "Index " + element + ": " + cluOriginal + " == " + cluOptimized;
				assertEquals(cluOriginal, cpp, epsilon);
				comparison += " == " + cpp;
				if (cluOriginal != 0.0d) {
					System.out.println(comparison);
				}
			}
		}
	}
	
	private static double getEpsilon(double d) {
        Locale.setDefault(Locale.US);
        String string = Double.toString(d);
		int decimals = string.substring(string.lastIndexOf(DecimalFormatSymbols.getInstance().getDecimalSeparator())+1).length();
		return Math.pow(10, -decimals);
	}

}
