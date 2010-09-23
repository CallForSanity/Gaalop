package de.gaalop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.gaalop.CluCalcCppTest.*;
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
		ErrorTests.class
})
public class CluCalcCppTest {

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
		
		@Before
		public void init() {
			CluCalcCppTest.init();
		}

		/**
		 * Tests the Horizon.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void horizon() throws Exception {
			String fileName = getClass().getResource("Horizon.clu").getFile();
			inputValues.put("mx", 0.0f);
			inputValues.put("my", 0.0f);
			inputValues.put("mz", 0.0f);
			inputValues.put("px", 1.0f);
			inputValues.put("py", 1.0f);
			inputValues.put("pz", 1.0f);
			inputValues.put("r", 0.5f);

			int outputMVs = 1; // C
			compare(fileName, outputMVs);
		}

		/**
		 * Tests the inverse kinematics algorithm.
		 * 
		 * @throws IOException
		 */
		@Test
		public void inverseKinematics() throws Exception {
			String fileName = getClass().getResource("IK_Gaalop-2.0_input.clu").getFile();
			inputValues.put("pwx", 1.1f);
			inputValues.put("pwy", 1.1f);
			inputValues.put("pwz", 1.1f);
			inputValues.put("d1", 1.40f);
			inputValues.put("d2", 1.30f);
			inputValues.put("phi", (float) Math.PI);

			int outputMVs = 6; // SwivelPlane, p_e, q_e, q12, q3, q_s
			epsilon = 1.0e-3; // assume precision of original clucalc output
			compare(fileName, outputMVs);
		}

		/**
		 * Tests all-control-flow.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void allControlFlow() throws Exception {
			String fileName = getClass().getResource("all-control-flow.clu").getFile();
			// no input variables for this test

			int outputMVs = 1; // x
			compare(fileName, outputMVs);
		}
		
		/**
		 * Tests the loops.clu example.
		 * 
		 * @throws Exception
		 */
		@Test
		public void loops() throws Exception {
			String fileName = getClass().getResource("loops.clu").getFile();
			// no input variables for this test
			
			int outputMVs = 1; // x
			compare(fileName, outputMVs);
		}
		
		/**
		 * Tests the loop_counter.clu example.
		 * 
		 * @throws Exception
		 */
		@Test
		public void loopCounter() throws Exception {
			String fileName = getClass().getResource("loop_counter.clu").getFile();
			// no input variables for this test
			
			int outputMVs = 1; // x
			compare(fileName, outputMVs);
		}
		
		/**
		 * Tests the loop_in_branch.clu example.
		 * 
		 * @throws Exception
		 */
		@Test
		public void loopInBranch() throws Exception {
			String fileName = getClass().getResource("loop_in_branch.clu").getFile();
			// no input variables for this test
			
			int outputMVs = 1; // x
			compare(fileName, outputMVs);
		}

		/**
		 * Tests the loop_no_unrolling.clu example.
		 * 
		 * @throws Exception
		 */
		@Test
		public void loopNoUnrolling() throws Exception {
			String fileName = getClass().getResource("loop_no_unrolling.clu").getFile();
			inputValues.put("x", 1.0f);
			inputValues.put("y", 1.0f);
			inputValues.put("z", 1.0f);
			inputValues.put("a", 2.0f);
			inputValues.put("b", 2.0f);
			inputValues.put("c", 2.0f);

			int outputMVs = 2; // val, var
			compare(fileName, outputMVs);
		}

		/**
		 * Tests the Nested_If.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void nestedIf() throws Exception {
			String fileName = getClass().getResource("Nested_If.clu").getFile();
			inputValues.put("s1", 1.0f);
			inputValues.put("s2", 1.0f);
			inputValues.put("s3", 1.0f);
			inputValues.put("r", 1.0f);
			inputValues.put("z", 5.0f);
			inputValues.put("p1", 3.0f);
			inputValues.put("p2", 3.0f);
			inputValues.put("p3", 3.0f);

			int outputMVs = 1; // rslt
			compare(fileName, outputMVs);
		}

		/**
		 * Tests the mixed_macros.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void mixedMacros() throws Exception {
			String fileName = getClass().getResource("mixed_macros.clu").getFile();
			// no input values for this test

			int outputMVs = 1; // rslt
			compare(fileName, outputMVs);
		}
		
		/**
		 * Tests the equality_condition.clu example.
		 * 
		 * @throws Exception
		 */
		@Test
		public void equalityCondition() throws Exception {
			String fileName = getClass().getResource("equality_condition.clu").getFile();
			inputValues.put("a", 1.0f);
			inputValues.put("b", 2.0f);
			inputValues.put("c", 3.0f);
			inputValues.put("j", 2.0f);
			inputValues.put("k", 3.0f);
			
			int outputMVs = 1; // x
			compare(fileName, outputMVs);			
		}
		
		/**
		 * Tests the unknown_if.clu example for +1 and -1.
		 * 
		 * @throws Exception
		 */
		@Test
		public void unknownIf() throws Exception {
			String fileName = getClass().getResource("unknown_if.clu").getFile();
			int outputMVs = 1;
			
			inputValues.put("unknown", 1.0f); // r
			compare(fileName, outputMVs);			
			
			inputValues.put("unknown", -1.0f); // r
			compare(fileName, outputMVs);			
		}
		
	}

	public static class ErrorTests {
			
		private static final String RECURSIVE_X = "The Variable x cannot be a local variable and an input variable at the same time.";
		private static final String NO_OPT = "There are no lines marked for optimization ('?')";

		@Before
		public void init() {
			CluCalcCppTest.init();
		}

		/**
		 * Tests if a code parser exception is thrown for missing question marks as expected.
		 * 
		 * @throws Exception
		 */
		@Test(expected = CodeParserException.class)
		public void missingQuestionMark() throws Exception {
			String content = "x = VecN3(1,2,3);";
			try {
				compare("no-question-mark.clu", content, 0);
			} catch (CodeParserException e) {
				assertEquals(NO_OPT, e.getMessage());
				throw e;
			}
		}

		/**
		 * Tests if a code parser exception is thrown for recursive assignments as expected.
		 * 
		 * @throws Exception
		 */
		@Test(expected = CodeParserException.class)
		public void recursiveInputVariable() throws Exception {
			String content = "?x = VecN3(1,2,x);";
			try {
				compare("recursive-assignment.clu", content, 0);
			} catch (CodeParserException e) {
				assertEquals(RECURSIVE_X, e.getMessage());
				throw e;
			}
		}
		
		/**
		 * Tests if a code parser exception is thrown for uninitialized variables in recursive assignments as expected.
		 * 
		 * @throws Exception
		 */
		@Test(expected = CodeParserException.class)
		public void uninitializedVariable() throws Exception {
			String fileName = getClass().getResource("uninitializedVariable.clu").getFile();
			// no input variables for this test
						
			try {
				compare(fileName, 0);	
			} catch (CodeParserException e) {
				assertEquals(RECURSIVE_X, e.getMessage());
				throw e;
			}
		}
		
	}

	final static String PATH = "C:/Users/Christian/Downloads/Testbench/";
	final static String INCLUDE = "C:/Program Files (x86)/CLUViz/v6_1/SDK/include";
	final static String LIBPATH = "C:/Program Files (x86)/CLUViz/v6_1/SDK/lib";

	static TestbenchGenerator generator;
	static 	Map<String, Float> inputValues = new HashMap<String, Float>();
	
	static double epsilon = 0.0d;
	
	static void init() {
		inputValues.clear();
		epsilon = 0.0d;
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
				assertEquals(cluOriginal, cluOptimized, epsilon);
				assertEquals(cluOriginal, cpp, epsilon);
			}
		}
	}

}
