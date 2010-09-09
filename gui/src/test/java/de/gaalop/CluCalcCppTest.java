package de.gaalop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.gaalop.CluCalcCppTest.*;

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
		ErrorTests.class })
public class CluCalcCppTest {

	public static class OutputSet {
		public String CPP;
		public String CLU_ORIGINAL;
		public String CLU_OPT;
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
			String fileName = getClass().getResource("/de/gaalop/Horizon.clu").getFile();
			inputValues.put("mx", 0.0f);
			inputValues.put("my", 0.0f);
			inputValues.put("mz", 0.0f);
			inputValues.put("px", 1.0f);
			inputValues.put("py", 1.0f);
			inputValues.put("pz", 1.0f);
			inputValues.put("r", 0.5f);

			int outputMVs = 1;
			// C
			expectedCppResults
					.add("0.125^(e1^e) - 1^(e1^e0) + 0.125^(e2^e) - 1^(e2^e0) + 0.125^(e3^e) - 1^(e3^e0) - 0.25^E");

			compare(fileName, outputMVs);
		}

		/**
		 * Tests the inverse kinematics algorithm.
		 * 
		 * @throws IOException
		 */
		@Test
		public void inverseKinematics() throws Exception {
			String fileName = getClass().getResource("/de/gaalop/IK_Gaalop-2.0_input.clu").getFile();
			inputValues.put("pwx", 1.1f);
			inputValues.put("pwy", 1.1f);
			inputValues.put("pwz", 1.1f);
			inputValues.put("d1", 1.40f);
			inputValues.put("d2", 1.30f);
			inputValues.put("phi", (float) Math.PI);

			int outputMVs = 6;
			// SwivelPlane
			expectedCppResults.add("-1.1^e1 + 1.1^e2 + 1.11042e-007^e3");
			// p_e
			expectedCppResults.add("0.980883^e1 + 0.980883^e2 - 0.189039^e3 + 0.98^e + 1^e0");
			// q_e
			expectedCppResults.add("0.705162 + 0.709047^e23");
			// q12
			expectedCppResults.add("-0.657637^e12 + 0.532688^e13 - 0.532688^e23");
			// q3
			expectedCppResults.add("0.382683 - 0.92388^e12");
			// q_s
			expectedCppResults.add("-0.607577 - 0.251667^e12 - 0.288289^e13 - 0.695991^e23");

			compare(fileName, outputMVs);
		}

		/**
		 * Tests all-control-flow.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void allControlFlow() throws Exception {
			String fileName = getClass().getResource("/de/gaalop/all-control-flow.clu").getFile();
			// no input variables for this test

			int outputMVs = 1;
			// x
			expectedCppResults.add("3e+010");

			compare(fileName, outputMVs);
		}

		@Test
		public void loopNoUnrolling() throws Exception {
			String fileName = getClass().getResource("/de/gaalop/loop_no_unrolling.clu").getFile();
			inputValues.put("x", 1.0f);
			inputValues.put("y", 1.0f);
			inputValues.put("z", 1.0f);
			inputValues.put("a", 2.0f);
			inputValues.put("b", 2.0f);
			inputValues.put("c", 2.0f);

			int outputMVs = 2;
			// val
			expectedCppResults.add("10 + 1^e1 + 1^e2 + 1^e3 + 1.5^e + 1^e0");
			// var
			expectedCppResults.add("2^e1 + 2^e2 + 2^e3 + 6^e + 1^e0");

			compare(fileName, outputMVs);
		}

		/**
		 * Tests the Nested_If.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void nestedIf() throws Exception {
			String fileName = getClass().getResource("/de/gaalop/Nested_If.clu").getFile();
			inputValues.put("s1", 1.0f);
			inputValues.put("s2", 1.0f);
			inputValues.put("s3", 1.0f);
			inputValues.put("r", 1.0f);
			inputValues.put("z", 5.0f);
			inputValues.put("p1", 3.0f);
			inputValues.put("p2", 3.0f);
			inputValues.put("p3", 3.0f);

			int outputMVs = 1;
			// rslt
			expectedCppResults.add("20^e1 + 40^e2 + 60^e3 + 140^e + 20^e0");

			compare(fileName, outputMVs);
		}

		/**
		 * Tests the mixed_macros.clu example.
		 * 
		 * @throws IOException
		 */
		@Test
		public void mixedMacros() throws Exception {
			String fileName = getClass().getResource("/de/gaalop/mixed_macros.clu").getFile();
			// no input values for this test

			int outputMVs = 1;
			// rslt
			expectedCppResults.add("1.8225e+006");

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
	}

	final static String PATH = "C:/Users/Christian/Downloads/Testbench/";
	final static String INCLUDE = "C:/Program Files (x86)/CLUViz/v6_1/SDK/include";
	final static String LIBPATH = "C:/Program Files (x86)/CLUViz/v6_1/SDK/lib";

	static TestbenchGenerator generator;
	static 	Map<String, Float> inputValues = new HashMap<String, Float>();
	static List<String> expectedCppResults = new ArrayList<String>();
	
	static void init() {
		inputValues.clear();
		expectedCppResults.clear();
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
			results.get(i).CPP = vector;
		}
		// read CLU original header
		printNextLine(scanner);
		// read CLU original outputs
		for (int i = 0; i < numElements; i++) {
			String vector = printNextLine(scanner);
			results.get(i).CLU_ORIGINAL = vector;
		}
		// read CLU opt header
		printNextLine(scanner);
		// read CLU opt outputs
		for (int i = 0; i < numElements; i++) {
			String vector = printNextLine(scanner);
			results.get(i).CLU_OPT = vector;
		}

		return results;
	}

	private static String printNextLine(Scanner scanner) {
		String line = scanner.nextLine();
		System.out.println(line);
		return line;
	}

	static void compare(String fileName, int numVectors) throws Exception {
		assertEquals("Wrong number of expected values for output multivectors", numVectors, expectedCppResults.size());
		generator = new TestbenchGenerator(fileName, PATH, inputValues);

		File exe = compile();
		Scanner scanner = run(exe);
		List<OutputSet> results = parseResult(scanner, numVectors);
		compareMultivectors(results);
	}
	
	static void compare(String fileName, String contents, int numVectors) throws Exception {
		assertEquals("Wrong number of expected values for output multivectors", numVectors, expectedCppResults.size());
		generator = new TestbenchGenerator(fileName, contents, PATH, inputValues);
		
		File exe = compile();
		Scanner scanner = run(exe);
		List<OutputSet> results = parseResult(scanner, numVectors);
		compareMultivectors(results);
	}

	private static void compareMultivectors(List<OutputSet> actualList) {
		for (int i = 0; i < expectedCppResults.size(); i++) {
			OutputSet actual = actualList.get(i);
			assertEquals("Original and optimized CLUCalc output are not equal", actual.CLU_ORIGINAL, actual.CLU_OPT);
			String cppResult = expectedCppResults.get(i);
			assertEquals("Expected and actual C++ results do not match", cppResult, actual.CPP);
		}
	}

}
