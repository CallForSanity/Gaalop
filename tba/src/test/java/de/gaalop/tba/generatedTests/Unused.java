package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class Unused implements GAProgram {
	// input variables
	private float arw_0;
	private float arx_0;
	private float lpz_0;
	private float ary_0;
	private float arz_0;
	private float lpx_0;
	private float lpy_0;

	// output variables
	private float Din_0;
	private float Din_1;
	private float Din_2;
	private float Din_3;
	private float Din_4;
	private float Din_5;
	private float Din_6;
	private float Din_7;
	private float Din_8;
	private float Din_9;
	private float Din_10;
	private float Din_11;
	private float Din_12;
	private float Din_13;
	private float Din_14;
	private float Din_15;
	private float Din_16;
	private float Din_17;
	private float Din_18;
	private float Din_19;
	private float Din_20;
	private float Din_21;
	private float Din_22;
	private float Din_23;
	private float Din_24;
	private float Din_25;
	private float Din_26;
	private float Din_27;
	private float Din_28;
	private float Din_29;
	private float Din_30;
	private float Din_31;

	@Override
	public float getValue(String varName) {
		if (varName.equals("Din_0")) return Din_0;
		if (varName.equals("Din_1")) return Din_1;
		if (varName.equals("Din_2")) return Din_2;
		if (varName.equals("Din_3")) return Din_3;
		if (varName.equals("Din_4")) return Din_4;
		if (varName.equals("Din_5")) return Din_5;
		if (varName.equals("Din_6")) return Din_6;
		if (varName.equals("Din_7")) return Din_7;
		if (varName.equals("Din_8")) return Din_8;
		if (varName.equals("Din_9")) return Din_9;
		if (varName.equals("Din_10")) return Din_10;
		if (varName.equals("Din_11")) return Din_11;
		if (varName.equals("Din_12")) return Din_12;
		if (varName.equals("Din_13")) return Din_13;
		if (varName.equals("Din_14")) return Din_14;
		if (varName.equals("Din_15")) return Din_15;
		if (varName.equals("Din_16")) return Din_16;
		if (varName.equals("Din_17")) return Din_17;
		if (varName.equals("Din_18")) return Din_18;
		if (varName.equals("Din_19")) return Din_19;
		if (varName.equals("Din_20")) return Din_20;
		if (varName.equals("Din_21")) return Din_21;
		if (varName.equals("Din_22")) return Din_22;
		if (varName.equals("Din_23")) return Din_23;
		if (varName.equals("Din_24")) return Din_24;
		if (varName.equals("Din_25")) return Din_25;
		if (varName.equals("Din_26")) return Din_26;
		if (varName.equals("Din_27")) return Din_27;
		if (varName.equals("Din_28")) return Din_28;
		if (varName.equals("Din_29")) return Din_29;
		if (varName.equals("Din_30")) return Din_30;
		if (varName.equals("Din_31")) return Din_31;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("Din_0",Din_0);
		result.put("Din_1",Din_1);
		result.put("Din_2",Din_2);
		result.put("Din_3",Din_3);
		result.put("Din_4",Din_4);
		result.put("Din_5",Din_5);
		result.put("Din_6",Din_6);
		result.put("Din_7",Din_7);
		result.put("Din_8",Din_8);
		result.put("Din_9",Din_9);
		result.put("Din_10",Din_10);
		result.put("Din_11",Din_11);
		result.put("Din_12",Din_12);
		result.put("Din_13",Din_13);
		result.put("Din_14",Din_14);
		result.put("Din_15",Din_15);
		result.put("Din_16",Din_16);
		result.put("Din_17",Din_17);
		result.put("Din_18",Din_18);
		result.put("Din_19",Din_19);
		result.put("Din_20",Din_20);
		result.put("Din_21",Din_21);
		result.put("Din_22",Din_22);
		result.put("Din_23",Din_23);
		result.put("Din_24",Din_24);
		result.put("Din_25",Din_25);
		result.put("Din_26",Din_26);
		result.put("Din_27",Din_27);
		result.put("Din_28",Din_28);
		result.put("Din_29",Din_29);
		result.put("Din_30",Din_30);
		result.put("Din_31",Din_31);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		if (varName.equals("arw_0")) { arw_0 = value; return true; }
		if (varName.equals("arx_0")) { arx_0 = value; return true; }
		if (varName.equals("lpz_0")) { lpz_0 = value; return true; }
		if (varName.equals("ary_0")) { ary_0 = value; return true; }
		if (varName.equals("arz_0")) { arz_0 = value; return true; }
		if (varName.equals("lpx_0")) { lpx_0 = value; return true; }
		if (varName.equals("lpy_0")) { lpy_0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		rotor_7 = (-ary_0); // e1^e3;
		translator_8 = (-((0.5f * lpx_0))); // e1^einf;
		translator_11 = (-((0.5f * lpy_0))); // e2^einf;
		translator_13 = (-((0.5f * lpz_0))); // e3^einf;
		Din_0 = arw_0; // 1.0;
		Din_6 = arz_0; // e1^e2;
		Din_7 = rotor_7; // e1^e3;
		Din_8 = (((arw_0 * translator_8) - (rotor_7 * translator_13)) - (arz_0 * translator_11)); // e1^einf;
		Din_10 = arx_0; // e2^e3;
		Din_11 = (((arz_0 * translator_8) - (arx_0 * translator_13)) + (arw_0 * translator_11)); // e2^einf;
		Din_13 = (((rotor_7 * translator_8) + (arw_0 * translator_13)) + (arx_0 * translator_11)); // e3^einf;
		Din_26 = (((arx_0 * translator_8) + (arz_0 * translator_13)) - (rotor_7 * translator_11)); // e1^e2^e3^einf;
	}

	private float rotor_7;
	private float translator_13;
	private float translator_11;
	private float translator_8;

}
