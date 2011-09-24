package de.gaalop.tba.generatedTests;

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
		y1$0 = 2.0f; // 1.0;
		x2$0 = 3.0f; // 1.0;
		y2$0 = 9.0f; // 1.0;
		x3$0 = 6.0f; // 1.0;
		y3$0 = 4.0f; // 1.0;
		v1$1 = ((x1$0 * 1.0f) * 1.0f); // e1;
		v1$2 = ((y1$0 * 1.0f) * 1.0f); // e2;
		v2$1 = ((x2$0 * 1.0f) * 1.0f); // e1;
		v2$2 = ((y2$0 * 1.0f) * 1.0f); // e2;
		v3$1 = ((x3$0 * 1.0f) * 1.0f); // e1;
		v3$2 = ((y3$0 * 1.0f) * 1.0f); // e2;
		p1$1 = v1$1; // e1;
		p1$2 = v1$2; // e2;
		p1$4 = ((((((((0.5f * v1$1) * 1.0f) * v1$1) * 1.0f) + ((((0.5f * v1$2) * 1.0f) * v1$2) * 1.0f))) * 1.0f) * 1.0f); // einf;
		p1$5 = 1.0f; // e0;
		p1$17 = ((((((((0.5f * v1$1) * 1.0f) * v1$2) * 1.0f) + ((((0.5f * v1$2) * 1.0f) * v1$1) * -1.0f))) * 1.0f) * 1.0f); // e1^e2^einf;
		p2$1 = v2$1; // e1;
		p2$2 = v2$2; // e2;
		p2$4 = ((((((((0.5f * v2$1) * 1.0f) * v2$1) * 1.0f) + ((((0.5f * v2$2) * 1.0f) * v2$2) * 1.0f))) * 1.0f) * 1.0f); // einf;
		p2$5 = 1.0f; // e0;
		p2$17 = ((((((((0.5f * v2$1) * 1.0f) * v2$2) * 1.0f) + ((((0.5f * v2$2) * 1.0f) * v2$1) * -1.0f))) * 1.0f) * 1.0f); // e1^e2^einf;
		p3$1 = v3$1; // e1;
		p3$2 = v3$2; // e2;
		p3$4 = ((((((((0.5f * v3$1) * 1.0f) * v3$1) * 1.0f) + ((((0.5f * v3$2) * 1.0f) * v3$2) * 1.0f))) * 1.0f) * 1.0f); // einf;
		p3$5 = 1.0f; // e0;
		p3$17 = ((((((((0.5f * v3$1) * 1.0f) * v3$2) * 1.0f) + ((((0.5f * v3$2) * 1.0f) * v3$1) * -1.0f))) * 1.0f) * 1.0f); // e1^e2^einf;
		c$7 = (((-(((((((((p1$2 * p2$4) * 1.0f) + ((p1$4 * p2$2) * -1.0f))) * p3$5) * 1.0f) + ((((((p1$2 * p2$5) * 1.0f) + ((p1$5 * p2$2) * -1.0f))) * p3$4) * -1.0f)) + ((((((p1$4 * p2$5) * 1.0f) + ((p1$5 * p2$4) * -1.0f))) * p3$2) * 1.0f)))) * ((1.0f * ((1.0f * ((1.0f * ((1.0f * 1.0f) * 1.0f)) * 1.0f)) * 1.0f)) * 1.0f)) * -1.0f); // e1^e3;
		c$10 = (((-(((((((((p1$1 * p2$4) * 1.0f) + ((p1$4 * p2$1) * -1.0f))) * p3$5) * 1.0f) + ((((((p1$1 * p2$5) * 1.0f) + ((p1$5 * p2$1) * -1.0f))) * p3$4) * -1.0f)) + ((((((p1$4 * p2$5) * 1.0f) + ((p1$5 * p2$4) * -1.0f))) * p3$1) * 1.0f)))) * ((1.0f * ((1.0f * ((1.0f * ((1.0f * 1.0f) * 1.0f)) * 1.0f)) * 1.0f)) * 1.0f)) * 1.0f); // e2^e3;
		c$13 = (((-(((((((((p1$1 * p2$2) * 1.0f) + ((p1$2 * p2$1) * -1.0f))) * p3$4) * 1.0f) + ((((((p1$1 * p2$4) * 1.0f) + ((p1$4 * p2$1) * -1.0f))) * p3$2) * -1.0f)) + ((((((p1$2 * p2$4) * 1.0f) + ((p1$4 * p2$2) * -1.0f))) * p3$1) * 1.0f)))) * ((1.0f * ((1.0f * ((1.0f * ((1.0f * 1.0f) * 1.0f)) * 1.0f)) * 1.0f)) * 1.0f)) * 1.0f); // e3^einf;
		c$14 = (((-(((((((((p1$1 * p2$2) * 1.0f) + ((p1$2 * p2$1) * -1.0f))) * p3$5) * 1.0f) + ((((((p1$1 * p2$5) * 1.0f) + ((p1$5 * p2$1) * -1.0f))) * p3$2) * -1.0f)) + ((((((p1$2 * p2$5) * 1.0f) + ((p1$5 * p2$2) * -1.0f))) * p3$1) * 1.0f)))) * ((1.0f * ((1.0f * ((1.0f * ((1.0f * 1.0f) * 1.0f)) * 1.0f)) * 1.0f)) * 1.0f)) * -1.0f); // e3^e0;
		mtmp$1 = (((((c$14 * 1.0f) * -1.0f) * c$7) * -1.0f) + ((((c$7 * 1.0f) * 1.0f) * c$14) * 1.0f)); // e1;
		mtmp$2 = (((((c$14 * 1.0f) * -1.0f) * c$10) * -1.0f) + ((((c$10 * 1.0f) * 1.0f) * c$14) * 1.0f)); // e2;
		mtmp$4 = (((((((c$14 * 1.0f) * -1.0f) * c$13) * 1.0f) + ((((c$7 * 1.0f) * 1.0f) * c$7) * -1.0f)) + ((((c$10 * 1.0f) * 1.0f) * c$10) * -1.0f)) + ((((c$14 * 1.0f) * -1.0f) * c$13) * -1.0f)); // einf;
		mtmp$5 = (((((c$14 * 1.0f) * -1.0f) * c$14) * 1.0f) + ((((c$14 * 1.0f) * -1.0f) * c$14) * 1.0f)); // e0;
		mtmp$17 = (((((c$7 * 1.0f) * 1.0f) * c$10) * -1.0f) + ((((c$10 * 1.0f) * 1.0f) * c$7) * 1.0f)); // e1^e2^einf;
		mtmp$21 = (((((c$7 * 1.0f) * 1.0f) * c$14) * -1.0f) + ((((c$14 * 1.0f) * -1.0f) * c$7) * -1.0f)); // e1^einf^e0;
		mtmp$24 = (((((c$10 * 1.0f) * 1.0f) * c$14) * -1.0f) + ((((c$14 * 1.0f) * -1.0f) * c$10) * -1.0f)); // e2^einf^e0;
		m$1 = ((((-mtmp$1) * (((mtmp$5 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f) + (((-mtmp$5) * (((mtmp$21 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)); // e1;
		m$2 = ((((-mtmp$2) * (((mtmp$5 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f) + (((-mtmp$5) * (((mtmp$24 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)); // e2;
		m$4 = (((((((-mtmp$1) * (((mtmp$21 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f) + (((-mtmp$2) * (((mtmp$24 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)) + (((-mtmp$4) * (((mtmp$5 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)) + (((-mtmp$21) * (((mtmp$21 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * -1.0f)) + (((-mtmp$24) * (((mtmp$24 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * -1.0f)); // einf;
		m$5 = (((-mtmp$5) * (((mtmp$5 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f); // e0;
		m$17 = (((((((-mtmp$1) * (((mtmp$24 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f) + (((-mtmp$2) * (((mtmp$21 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * -1.0f)) + (((-mtmp$17) * (((mtmp$5 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)) + (((-mtmp$21) * (((mtmp$24 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * -1.0f)) + (((-mtmp$24) * (((mtmp$21 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)); // e1^e2^einf;
		m$21 = ((((-mtmp$5) * (((mtmp$21 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f) + (((-mtmp$21) * (((mtmp$5 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)); // e1^einf^e0;
		m$24 = ((((-mtmp$5) * (((mtmp$24 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f) + (((-mtmp$24) * (((mtmp$5 * 1.0f) * -1.0f) / ((((mtmp$5 * 1.0f) * -1.0f) * ((mtmp$5 * 1.0f) * -1.0f)) * 1.0f))) * 1.0f)); // e2^einf^e0;
		r$0 = (float) Math.sqrt((float) Math.sqrt((float) Math.abs(((((((((((c$7 * c$7) * -1.0f) + ((c$10 * c$10) * -1.0f)) + ((c$13 * c$14) * 1.0f)) + ((c$14 * c$13) * 1.0f))) * (((((1.0f * c$14) * 1.0f) * ((1.0f * c$14) * 1.0f)) * 1.0f) / ((((((1.0f * c$14) * 1.0f) * ((1.0f * c$14) * 1.0f)) * 1.0f) * ((((1.0f * c$14) * 1.0f) * ((1.0f * c$14) * 1.0f)) * 1.0f)) * 1.0f))) * 1.0f) * ((((((((c$7 * c$7) * -1.0f) + ((c$10 * c$10) * -1.0f)) + ((c$13 * c$14) * 1.0f)) + ((c$14 * c$13) * 1.0f))) * (((((1.0f * c$14) * 1.0f) * ((1.0f * c$14) * 1.0f)) * 1.0f) / ((((((1.0f * c$14) * 1.0f) * ((1.0f * c$14) * 1.0f)) * 1.0f) * ((((1.0f * c$14) * 1.0f) * ((1.0f * c$14) * 1.0f)) * 1.0f)) * 1.0f))) * 1.0f)) * 1.0f)))); // 1.0;
	}

	private float y1$0;
	private float m$17;
	private float m$4;
	private float m$5;
	private float mtmp$21;
	private float c$14;
	private float mtmp$24;
	private float p2$1;
	private float c$13;
	private float c$10;
	private float p2$4;
	private float mtmp$17;
	private float p2$5;
	private float p2$2;
	private float c$7;
	private float v2$1;
	private float x2$0;
	private float v2$2;
	private float v1$2;
	private float v1$1;
	private float p1$17;
	private float p3$5;
	private float p3$4;
	private float p2$17;
	private float p3$1;
	private float p3$2;
	private float m$24;
	private float x3$0;
	private float p3$17;
	private float v3$1;
	private float v3$2;
	private float m$21;
	private float p1$1;
	private float p1$2;
	private float y3$0;
	private float p1$4;
	private float p1$5;
	private float mtmp$1;
	private float mtmp$4;
	private float mtmp$5;
	private float mtmp$2;
	private float y2$0;

}
