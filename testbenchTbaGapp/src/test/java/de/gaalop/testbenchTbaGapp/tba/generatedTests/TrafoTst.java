package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class TrafoTst implements GAProgram {
		
	// input variables

	// local variables

	// output variables
	private double p$1;
	private double p$2;
	private double p$4;
	private double p$5;
	private double pt$1;
	private double pt$2;
	private double pt$4;
	private double pt$5;
	private double pt2$1;
	private double pt2$2;
	private double pt2$4;
	private double pt2$5;
	private double pt3$1;
	private double pt3$2;
	private double pt3$4;
	private double pt3$5;

	@Override
	public double getValue(String varName) {
		if (varName.equals("pt2$5")) return pt2$5;
		if (varName.equals("pt2$4")) return pt2$4;
		if (varName.equals("pt2$2")) return pt2$2;
		if (varName.equals("pt2$1")) return pt2$1;
		if (varName.equals("pt3$4")) return pt3$4;
		if (varName.equals("pt3$1")) return pt3$1;
		if (varName.equals("pt3$2")) return pt3$2;
		if (varName.equals("pt$1")) return pt$1;
		if (varName.equals("pt$2")) return pt$2;
		if (varName.equals("pt$5")) return pt$5;
		if (varName.equals("pt$4")) return pt$4;
		if (varName.equals("p$1")) return p$1;
		if (varName.equals("p$5")) return p$5;
		if (varName.equals("p$4")) return p$4;
		if (varName.equals("p$2")) return p$2;
		if (varName.equals("pt3$5")) return pt3$5;
		return 0.0d;
	}

	@Override
	public HashMap<String,Double> getValues() {
		HashMap<String,Double> result = new HashMap<String,Double>();
		result.put("pt2$5",pt2$5);
		result.put("pt2$4",pt2$4);
		result.put("pt2$2",pt2$2);
		result.put("pt2$1",pt2$1);
		result.put("pt3$4",pt3$4);
		result.put("pt3$1",pt3$1);
		result.put("pt3$2",pt3$2);
		result.put("pt$1",pt$1);
		result.put("pt$2",pt$2);
		result.put("pt$5",pt$5);
		result.put("pt$4",pt$4);
		result.put("p$1",p$1);
		result.put("p$5",p$5);
		result.put("p$4",p$4);
		result.put("p$2",p$2);
		result.put("pt3$5",pt3$5);
		return result;
	}
	@Override
	public boolean setValue(String varName, double value) {
		return false;
	}
	
	@Override
	public void calculate() {
		p$1 = 3.0d; // e1;
		p$2 = 6.0d; // e2;
		p$4 = 22.5d; // einf;
		p$5 = 1.0d; // e0;
		pt$1 = 4.0d; // e1;
		pt$2 = 8.0d; // e2;
		pt$4 = 40.0d; // einf;
		pt$5 = 1.0d; // e0;
		pt2$1 = 3.0d; // e1;
		pt2$2 = 6.0d; // e2;
		pt2$4 = 22.5d; // einf;
		pt2$5 = 1.0d; // e0;
		pt3$1 = 3.0d; // e1;
		pt3$2 = 6.0d; // e2;
		pt3$4 = 22.5d; // einf;
		pt3$5 = 1.0d; // e0;
	}


}
