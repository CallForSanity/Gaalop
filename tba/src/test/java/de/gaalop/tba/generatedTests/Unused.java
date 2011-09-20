package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class Unused implements GAProgram {
	// input variables
	private float arw$0;
	private float arx$0;
	private float lpz$0;
	private float ary$0;
	private float arz$0;
	private float lpx$0;
	private float lpy$0;

	// output variables
	private float Din$0;
	private float Din$1;
	private float Din$2;
	private float Din$3;
	private float Din$4;
	private float Din$5;
	private float Din$6;
	private float Din$7;
	private float Din$8;
	private float Din$9;
	private float Din$10;
	private float Din$11;
	private float Din$12;
	private float Din$13;
	private float Din$14;
	private float Din$15;
	private float Din$16;
	private float Din$17;
	private float Din$18;
	private float Din$19;
	private float Din$20;
	private float Din$21;
	private float Din$22;
	private float Din$23;
	private float Din$24;
	private float Din$25;
	private float Din$26;
	private float Din$27;
	private float Din$28;
	private float Din$29;
	private float Din$30;
	private float Din$31;

	@Override
	public float getValue(String varName) {
		if (varName.equals("Din$0")) return Din$0;
		if (varName.equals("Din$1")) return Din$1;
		if (varName.equals("Din$2")) return Din$2;
		if (varName.equals("Din$3")) return Din$3;
		if (varName.equals("Din$4")) return Din$4;
		if (varName.equals("Din$5")) return Din$5;
		if (varName.equals("Din$6")) return Din$6;
		if (varName.equals("Din$7")) return Din$7;
		if (varName.equals("Din$8")) return Din$8;
		if (varName.equals("Din$9")) return Din$9;
		if (varName.equals("Din$10")) return Din$10;
		if (varName.equals("Din$11")) return Din$11;
		if (varName.equals("Din$12")) return Din$12;
		if (varName.equals("Din$13")) return Din$13;
		if (varName.equals("Din$14")) return Din$14;
		if (varName.equals("Din$15")) return Din$15;
		if (varName.equals("Din$16")) return Din$16;
		if (varName.equals("Din$17")) return Din$17;
		if (varName.equals("Din$18")) return Din$18;
		if (varName.equals("Din$19")) return Din$19;
		if (varName.equals("Din$20")) return Din$20;
		if (varName.equals("Din$21")) return Din$21;
		if (varName.equals("Din$22")) return Din$22;
		if (varName.equals("Din$23")) return Din$23;
		if (varName.equals("Din$24")) return Din$24;
		if (varName.equals("Din$25")) return Din$25;
		if (varName.equals("Din$26")) return Din$26;
		if (varName.equals("Din$27")) return Din$27;
		if (varName.equals("Din$28")) return Din$28;
		if (varName.equals("Din$29")) return Din$29;
		if (varName.equals("Din$30")) return Din$30;
		if (varName.equals("Din$31")) return Din$31;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("Din$0",Din$0);
		result.put("Din$1",Din$1);
		result.put("Din$2",Din$2);
		result.put("Din$3",Din$3);
		result.put("Din$4",Din$4);
		result.put("Din$5",Din$5);
		result.put("Din$6",Din$6);
		result.put("Din$7",Din$7);
		result.put("Din$8",Din$8);
		result.put("Din$9",Din$9);
		result.put("Din$10",Din$10);
		result.put("Din$11",Din$11);
		result.put("Din$12",Din$12);
		result.put("Din$13",Din$13);
		result.put("Din$14",Din$14);
		result.put("Din$15",Din$15);
		result.put("Din$16",Din$16);
		result.put("Din$17",Din$17);
		result.put("Din$18",Din$18);
		result.put("Din$19",Din$19);
		result.put("Din$20",Din$20);
		result.put("Din$21",Din$21);
		result.put("Din$22",Din$22);
		result.put("Din$23",Din$23);
		result.put("Din$24",Din$24);
		result.put("Din$25",Din$25);
		result.put("Din$26",Din$26);
		result.put("Din$27",Din$27);
		result.put("Din$28",Din$28);
		result.put("Din$29",Din$29);
		result.put("Din$30",Din$30);
		result.put("Din$31",Din$31);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		if (varName.equals("arw$0")) { arw$0 = value; return true; }
		if (varName.equals("arx$0")) { arx$0 = value; return true; }
		if (varName.equals("lpz$0")) { lpz$0 = value; return true; }
		if (varName.equals("ary$0")) { ary$0 = value; return true; }
		if (varName.equals("arz$0")) { arz$0 = value; return true; }
		if (varName.equals("lpx$0")) { lpx$0 = value; return true; }
		if (varName.equals("lpy$0")) { lpy$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		rotor$7 = (-ary$0); // e1^e3;
		translator$8 = (-((0.5f * lpx$0))); // e1^einf;
		translator$11 = (-((0.5f * lpy$0))); // e2^einf;
		translator$13 = (-((0.5f * lpz$0))); // e3^einf;
		Din$0 = arw$0; // 1.0;
		Din$6 = arz$0; // e1^e2;
		Din$7 = (-ary$0); // e1^e3;
		Din$8 = (((-(((0.5f * ary$0) * lpz$0))) + ((0.5f * arz$0) * lpy$0)) - ((0.5f * arw$0) * lpx$0)); // e1^einf;
		Din$10 = arx$0; // e2^e3;
		Din$11 = ((((0.5f * arx$0) * lpz$0) - ((0.5f * arw$0) * lpy$0)) - ((0.5f * arz$0) * lpx$0)); // e2^einf;
		Din$13 = (((-(((0.5f * arw$0) * lpz$0))) - ((0.5f * arx$0) * lpy$0)) + ((0.5f * ary$0) * lpx$0)); // e3^einf;
		Din$26 = (((-(((0.5f * arz$0) * lpz$0))) - ((0.5f * ary$0) * lpy$0)) - ((0.5f * arx$0) * lpx$0)); // e1^e2^e3^einf;
	}

	private float rotor$7;
	private float translator$8;
	private float translator$13;
	private float translator$11;

}
