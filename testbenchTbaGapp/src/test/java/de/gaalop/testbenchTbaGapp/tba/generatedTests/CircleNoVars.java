package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class CircleNoVars implements GAProgram {
	// input variables

	// output variables
	private double m$1;
	private double r$0;
	private double m$2;

	@Override
	public double getValue(String varName) {
		if (varName.equals("m$1")) return m$1;
		if (varName.equals("r$0")) return r$0;
		if (varName.equals("m$2")) return m$2;
		return 0.0d;
	}

	@Override
	public HashMap<String,Double> getValues() {
		HashMap<String,Double> result = new HashMap<String,Double>();
		result.put("m$1",m$1);
		result.put("r$0",r$0);
		result.put("m$2",m$2);
		return result;
	}
	@Override
	public boolean setValue(String varName, double value) {
		return false;
	}
	
	@Override
	public void calculate() {
		m$1 = 1.7727272727272727d; // e1;
		m$2 = 4.863636363636364d; // e2;
		r$0 = 4.314591809121558d; // 1.0;
	}


}
