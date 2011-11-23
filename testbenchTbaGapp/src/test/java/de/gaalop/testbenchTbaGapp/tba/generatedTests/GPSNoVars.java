package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class GPSNoVars implements GAProgram {
	// input variables

	// output variables
	private float rc2N$3;
	private float z32$0;
	private float z12$0;
	private float z11$0;
	private float rc2N$1;
	private float rc2N$2;
	private float z22$0;
	private float z31$0;
	private float rc1N$2;
	private float rc1N$3;
	private float rc1N$1;
	private float z21$0;

	@Override
	public float getValue(String varName) {
		if (varName.equals("rc2N$3")) return rc2N$3;
		if (varName.equals("z32$0")) return z32$0;
		if (varName.equals("z12$0")) return z12$0;
		if (varName.equals("z11$0")) return z11$0;
		if (varName.equals("rc2N$1")) return rc2N$1;
		if (varName.equals("rc2N$2")) return rc2N$2;
		if (varName.equals("z22$0")) return z22$0;
		if (varName.equals("z31$0")) return z31$0;
		if (varName.equals("rc1N$2")) return rc1N$2;
		if (varName.equals("rc1N$3")) return rc1N$3;
		if (varName.equals("rc1N$1")) return rc1N$1;
		if (varName.equals("z21$0")) return z21$0;
		return 0.0f;
	}

	@Override
	public HashMap<String,Float> getValues() {
		HashMap<String,Float> result = new HashMap<String,Float>();
		result.put("rc2N$3",rc2N$3);
		result.put("z32$0",z32$0);
		result.put("z12$0",z12$0);
		result.put("z11$0",z11$0);
		result.put("rc2N$1",rc2N$1);
		result.put("rc2N$2",rc2N$2);
		result.put("z22$0",z22$0);
		result.put("z31$0",z31$0);
		result.put("rc1N$2",rc1N$2);
		result.put("rc1N$3",rc1N$3);
		result.put("rc1N$1",rc1N$1);
		result.put("z21$0",z21$0);
		return result;
	}
	@Override
	public boolean setValue(String varName, float value) {
		return false;
	}
	
	@Override
	public void calculate() {
		rc1N$1 = 0.016657978f; // e1;
		rc1N$2 = 1.048342f; // e2;
		rc1N$3 = 0.928342f; // e3;
		rc2N$1 = 0.65667534f; // e1;
		rc2N$2 = 0.40832466f; // e2;
		rc2N$3 = 0.28832468f; // e3;
		z11$0 = 5.9604645E-8f; // 1.0;
		z12$0 = -2.9802322E-7f; // 1.0;
	}


}
