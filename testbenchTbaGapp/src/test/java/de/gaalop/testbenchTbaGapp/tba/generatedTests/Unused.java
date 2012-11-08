package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class Unused implements GAProgram {
		
	// input variables
	private double arw$0;
	private double arx$0;
	private double ary$0;
	private double lpz$0;
	private double arz$0;
	private double lpx$0;
	private double lpy$0;

	// local variables
	private double rotor$7;
	private double translator$11;
	private double translator$13;
	private double translator$8;

	// output variables
	private double Din$0;
	private double Din$10;
	private double Din$11;
	private double Din$13;
	private double Din$26;
	private double Din$6;
	private double Din$7;
	private double Din$8;

	@Override
	public double getValue(String varName) {
		if (varName.equals("Din$6")) return Din$6;
		if (varName.equals("Din$7")) return Din$7;
		if (varName.equals("Din$8")) return Din$8;
		if (varName.equals("Din$13")) return Din$13;
		if (varName.equals("Din$10")) return Din$10;
		if (varName.equals("Din$11")) return Din$11;
		if (varName.equals("Din$0")) return Din$0;
		if (varName.equals("Din$26")) return Din$26;
		return 0.0d;
	}

	@Override
	public HashMap<String,Double> getValues() {
		HashMap<String,Double> result = new HashMap<String,Double>();
		result.put("Din$6",Din$6);
		result.put("Din$7",Din$7);
		result.put("Din$8",Din$8);
		result.put("Din$13",Din$13);
		result.put("Din$10",Din$10);
		result.put("Din$11",Din$11);
		result.put("Din$0",Din$0);
		result.put("Din$26",Din$26);
		return result;
	}
	@Override
	public boolean setValue(String varName, double value) {
		if (varName.equals("arw$0")) { arw$0 = value; return true; }
		if (varName.equals("arx$0")) { arx$0 = value; return true; }
		if (varName.equals("ary$0")) { ary$0 = value; return true; }
		if (varName.equals("lpz$0")) { lpz$0 = value; return true; }
		if (varName.equals("arz$0")) { arz$0 = value; return true; }
		if (varName.equals("lpx$0")) { lpx$0 = value; return true; }
		if (varName.equals("lpy$0")) { lpy$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		rotor$7 = (-ary$0); // e1 ^ e3;
		translator$8 = (-((lpx$0 / 2.0d))); // e1 ^ einf;
		translator$11 = (-((lpy$0 / 2.0d))); // e2 ^ einf;
		translator$13 = (-((lpz$0 / 2.0d))); // e3 ^ einf;
		Din$0 = arw$0; // 1.0;
		Din$6 = arz$0; // e1 ^ e2;
		Din$7 = rotor$7; // e1 ^ e3;
		Din$8 = (((translator$8 * arw$0) + (-((translator$11 * arz$0)))) + (-((translator$13 * rotor$7)))); // e1 ^ einf;
		Din$10 = arx$0; // e2 ^ e3;
		Din$11 = (((translator$8 * arz$0) + (translator$11 * arw$0)) + (-((translator$13 * arx$0)))); // e2 ^ einf;
		Din$13 = (((translator$8 * rotor$7) + (translator$11 * arx$0)) + (translator$13 * arw$0)); // e3 ^ einf;
		Din$26 = (((translator$8 * arx$0) + (-((translator$11 * rotor$7)))) + (translator$13 * arz$0)); // e1 ^ (e2 ^ (e3 ^ einf));
	}


}
