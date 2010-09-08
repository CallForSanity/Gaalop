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
 * Executes a suite of test comparing the original CLUCalc output with optimized CLUCalc and C++ outputs.
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
	
	private void compareMultivectors(List<OutputSet> actualList, List<String> cppValues) {
		for (int i = 0; i < actualList.size(); i++) {
			OutputSet actual = actualList.get(i);
			assertEquals(actual.CLU_ORIGINAL, actual.CLU_OPT);
			String cppResult = cppValues.get(i);
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
		String fileName = "C:/Users/Christian/Documents/Horizon.clu";
		inputValues.put("mx", 0.0f);
		inputValues.put("my", 0.0f);
		inputValues.put("mz", 0.0f);
		inputValues.put("px", 1.0f);
		inputValues.put("py", 1.0f);
		inputValues.put("pz", 1.0f);
		inputValues.put("r", 0.5f);
		
		expectedCppResults.add("0.125^(e1^e) - 1^(e1^e0) + 0.125^(e2^e) - 1^(e2^e0) + 0.125^(e3^e) - 1^(e3^e0) - 0.25^E");
		
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
		// FIXME: copy test cases and reference workspace-relative paths
		String fileName = "C:/Users/Christian/Documents/Masterarbeit/GaalopCompiler/gravisma-2010/FPGA/IK_Gaalop-2.0_input.clu";
		inputValues.put("pwx", 1.0f);
		inputValues.put("pwy", 1.0f);
		inputValues.put("pwz", 1.0f);
		inputValues.put("d1", 1.50f);
		inputValues.put("d2", 1.10f);
		inputValues.put("phi", (float) Math.PI);
		
		expectedCppResults.add("0");
		
		generator = new TestbenchGenerator(fileName, PATH, inputValues);
		compare(1);
	}

}
