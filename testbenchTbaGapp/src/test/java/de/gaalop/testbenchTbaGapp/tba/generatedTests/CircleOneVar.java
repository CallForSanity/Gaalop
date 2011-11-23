package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class CircleOneVar implements GAProgram {
	// input variables
	private float x1$0;

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
		if (varName.equals("x1$0")) { x1$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		p1$4 = (((x1$0 / 2.0f) * x1$0) + 2.0f); // einf;
		c$7 = (((90.0f + (-((p1$4 * 9.0f)))) + 182.0f) + (((p1$4 + -45.0f)) * 4.0f)); // e1 ^ e3;
		c$10 = (-(((((x1$0 * 45.0f) + (-((p1$4 * 3.0f)))) + (-((((x1$0 + -3.0f)) * 26.0f)))) + (((p1$4 + -45.0f)) * 6.0f)))); // e2 ^ e3;
		c$13 = (-(((((((x1$0 * 9.0f) + -6.0f)) * 26.0f) + (-(((((x1$0 * 45.0f) + (-((p1$4 * 3.0f))))) * 4.0f)))) + (((90.0f + (-((p1$4 * 9.0f))))) * 6.0f)))); // e3 ^ einf;
		c$14 = ((((x1$0 * 9.0f) + -6.0f) + (-((((x1$0 + -3.0f)) * 4.0f)))) + -42.0f); // e3 ^ e0;
		mtmp$1 = ((-(((-c$14) * c$7))) + (c$7 * c$14)); // e1;
		mtmp$2 = ((-(((-c$14) * c$10))) + (c$10 * c$14)); // e2;
		mtmp$5 = (((-c$14) * c$14) + ((-c$14) * c$14)); // e0;
		mtmp$21 = ((-((c$7 * c$14))) + (-(((-c$14) * c$7)))); // e1 ^ (einf ^ e0);
		mtmp$24 = ((-((c$10 * c$14))) + (-(((-c$14) * c$10)))); // e2 ^ (einf ^ e0);
		m$1 = (((-mtmp$1) * ((-mtmp$5) / ((-mtmp$5) * (-mtmp$5)))) + ((-mtmp$5) * (mtmp$21 / ((-mtmp$5) * (-mtmp$5))))); // e1;
		m$2 = (((-mtmp$2) * ((-mtmp$5) / ((-mtmp$5) * (-mtmp$5)))) + ((-mtmp$5) * (mtmp$24 / ((-mtmp$5) * (-mtmp$5))))); // e2;
		r$0 = (float) Math.sqrt((float) Math.sqrt((float) Math.abs((((((((-((c$7 * c$7))) + (-((c$10 * c$10)))) + (c$13 * c$14)) + (c$14 * c$13))) * ((c$14 * c$14) / ((c$14 * c$14) * (c$14 * c$14)))) * ((((((-((c$7 * c$7))) + (-((c$10 * c$10)))) + (c$13 * c$14)) + (c$14 * c$13))) * ((c$14 * c$14) / ((c$14 * c$14) * (c$14 * c$14)))))))); // 1.0;
	}

	private float p1$4;
	private float c$7;
	private float mtmp$1;
	private float mtmp$21;
	private float mtmp$5;
	private float mtmp$2;
	private float c$14;
	private float mtmp$24;
	private float c$13;
	private float c$10;

}
