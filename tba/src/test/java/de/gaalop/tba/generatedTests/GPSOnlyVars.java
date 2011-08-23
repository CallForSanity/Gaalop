package de.gaalop.tba.generatedTests;

import java.util.HashMap;

public class GPSOnlyVars implements GAProgram {
	// input variables
	private float d1_0;
	private float d2_0;
	private float d3_0;
	private float sat3x_0;
	private float sat3y_0;
	private float sat3z_0;
	private float sat2y_0;
	private float sat1z_0;
	private float sat2x_0;
	private float sat1y_0;
	private float sat1x_0;
	private float sat2z_0;

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
		if (varName.equals("d1_0")) { d1_0 = value; return true; }
		if (varName.equals("d2_0")) { d2_0 = value; return true; }
		if (varName.equals("d3_0")) { d3_0 = value; return true; }
		if (varName.equals("sat3x_0")) { sat3x_0 = value; return true; }
		if (varName.equals("sat3y_0")) { sat3y_0 = value; return true; }
		if (varName.equals("sat3z_0")) { sat3z_0 = value; return true; }
		if (varName.equals("sat2y_0")) { sat2y_0 = value; return true; }
		if (varName.equals("sat1z_0")) { sat1z_0 = value; return true; }
		if (varName.equals("sat2x_0")) { sat2x_0 = value; return true; }
		if (varName.equals("sat1y_0")) { sat1y_0 = value; return true; }
		if (varName.equals("sat1x_0")) { sat1x_0 = value; return true; }
		if (varName.equals("sat2z_0")) { sat2z_0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		sat1_1 = sat1x_0; // e1;
		sat1_2 = sat1y_0; // e2;
		sat1_3 = sat1z_0; // e3;
		sat1_4 = ((((sat1x_0 * sat1x_0) + ((sat1y_0 * sat1y_0) + (sat1z_0 * sat1z_0)))) / 2.0f); // einf;
		sat2_1 = sat2x_0; // e1;
		sat2_2 = sat2y_0; // e2;
		sat2_3 = sat2z_0; // e3;
		sat2_4 = ((((sat2x_0 * sat2x_0) + ((sat2y_0 * sat2y_0) + (sat2z_0 * sat2z_0)))) / 2.0f); // einf;
		sat3_1 = sat3x_0; // e1;
		sat3_2 = sat3y_0; // e2;
		sat3_3 = sat3z_0; // e3;
		sat3_4 = ((((sat3x_0 * sat3x_0) + ((sat3y_0 * sat3y_0) + (sat3z_0 * sat3z_0)))) / 2.0f); // einf;
		sph1_1 = sat1_1; // e1;
		sph1_2 = sat1_2; // e2;
		sph1_3 = sat1_3; // e3;
		sph1_4 = (sat1_4 - ((d1_0 / 2.0f) * d1_0)); // einf;
		sph2_1 = sat2_1; // e1;
		sph2_2 = sat2_2; // e2;
		sph2_3 = sat2_3; // e3;
		sph2_4 = (sat2_4 - ((d2_0 / 2.0f) * d2_0)); // einf;
		sph3_1 = sat3_1; // e1;
		sph3_2 = sat3_2; // e2;
		sph3_3 = sat3_3; // e3;
		sph3_4 = (sat3_4 - ((d3_0 / 2.0f) * d3_0)); // einf;
		rcPp_16 = ((((((sph1_1 * sph2_2) + (-((sph1_2 * sph2_1))))) * sph3_3) + (-(((((sph1_1 * sph2_3) + (-((sph1_3 * sph2_1))))) * sph3_2)))) + ((((sph1_2 * sph2_3) + (-((sph1_3 * sph2_2))))) * sph3_1)); // e1^e2^e3;
		rcPp_17 = ((((((sph1_1 * sph2_2) + (-((sph1_2 * sph2_1))))) * sph3_4) + (-(((((sph1_1 * sph2_4) + (-((sph1_4 * sph2_1))))) * sph3_2)))) + ((((sph1_2 * sph2_4) + (-((sph1_4 * sph2_2))))) * sph3_1)); // e1^e2^einf;
		rcPp_18 = ((((sph1_1 * sph2_2) + (-((sph1_2 * sph2_1)))) + (-((((sph1_1 + (-sph2_1))) * sph3_2)))) + (((sph1_2 + (-sph2_2))) * sph3_1)); // e1^e2^e0;
		rcPp_19 = ((((((sph1_1 * sph2_3) + (-((sph1_3 * sph2_1))))) * sph3_4) + (-(((((sph1_1 * sph2_4) + (-((sph1_4 * sph2_1))))) * sph3_3)))) + ((((sph1_3 * sph2_4) + (-((sph1_4 * sph2_3))))) * sph3_1)); // e1^e3^einf;
		rcPp_20 = ((((sph1_1 * sph2_3) + (-((sph1_3 * sph2_1)))) + (-((((sph1_1 + (-sph2_1))) * sph3_3)))) + (((sph1_3 + (-sph2_3))) * sph3_1)); // e1^e3^e0;
		rcPp_21 = ((((sph1_1 * sph2_4) + (-((sph1_4 * sph2_1)))) + (-((((sph1_1 + (-sph2_1))) * sph3_4)))) + (((sph1_4 + (-sph2_4))) * sph3_1)); // e1^einf^e0;
		rcPp_22 = ((((((sph1_2 * sph2_3) + (-((sph1_3 * sph2_2))))) * sph3_4) + (-(((((sph1_2 * sph2_4) + (-((sph1_4 * sph2_2))))) * sph3_3)))) + ((((sph1_3 * sph2_4) + (-((sph1_4 * sph2_3))))) * sph3_2)); // e2^e3^einf;
		rcPp_23 = ((((sph1_2 * sph2_3) + (-((sph1_3 * sph2_2)))) + (-((((sph1_2 + (-sph2_2))) * sph3_3)))) + (((sph1_3 + (-sph2_3))) * sph3_2)); // e2^e3^e0;
		rcPp_24 = ((((sph1_2 * sph2_4) + (-((sph1_4 * sph2_2)))) + (-((((sph1_2 + (-sph2_2))) * sph3_4)))) + (((sph1_4 + (-sph2_4))) * sph3_2)); // e2^einf^e0;
		rcPp_25 = ((((sph1_3 * sph2_4) + (-((sph1_4 * sph2_3)))) + (-((((sph1_3 + (-sph2_3))) * sph3_4)))) + (((sph1_4 + (-sph2_4))) * sph3_3)); // e3^einf^e0;
		len_0 = (float) Math.sqrt((float) Math.sqrt((float) Math.abs(((((((((((((-((rcPp_16 * rcPp_16))) + (rcPp_17 * rcPp_18)) + (rcPp_18 * rcPp_17)) + (rcPp_19 * rcPp_20)) + (rcPp_20 * rcPp_19)) + (rcPp_21 * rcPp_21)) + (rcPp_22 * rcPp_23)) + (rcPp_23 * rcPp_22)) + (rcPp_24 * rcPp_24)) + (rcPp_25 * rcPp_25))) * (((((((((((-((rcPp_16 * rcPp_16))) + (rcPp_17 * rcPp_18)) + (rcPp_18 * rcPp_17)) + (rcPp_19 * rcPp_20)) + (rcPp_20 * rcPp_19)) + (rcPp_21 * rcPp_21)) + (rcPp_22 * rcPp_23)) + (rcPp_23 * rcPp_22)) + (rcPp_24 * rcPp_24)) + (rcPp_25 * rcPp_25))))))); // 1.0;
		rcPpDual_6 = (-rcPp_25); // e1^e2;
		rcPpDual_7 = rcPp_24; // e1^e3;
		rcPpDual_8 = (-rcPp_22); // e1^einf;
		rcPpDual_9 = rcPp_23; // e1^e0;
		rcPpDual_10 = (-rcPp_21); // e2^e3;
		rcPpDual_11 = rcPp_19; // e2^einf;
		rcPpDual_12 = (-rcPp_20); // e2^e0;
		rcPpDual_13 = (-rcPp_17); // e3^einf;
		rcPpDual_14 = rcPp_18; // e3^e0;
		rcPpDual_15 = rcPp_16; // einf^e0;
		nen_1 = rcPpDual_9; // e1;
		nen_2 = rcPpDual_12; // e2;
		nen_3 = rcPpDual_14; // e3;
		nen_4 = rcPpDual_15; // einf;
		rc1_1 = ((((len_0 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (rcPpDual_6 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))) + (rcPpDual_7 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))) + (-((rcPpDual_9 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e1;
		rc1_2 = ((((len_0 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_6 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_10 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))) + (-((rcPpDual_12 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e2;
		rc1_3 = ((((len_0 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_7 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_10 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_14 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e3;
		rc1_4 = (((((len_0 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_8 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_11 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_13 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_15 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // einf;
		rc1_5 = (((-((rcPpDual_9 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (-((rcPpDual_12 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_14 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e0;
		rc1_16 = (((rcPpDual_6 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_7 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_10 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e2^e3;
		rc1_17 = (((rcPpDual_6 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_8 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_11 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e2^einf;
		rc1_18 = ((-((rcPpDual_9 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_12 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e2^e0;
		rc1_19 = (((rcPpDual_7 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_8 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_13 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e3^einf;
		rc1_20 = ((-((rcPpDual_9 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_14 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e3^e0;
		rc1_21 = ((-((rcPpDual_9 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_15 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^einf^e0;
		rc1_22 = (((rcPpDual_10 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_11 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_13 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e2^e3^einf;
		rc1_23 = ((-((rcPpDual_12 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_14 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e2^e3^e0;
		rc1_24 = ((-((rcPpDual_12 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_15 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e2^einf^e0;
		rc1_25 = ((-((rcPpDual_14 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_15 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e3^einf^e0;
		rc1N_1 = ((((((((-rc1_1) * ((-rc1_5) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23))))))) + (-(((-rc1_2) * ((-rc1_18) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_3) * ((-rc1_20) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + ((-rc1_5) * ((-rc1_21) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + (-(((-rc1_16) * ((-rc1_23) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + ((-rc1_18) * ((-rc1_24) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + ((-rc1_20) * ((-rc1_25) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))); // e1;
		rc1N_2 = ((((((((-rc1_1) * ((-rc1_18) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23))))))) + ((-rc1_2) * ((-rc1_5) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + (-(((-rc1_3) * ((-rc1_23) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + ((-rc1_5) * ((-rc1_24) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + ((-rc1_16) * ((-rc1_20) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + (-(((-rc1_18) * ((-rc1_21) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + ((-rc1_23) * ((-rc1_25) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))); // e2;
		rc1N_3 = ((((((((-rc1_1) * ((-rc1_20) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23))))))) + ((-rc1_2) * ((-rc1_23) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + ((-rc1_3) * ((-rc1_5) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + ((-rc1_5) * ((-rc1_25) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + (-(((-rc1_16) * ((-rc1_18) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_20) * ((-rc1_21) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_23) * ((-rc1_24) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))); // e3;
		rc1N_4 = (((((((((((-rc1_1) * ((-rc1_21) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23))))))) + ((-rc1_2) * ((-rc1_24) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + ((-rc1_3) * ((-rc1_25) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + ((-rc1_4) * ((-rc1_5) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))) + (-(((-rc1_17) * ((-rc1_18) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_19) * ((-rc1_20) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_21) * ((-rc1_21) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_22) * ((-rc1_23) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_24) * ((-rc1_24) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_25) * ((-rc1_25) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))); // einf;
		rc1N_5 = (((((-rc1_5) * ((-rc1_5) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23))))))) + (-(((-rc1_18) * ((-rc1_18) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_20) * ((-rc1_20) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))) + (-(((-rc1_23) * ((-rc1_23) / ((((((-rc1_5) * (-rc1_5)) + (-(((-rc1_18) * rc1_18)))) + (-(((-rc1_20) * rc1_20)))) + (-(((-rc1_23) * rc1_23)))))))))); // e0;
		rc2_1 = (((((-len_0) * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (rcPpDual_6 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))) + (rcPpDual_7 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))) + (-((rcPpDual_9 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e1;
		rc2_2 = (((((-len_0) * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_6 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_10 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))) + (-((rcPpDual_12 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e2;
		rc2_3 = (((((-len_0) * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_7 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_10 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_14 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e3;
		rc2_4 = ((((((-len_0) * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_8 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_11 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_13 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_15 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // einf;
		rc2_5 = (((-((rcPpDual_9 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (-((rcPpDual_12 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (-((rcPpDual_14 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))); // e0;
		rc2_16 = (((rcPpDual_6 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_7 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_10 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e2^e3;
		rc2_17 = (((rcPpDual_6 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_8 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_11 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e2^einf;
		rc2_18 = ((-((rcPpDual_9 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_12 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e2^e0;
		rc2_19 = (((rcPpDual_7 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_8 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_13 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e3^einf;
		rc2_20 = ((-((rcPpDual_9 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_14 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^e3^e0;
		rc2_21 = ((-((rcPpDual_9 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_15 * (nen_1 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e1^einf^e0;
		rc2_22 = (((rcPpDual_10 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))) + (-((rcPpDual_11 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))))) + (rcPpDual_13 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e2^e3^einf;
		rc2_23 = ((-((rcPpDual_12 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_14 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e2^e3^e0;
		rc2_24 = ((-((rcPpDual_12 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_15 * (nen_2 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e2^einf^e0;
		rc2_25 = ((-((rcPpDual_14 * (nen_4 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3))))))) + (rcPpDual_15 * (nen_3 / ((((nen_1 * nen_1) + (nen_2 * nen_2)) + (nen_3 * nen_3)))))); // e3^einf^e0;
		rc2N_1 = ((((((((-rc2_1) * ((-rc2_5) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23))))))) + (-(((-rc2_2) * ((-rc2_18) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_3) * ((-rc2_20) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + ((-rc2_5) * ((-rc2_21) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + (-(((-rc2_16) * ((-rc2_23) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + ((-rc2_18) * ((-rc2_24) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + ((-rc2_20) * ((-rc2_25) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))); // e1;
		rc2N_2 = ((((((((-rc2_1) * ((-rc2_18) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23))))))) + ((-rc2_2) * ((-rc2_5) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + (-(((-rc2_3) * ((-rc2_23) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + ((-rc2_5) * ((-rc2_24) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + ((-rc2_16) * ((-rc2_20) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + (-(((-rc2_18) * ((-rc2_21) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + ((-rc2_23) * ((-rc2_25) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))); // e2;
		rc2N_3 = ((((((((-rc2_1) * ((-rc2_20) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23))))))) + ((-rc2_2) * ((-rc2_23) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + ((-rc2_3) * ((-rc2_5) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + ((-rc2_5) * ((-rc2_25) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + (-(((-rc2_16) * ((-rc2_18) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_20) * ((-rc2_21) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_23) * ((-rc2_24) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))); // e3;
		rc2N_4 = (((((((((((-rc2_1) * ((-rc2_21) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23))))))) + ((-rc2_2) * ((-rc2_24) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + ((-rc2_3) * ((-rc2_25) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + ((-rc2_4) * ((-rc2_5) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))) + (-(((-rc2_17) * ((-rc2_18) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_19) * ((-rc2_20) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_21) * ((-rc2_21) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_22) * ((-rc2_23) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_24) * ((-rc2_24) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_25) * ((-rc2_25) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))); // einf;
		rc2N_5 = (((((-rc2_5) * ((-rc2_5) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23))))))) + (-(((-rc2_18) * ((-rc2_18) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_20) * ((-rc2_20) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))) + (-(((-rc2_23) * ((-rc2_23) / ((((((-rc2_5) * (-rc2_5)) + (-(((-rc2_18) * rc2_18)))) + (-(((-rc2_20) * rc2_20)))) + (-(((-rc2_23) * rc2_23)))))))))); // e0;
		z11_0 = ((float) Math.sqrt((-2.0f * ((((((rc1N_1 * sat1_1) + (rc1N_2 * sat1_2)) + (rc1N_3 * sat1_3)) + (-rc1N_4)) + (-((rc1N_5 * sat1_4))))))) - d1_0); // 1.0;
		z12_0 = ((float) Math.sqrt((-2.0f * ((((((rc2N_1 * sat1_1) + (rc2N_2 * sat1_2)) + (rc2N_3 * sat1_3)) + (-rc2N_4)) + (-((rc2N_5 * sat1_4))))))) - d1_0); // 1.0;
		z21_0 = ((float) Math.sqrt((-2.0f * ((((((rc1N_1 * sat2_1) + (rc1N_2 * sat2_2)) + (rc1N_3 * sat2_3)) + (-rc1N_4)) + (-((rc1N_5 * sat2_4))))))) - d2_0); // 1.0;
		z22_0 = ((float) Math.sqrt((-2.0f * ((((((rc2N_1 * sat2_1) + (rc2N_2 * sat2_2)) + (rc2N_3 * sat2_3)) + (-rc2N_4)) + (-((rc2N_5 * sat2_4))))))) - d2_0); // 1.0;
		z31_0 = ((float) Math.sqrt((-2.0f * ((((((rc1N_1 * sat3_1) + (rc1N_2 * sat3_2)) + (rc1N_3 * sat3_3)) + (-rc1N_4)) + (-((rc1N_5 * sat3_4))))))) - d3_0); // 1.0;
		z32_0 = ((float) Math.sqrt((-2.0f * ((((((rc2N_1 * sat3_1) + (rc2N_2 * sat3_2)) + (rc2N_3 * sat3_3)) + (-rc2N_4)) + (-((rc2N_5 * sat3_4))))))) - d3_0); // 1.0;
	}

	private float rc1_1;
	private float rc1_2;
	private float rc1_5;
	private float sat3_2;
	private float sat3_3;
	private float rc1_3;
	private float sat3_4;
	private float rc1_4;
	private float rc2_4;
	private float sph2_3;
	private float sat2_3;
	private float rc2_5;
	private float sph2_2;
	private float sat2_4;
	private float rc2_2;
	private float sph2_1;
	private float rc2_3;
	private float rc2_1;
	private float rc2_24;
	private float rc2_25;
	private float sph2_4;
	private float rcPpDual_7;
	private float rcPpDual_6;
	private float sph3_4;
	private float rcPpDual_9;
	private float sph3_3;
	private float rcPpDual_8;
	private float sph3_2;
	private float sph3_1;
	private float rcPp_19;
	private float rcPp_18;
	private float rcPp_17;
	private float rcPp_16;
	private float rcPp_20;
	private float nen_2;
	private float rc1_25;
	private float rcPp_21;
	private float len_0;
	private float nen_3;
	private float rc1_24;
	private float rcPp_22;
	private float rc1_23;
	private float rcPp_23;
	private float nen_1;
	private float nen_4;
	private float rc2_17;
	private float rc2_18;
	private float rc2_19;
	private float sat1_4;
	private float rc2_16;
	private float rc2_21;
	private float rc2_20;
	private float rc2_23;
	private float rc2_22;
	private float sph1_1;
	private float rcPpDual_14;
	private float sph1_2;
	private float rcPpDual_13;
	private float sph1_3;
	private float sph1_4;
	private float rcPpDual_15;
	private float rcPp_25;
	private float rcPpDual_10;
	private float rcPp_24;
	private float rcPpDual_12;
	private float rcPpDual_11;
	private float rc1_17;
	private float rc1_16;
	private float rc1_19;
	private float rc1_18;
	private float rc2N_5;
	private float rc2N_4;
	private float rc1_20;
	private float rc1_21;
	private float rc1N_5;
	private float rc1_22;
	private float rc1N_4;
	private float sat1_3;
	private float sat1_2;
	private float sat1_1;
	private float sat2_2;
	private float sat2_1;
	private float sat3_1;

}
