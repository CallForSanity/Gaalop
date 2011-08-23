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
		v1_1 = x1_0; // e1;
		v1_2 = y1_0; // e2;
		v2_1 = x2_0; // e1;
		v2_2 = y2_0; // e2;
		v3_1 = x3_0; // e1;
		v3_2 = y3_0; // e2;
		p1_1 = v1_1; // e1;
		p1_2 = v1_2; // e2;
		p1_4 = (((v1_1 / 2.0f) * v1_1) + ((v1_2 / 2.0f) * v1_2)); // einf;
		p2_1 = v2_1; // e1;
		p2_2 = v2_2; // e2;
		p2_4 = (((v2_1 / 2.0f) * v2_1) + ((v2_2 / 2.0f) * v2_2)); // einf;
		p3_1 = v3_1; // e1;
		p3_2 = v3_2; // e2;
		p3_4 = (((v3_1 / 2.0f) * v3_1) + ((v3_2 / 2.0f) * v3_2)); // einf;
		c_7 = ((((p1_2 * p2_4) + (-((p1_4 * p2_2)))) + (-((((p1_2 + (-p2_2))) * p3_4)))) + (((p1_4 + (-p2_4))) * p3_2)); // e1^e3;
		c_10 = (-(((((p1_1 * p2_4) + (-((p1_4 * p2_1)))) + (-((((p1_1 + (-p2_1))) * p3_4)))) + (((p1_4 + (-p2_4))) * p3_1)))); // e2^e3;
		c_13 = (-(((((((p1_1 * p2_2) + (-((p1_2 * p2_1))))) * p3_4) + (-(((((p1_1 * p2_4) + (-((p1_4 * p2_1))))) * p3_2)))) + ((((p1_2 * p2_4) + (-((p1_4 * p2_2))))) * p3_1)))); // e3^einf;
		c_14 = ((((p1_1 * p2_2) + (-((p1_2 * p2_1)))) + (-((((p1_1 + (-p2_1))) * p3_2)))) + (((p1_2 + (-p2_2))) * p3_1)); // e3^e0;
		mtmp_1 = ((-(((-c_14) * c_7))) + (c_7 * c_14)); // e1;
		mtmp_2 = ((-(((-c_14) * c_10))) + (c_10 * c_14)); // e2;
		mtmp_5 = (((-c_14) * c_14) + ((-c_14) * c_14)); // e0;
		mtmp_21 = ((-((c_7 * c_14))) + (-(((-c_14) * c_7)))); // e1^einf^e0;
		mtmp_24 = ((-((c_10 * c_14))) + (-(((-c_14) * c_10)))); // e2^einf^e0;
		m_1 = (((-mtmp_1) * ((-mtmp_5) / ((-mtmp_5) * (-mtmp_5)))) + ((-mtmp_5) * ((-mtmp_21) / ((-mtmp_5) * (-mtmp_5))))); // e1;
		m_2 = (((-mtmp_2) * ((-mtmp_5) / ((-mtmp_5) * (-mtmp_5)))) + ((-mtmp_5) * ((-mtmp_24) / ((-mtmp_5) * (-mtmp_5))))); // e2;
		r_0 = (float) Math.sqrt((float) Math.sqrt((float) Math.abs((((((((-((c_7 * c_7))) + (-((c_10 * c_10)))) + (c_13 * c_14)) + (c_14 * c_13))) * ((c_14 * c_14) / ((c_14 * c_14) * (c_14 * c_14)))) * ((((((-((c_7 * c_7))) + (-((c_10 * c_10)))) + (c_13 * c_14)) + (c_14 * c_13))) * ((c_14 * c_14) / ((c_14 * c_14) * (c_14 * c_14)))))))); // 1.0;
	}

	private float v1_1;
	private float v1_2;
	private float mtmp_5;
	private float c_7;
	private float v3_1;
	private float c_13;
	private float c_14;
	private float p1_2;
	private float p1_4;
	private float p2_4;
	private float c_10;
	private float p1_1;
	private float p2_2;
	private float p3_4;
	private float v3_2;
	private float p2_1;
	private float p3_2;
	private float p3_1;
	private float mtmp_24;
	private float mtmp_21;
	private float mtmp_1;
	private float v2_2;
	private float mtmp_2;
	private float v2_1;

}
