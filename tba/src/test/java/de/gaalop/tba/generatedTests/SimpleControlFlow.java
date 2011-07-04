package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class SimpleControlFlow implements GAProgram {
	// input variables
	private float t_0;

	// output variables
	private float a_0;
	private float c_0;
	private float b_0;

	@Override
	public float getValue(String varName) {
		if (varName.equals("a_0")) return a_0;
		if (varName.equals("c_0")) return c_0;
		if (varName.equals("b_0")) return b_0;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("a_0",a_0);
		result.put("c_0",c_0);
		result.put("b_0",b_0);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		if (varName.equals("t_0")) { t_0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		a_0 = 1.0f;
		c_0 = 4.0f;
		if ((a_0 == t_0)) {
			a_0 = 2.0f;
		} else {
			a_0 = 3.0f;
		}
		b_0 = (a_0 + 1.0f);
	}
}
