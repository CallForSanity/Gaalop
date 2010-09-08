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
public class CluCalcCppTest {

	public static class OutputSet {
		public String CPP;
		public String CLU_ORIGINAL;
		public String CLU_OPT;
	}

	private final static String PATH = "C:/Users/Christian/Downloads/Testbench/";
	private final static String INCLUDE = "C:/Program Files (x86)/CLUViz/v6_1/SDK/include";
	private final static String LIBPATH = "C:/Program Files (x86)/CLUViz/v6_1/SDK/lib";

	private TestbenchGenerator generator;
	private Map<String, Float> inputValues = new HashMap<String, Float>();
	private List<String> expectedCppResults = new ArrayList<String>();

	@Before
	public void init() {
		inputValues.clear();
		expectedCppResults.clear();
	}

	private File compile() {
		generator.run();
		generator.createCompileScript(INCLUDE, LIBPATH);
		return generator.compile();
	}

	private Scanner run(File executable) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(executable.getAbsolutePath());
		pb.directory(executable.getParentFile());
		Process p = pb.start();
		return new Scanner(p.getInputStream());
	}

	private List<OutputSet> parseResult(Scanner scanner, int numElements) {
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

	private String printNextLine(Scanner scanner) {
		String line = scanner.nextLine();
		System.out.println(line);
		return line;
	}

	private void compare(int numVectors) throws IOException {
		File exe = compile();
		Scanner scanner = run(exe);
		List<OutputSet> results = parseResult(scanner, numVectors);
		compareMultivectors(results, expectedCppResults);
	}

	private void compareMultivectors(List<OutputSet> actualList, List<String> expectedCppValues) {
		for (int i = 0; i < expectedCppValues.size(); i++) {
			OutputSet actual = actualList.get(i);
			assertEquals(actual.CLU_ORIGINAL, actual.CLU_OPT);
			String cppResult = expectedCppValues.get(i);
			assertEquals(cppResult, actual.CPP);
		}
	}

	/**
	 * Tests the Horizon.clu example.
	 * 
	 * @throws IOException
	 */
	@Test
	public void horizon() throws IOException {
		String fileName = getClass().getResource("/de/gaalop/Horizon.clu").getFile();
		inputValues.put("mx", 0.0f);
		inputValues.put("my", 0.0f);
		inputValues.put("mz", 0.0f);
		inputValues.put("px", 1.0f);
		inputValues.put("py", 1.0f);
		inputValues.put("pz", 1.0f);
		inputValues.put("r", 0.5f);

		// C
		expectedCppResults
				.add("0.125^(e1^e) - 1^(e1^e0) + 0.125^(e2^e) - 1^(e2^e0) + 0.125^(e3^e) - 1^(e3^e0) - 0.25^E");

		generator = new TestbenchGenerator(fileName, PATH, inputValues);
		compare(1);
	}

	/**
	 * Tests the inverse kinematics algorithm.
	 * 
	 * @throws IOException
	 */
	@Test
	public void inverseKinematics() throws IOException {
		String fileName = getClass().getResource("/de/gaalop/IK_Gaalop-2.0_input.clu").getFile();
		inputValues.put("pwx", 1.1f);
		inputValues.put("pwy", 1.1f);
		inputValues.put("pwz", 1.1f);
		inputValues.put("d1", 1.40f);
		inputValues.put("d2", 1.30f);
		inputValues.put("phi", (float) Math.PI);

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

		generator = new TestbenchGenerator(fileName, PATH, inputValues);
		compare(6);
	}

	/**
	 * Tests all-control-flow.clu example.
	 * 
	 * @throws IOException
	 */
	@Test
	public void allControlFlow() throws IOException {
		String fileName = getClass().getResource("/de/gaalop/all-control-flow.clu").getFile();
		// no input variables for this test

		// x
		expectedCppResults.add("3e+010");

		generator = new TestbenchGenerator(fileName, PATH, inputValues);
		compare(1);
	}

}
