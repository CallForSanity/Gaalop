package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class CircleOnlyVars implements GAProgram {
	// input variables
	private float y1_0;
	private float y2_0;
	private float y3_0;
	private float x2_0;
	private float x3_0;
	private float x1_0;

	// output variables
	private float m_1;
	private float r_0;
	private float m_2;

	@Override
	public float getValue(String varName) {
		if (varName.equals("m_1")) return m_1;
		if (varName.equals("r_0")) return r_0;
		if (varName.equals("m_2")) return m_2;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("m_1",m_1);
		result.put("r_0",r_0);
		result.put("m_2",m_2);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		if (varName.equals("y1_0")) { y1_0 = value; return true; }
		if (varName.equals("y2_0")) { y2_0 = value; return true; }
		if (varName.equals("y3_0")) { y3_0 = value; return true; }
		if (varName.equals("x2_0")) { x2_0 = value; return true; }
		if (varName.equals("x3_0")) { x3_0 = value; return true; }
		if (varName.equals("x1_0")) { x1_0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		float p1_4 = (((x1_0 / 2.0f) * x1_0) + ((y1_0 / 2.0f) * y1_0));
		float p2_4 = (((x2_0 / 2.0f) * x2_0) + ((y2_0 / 2.0f) * y2_0));
		float p3_4 = (((x3_0 / 2.0f) * x3_0) + ((y3_0 / 2.0f) * y3_0));
		float c_7 = ((((y1_0 * p2_4) + (-((p1_4 * y2_0)))) + (-((((y1_0 + (-y2_0))) * p3_4)))) + (((p1_4 + (-p2_4))) * y3_0));
		float c_10 = (-(((((x1_0 * p2_4) + (-((p1_4 * x2_0)))) + (-((((x1_0 + (-x2_0))) * p3_4)))) + (((p1_4 + (-p2_4))) * x3_0))));
		float c_13 = (-(((((((x1_0 * y2_0) + (-((y1_0 * x2_0))))) * p3_4) + (-(((((x1_0 * p2_4) + (-((p1_4 * x2_0))))) * y3_0)))) + ((((y1_0 * p2_4) + (-((p1_4 * y2_0))))) * x3_0))));
		float c_14 = ((((x1_0 * y2_0) + (-((y1_0 * x2_0)))) + (-((((x1_0 + (-x2_0))) * y3_0)))) + (((y1_0 + (-y2_0))) * x3_0));
		float mtmp_1 = ((-(((-c_14) * c_7))) + (c_7 * c_14));
		float mtmp_2 = ((-(((-c_14) * c_10))) + (c_10 * c_14));
		float mtmp_5 = (((-c_14) * c_14) + ((-c_14) * c_14));
		float mtmp_21 = ((-((c_7 * c_14))) + (-(((-c_14) * c_7))));
		float mtmp_24 = ((-((c_10 * c_14))) + (-(((-c_14) * c_10))));
		m_1 = (((-mtmp_1) * ((-mtmp_5) / ((-mtmp_5) * (-mtmp_5)))) + ((-mtmp_5) * ((-mtmp_21) / ((-mtmp_5) * (-mtmp_5)))));
		m_2 = (((-mtmp_2) * ((-mtmp_5) / ((-mtmp_5) * (-mtmp_5)))) + ((-mtmp_5) * ((-mtmp_24) / ((-mtmp_5) * (-mtmp_5)))));
		r_0 = (float) Math.sqrt((float) Math.sqrt(Math.abs((((((((-((c_7 * c_7))) + (-((c_10 * c_10)))) + (c_13 * c_14)) + (c_14 * c_13))) * ((c_14 * c_14) / ((c_14 * c_14) * (c_14 * c_14)))) * ((((((-((c_7 * c_7))) + (-((c_10 * c_10)))) + (c_13 * c_14)) + (c_14 * c_13))) * ((c_14 * c_14) / ((c_14 * c_14) * (c_14 * c_14))))))));
	}
}
