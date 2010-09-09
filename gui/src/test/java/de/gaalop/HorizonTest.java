package de.gaalop;

import java.util.HashMap;
import java.util.Map;

public class HorizonTest {

	public static void main(String[] args) throws CodeParserException, OptimizationException {
		String fileName = args[0];
		String path = args[1];
		Map<String, Float> inputValues = new HashMap<String, Float>();
		inputValues.put("mx", 0.0f);
		inputValues.put("my", 0.0f);
		inputValues.put("mz", 0.0f);
		inputValues.put("px", 1.0f);
		inputValues.put("py", 1.0f);
		inputValues.put("pz", 1.0f);
		inputValues.put("r", 0.5f);
		
		TestbenchGenerator generator = new TestbenchGenerator(fileName, path, inputValues);
		generator.run();
		
		String include = "C:/Program Files (x86)/CLUViz/v6_1/SDK/include";
		String libpath = "C:/Program Files (x86)/CLUViz/v6_1/SDK/lib";
		generator.createCompileScript(include, libpath);
		generator.compile();
	}
	
}
