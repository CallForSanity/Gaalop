package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class GPSNoVars implements GAProgram {
	// input variables

	// output variables
	private float z22_0;
	private float rc2N_1;
	private float z21_0;
	private float z12_0;
	private float rc2N_3;
	private float z32_0;
	private float z31_0;
	private float z11_0;
	private float rc2N_2;
	private float rc1N_3;
	private float rc1N_2;
	private float rc1N_1;

	@Override
	public float getValue(String varName) {
		if (varName.equals("z22_0")) return z22_0;
		if (varName.equals("rc2N_1")) return rc2N_1;
		if (varName.equals("z21_0")) return z21_0;
		if (varName.equals("z12_0")) return z12_0;
		if (varName.equals("rc2N_3")) return rc2N_3;
		if (varName.equals("z32_0")) return z32_0;
		if (varName.equals("z31_0")) return z31_0;
		if (varName.equals("z11_0")) return z11_0;
		if (varName.equals("rc2N_2")) return rc2N_2;
		if (varName.equals("rc1N_3")) return rc1N_3;
		if (varName.equals("rc1N_2")) return rc1N_2;
		if (varName.equals("rc1N_1")) return rc1N_1;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("z22_0",z22_0);
		result.put("rc2N_1",rc2N_1);
		result.put("z21_0",z21_0);
		result.put("z12_0",z12_0);
		result.put("rc2N_3",rc2N_3);
		result.put("z32_0",z32_0);
		result.put("z31_0",z31_0);
		result.put("z11_0",z11_0);
		result.put("rc2N_2",rc2N_2);
		result.put("rc1N_3",rc1N_3);
		result.put("rc1N_2",rc1N_2);
		result.put("rc1N_1",rc1N_1);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		return false;
	}
	
	@Override
	public void calculate() {
		rc1N_1 = 0.016657978f;
		rc1N_2 = 1.048342f;
		rc1N_3 = 0.928342f;
		rc2N_1 = 0.65667534f;
		rc2N_2 = 0.40832466f;
		rc2N_3 = 0.28832468f;
		z11_0 = 5.9604645E-8f;
		z12_0 = -2.9802322E-7f;
		z21_0 = 0.0f;
		z22_0 = 0.0f;
		z31_0 = 0.0f;
		z32_0 = 0.0f;
	}
}
