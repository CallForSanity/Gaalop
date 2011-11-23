package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class CircleNoVars implements GAProgram {
	// input variables

	// output variables
	private float m$1;
	private float r$0;
	private float m$2;

	@Override
	public float getValue(String varName) {
		if (varName.equals("m$1")) return m$1;
		if (varName.equals("r$0")) return r$0;
		if (varName.equals("m$2")) return m$2;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("m$1",m$1);
		result.put("r$0",r$0);
		result.put("m$2",m$2);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		return false;
	}
	
	@Override
	public void calculate() {
		m$1 = 1.7727273f; // e1;
		m$2 = 4.863636f; // e2;
		r$0 = 4.314592f; // 1.0;
	}


}
