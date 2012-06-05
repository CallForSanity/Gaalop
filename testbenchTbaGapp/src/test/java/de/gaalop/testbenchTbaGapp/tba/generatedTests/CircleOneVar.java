package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class CircleOneVar implements GAProgram {
	// input variables
	private double x1$0;

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
		if (varName.equals("x1$0")) { x1$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		p1$4 = (((x1$0 / 2.0d) * x1$0) + 2.0d); // einf;
		c$7 = (((90.0d + (-((p1$4 * 9.0d)))) + 182.0d) + (((p1$4 + -45.0d)) * 4.0d)); // e1 ^ e3;
		c$10 = (-(((((x1$0 * 45.0d) + (-((p1$4 * 3.0d)))) + (-((((x1$0 + -3.0d)) * 26.0d)))) + (((p1$4 + -45.0d)) * 6.0d)))); // e2 ^ e3;
		c$13 = (-(((((((x1$0 * 9.0d) + -6.0d)) * 26.0d) + (-(((((x1$0 * 45.0d) + (-((p1$4 * 3.0d))))) * 4.0d)))) + (((90.0d + (-((p1$4 * 9.0d))))) * 6.0d)))); // e3 ^ einf;
		c$14 = ((((x1$0 * 9.0d) + -6.0d) + (-((((x1$0 + -3.0d)) * 4.0d)))) + -42.0d); // e3 ^ e0;
		mtmp$1 = ((-(((-c$14) * c$7))) + (c$7 * c$14)); // e1;
		mtmp$2 = ((-(((-c$14) * c$10))) + (c$10 * c$14)); // e2;
		mtmp$5 = (((-c$14) * c$14) + ((-c$14) * c$14)); // e0;
		mtmp$21 = ((-((c$7 * c$14))) + (-(((-c$14) * c$7)))); // e1 ^ (einf ^ e0);
		mtmp$24 = ((-((c$10 * c$14))) + (-(((-c$14) * c$10)))); // e2 ^ (einf ^ e0);
		m$1 = (((-mtmp$1) * ((-mtmp$5) / ((-mtmp$5) * (-mtmp$5)))) + ((-mtmp$5) * (mtmp$21 / ((-mtmp$5) * (-mtmp$5))))); // e1;
		m$2 = (((-mtmp$2) * ((-mtmp$5) / ((-mtmp$5) * (-mtmp$5)))) + ((-mtmp$5) * (mtmp$24 / ((-mtmp$5) * (-mtmp$5))))); // e2;
		r$0 = (double) Math.sqrt((double) Math.sqrt((double) Math.abs((((((((-((c$7 * c$7))) + (-((c$10 * c$10)))) + (c$13 * c$14)) + (c$14 * c$13))) * ((c$14 * c$14) / ((c$14 * c$14) * (c$14 * c$14)))) * ((((((-((c$7 * c$7))) + (-((c$10 * c$10)))) + (c$13 * c$14)) + (c$14 * c$13))) * ((c$14 * c$14) / ((c$14 * c$14) * (c$14 * c$14)))))))); // 1.0;
	}

	private double p1$4;
	private double c$7;
	private double mtmp$1;
	private double mtmp$21;
	private double mtmp$5;
	private double mtmp$2;
	private double c$14;
	private double mtmp$24;
	private double c$13;
	private double c$10;

}
