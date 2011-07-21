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
		float sat1_4 = (0.5f * ((((sat1z_0 * sat1z_0) + (sat1y_0 * sat1y_0)) + (sat1x_0 * sat1x_0))));
		float sat2_4 = (0.5f * ((((sat2z_0 * sat2z_0) + (sat2y_0 * sat2y_0)) + (sat2x_0 * sat2x_0))));
		float sat3_4 = (0.5f * ((((sat3z_0 * sat3z_0) + (sat3y_0 * sat3y_0)) + (sat3x_0 * sat3x_0))));
		float sph1_4 = (sat1_4 - (0.5f * (d1_0 * d1_0)));
		float sph2_4 = (sat2_4 - (0.5f * (d2_0 * d2_0)));
		float sph3_4 = (sat3_4 - (0.5f * (d3_0 * d3_0)));
		float rcPp_16 = ((((((sat1x_0 * sat2y_0) - (sat1y_0 * sat2x_0))) * sat3z_0) - ((((sat1x_0 * sat2z_0) - (sat1z_0 * sat2x_0))) * sat3y_0)) + ((((sat1y_0 * sat2z_0) - (sat1z_0 * sat2y_0))) * sat3x_0));
		float rcPp_17 = ((((((sat1x_0 * sat2y_0) - (sat1y_0 * sat2x_0))) * sph3_4) + (sat3x_0 * (((sat1y_0 * sph2_4) - (sat2y_0 * sph1_4))))) - (sat3y_0 * (((sat1x_0 * sph2_4) - (sat2x_0 * sph1_4)))));
		float rcPp_18 = ((((-((((sat1x_0 - sat2x_0)) * sat3y_0))) + (((sat1y_0 - sat2y_0)) * sat3x_0)) + (sat1x_0 * sat2y_0)) - (sat1y_0 * sat2x_0));
		float rcPp_19 = ((((((sat1x_0 * sat2z_0) - (sat1z_0 * sat2x_0))) * sph3_4) + (sat3x_0 * (((sat1z_0 * sph2_4) - (sat2z_0 * sph1_4))))) - (sat3z_0 * (((sat1x_0 * sph2_4) - (sat2x_0 * sph1_4)))));
		float rcPp_20 = ((((-((((sat1x_0 - sat2x_0)) * sat3z_0))) + (((sat1z_0 - sat2z_0)) * sat3x_0)) + (sat1x_0 * sat2z_0)) - (sat1z_0 * sat2x_0));
		float rcPp_21 = ((((-((((sat1x_0 - sat2x_0)) * sph3_4))) + (sat1x_0 * sph2_4)) + (sat3x_0 * ((sph1_4 - sph2_4)))) - (sat2x_0 * sph1_4));
		float rcPp_22 = ((((((sat1y_0 * sat2z_0) - (sat1z_0 * sat2y_0))) * sph3_4) + (sat3y_0 * (((sat1z_0 * sph2_4) - (sat2z_0 * sph1_4))))) - (sat3z_0 * (((sat1y_0 * sph2_4) - (sat2y_0 * sph1_4)))));
		float rcPp_23 = ((((-((((sat1y_0 - sat2y_0)) * sat3z_0))) + (((sat1z_0 - sat2z_0)) * sat3y_0)) + (sat1y_0 * sat2z_0)) - (sat1z_0 * sat2y_0));
		float rcPp_24 = ((((-((((sat1y_0 - sat2y_0)) * sph3_4))) + (sat1y_0 * sph2_4)) + (sat3y_0 * ((sph1_4 - sph2_4)))) - (sat2y_0 * sph1_4));
		float rcPp_25 = ((((-((((sat1z_0 - sat2z_0)) * sph3_4))) + (sat1z_0 * sph2_4)) + (sat3z_0 * ((sph1_4 - sph2_4)))) - (sat2z_0 * sph1_4));
		float len_0 = (float) Math.sqrt(Math.abs((((((((rcPp_25 * rcPp_25) + (rcPp_24 * rcPp_24)) + ((2.0f * rcPp_22) * rcPp_23)) + (rcPp_21 * rcPp_21)) + ((2.0f * rcPp_19) * rcPp_20)) + ((2.0f * rcPp_17) * rcPp_18)) - (rcPp_16 * rcPp_16))));
		float rcPpDual_6 = (-rcPp_25);
		float rcPpDual_8 = (-rcPp_22);
		float rcPpDual_10 = (-rcPp_21);
		float rcPpDual_12 = (-rcPp_20);
		float rcPpDual_13 = (-rcPp_17);
		float rc1_1 = (((((rcPp_18 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))) - ((rcPp_16 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((len_0 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((rcPpDual_12 * rcPpDual_6) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_2 = ((((-(((rcPpDual_6 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) + ((rcPpDual_10 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_12 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((len_0 * rcPpDual_12) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_3 = ((((-(((rcPp_23 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) - ((rcPp_16 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((len_0 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_10 * rcPpDual_12) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_4 = (((((-(((rcPpDual_8 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) - ((rcPpDual_12 * rcPp_19) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_13 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPp_16 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((len_0 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_5 = (((-(((rcPp_23 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) - ((rcPp_18 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_12 * rcPpDual_12) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_16 = (((-(((rcPpDual_12 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) + ((rcPpDual_10 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((rcPpDual_6 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_17 = ((((rcPp_19 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))) + ((rcPpDual_6 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_12 * rcPpDual_8) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_18 = 0.0f;
		float rc1_19 = ((((rcPp_16 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))) + ((rcPpDual_13 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_8 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_20 = 0.0f;
		float rc1_21 = 0.0f;
		float rc1_22 = (((-(((rcPp_18 * rcPp_19) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) + ((rcPpDual_10 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((rcPpDual_12 * rcPpDual_13) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc1_23 = 0.0f;
		float rc1_24 = 0.0f;
		float rc1_25 = 0.0f;
		rc1N_1 = ((((((((rc1_21 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))) + ((rc1_1 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_20 * rc1_3) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_20 * rc1_25) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_18 * rc1_24) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_16 * rc1_23) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_18 * rc1_2) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))));
		rc1N_2 = ((((((((rc1_24 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))) + ((rc1_2 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_23 * rc1_3) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_23 * rc1_25) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_18 * rc1_21) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_16 * rc1_20) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_1 * rc1_18) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))));
		rc1N_3 = ((((((((rc1_3 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))) + ((rc1_25 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_23 * rc1_24) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_2 * rc1_23) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_20 * rc1_21) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_1 * rc1_20) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_16 * rc1_18) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))));
		float rc1N_4 = (((((((((((rc1_4 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))) + ((rc1_25 * rc1_3) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_25 * rc1_25) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_24 * rc1_24) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_2 * rc1_24) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_22 * rc1_23) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_21 * rc1_21) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) + ((rc1_1 * rc1_21) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_19 * rc1_20) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_17 * rc1_18) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))));
		float rc1N_5 = (((((rc1_5 * rc1_5) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))) - ((rc1_23 * rc1_23) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_20 * rc1_20) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18))))) - ((rc1_18 * rc1_18) / (((((rc1_5 * rc1_5) + (rc1_23 * rc1_23)) + (rc1_20 * rc1_20)) + (rc1_18 * rc1_18)))));
		float rc2_1 = (((((rcPp_18 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))) - ((rcPp_16 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((len_0 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((rcPpDual_12 * rcPpDual_6) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_2 = ((((-(((rcPpDual_6 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) + ((rcPpDual_10 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_12 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((len_0 * rcPpDual_12) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_3 = ((((-(((rcPp_23 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) - ((rcPp_16 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((len_0 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_10 * rcPpDual_12) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_4 = (((((-(((rcPpDual_8 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) - ((rcPpDual_12 * rcPp_19) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_13 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPp_16 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((len_0 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_5 = (((-(((rcPp_23 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) - ((rcPp_18 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_12 * rcPpDual_12) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_16 = (((-(((rcPpDual_12 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) + ((rcPpDual_10 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((rcPpDual_6 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_17 = ((((rcPp_19 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))) + ((rcPpDual_6 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_12 * rcPpDual_8) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_18 = 0.0f;
		float rc2_19 = ((((rcPp_16 * rcPp_24) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))) + ((rcPpDual_13 * rcPp_23) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) - ((rcPpDual_8 * rcPp_18) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_20 = 0.0f;
		float rc2_21 = 0.0f;
		float rc2_22 = (((-(((rcPp_18 * rcPp_19) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))))) + ((rcPpDual_10 * rcPp_16) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12))))) + ((rcPpDual_12 * rcPpDual_13) / ((((rcPp_23 * rcPp_23) + (rcPp_18 * rcPp_18)) + (rcPpDual_12 * rcPpDual_12)))));
		float rc2_23 = 0.0f;
		float rc2_24 = 0.0f;
		float rc2_25 = 0.0f;
		rc2N_1 = ((((((((rc2_21 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))) + ((rc2_1 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_20 * rc2_3) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_20 * rc2_25) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_18 * rc2_24) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_16 * rc2_23) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_18 * rc2_2) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))));
		rc2N_2 = ((((((((rc2_24 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))) + ((rc2_2 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_23 * rc2_3) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_23 * rc2_25) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_18 * rc2_21) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_16 * rc2_20) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_1 * rc2_18) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))));
		rc2N_3 = ((((((((rc2_3 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))) + ((rc2_25 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_23 * rc2_24) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_2 * rc2_23) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_20 * rc2_21) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_1 * rc2_20) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_16 * rc2_18) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))));
		float rc2N_4 = (((((((((((rc2_4 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))) + ((rc2_25 * rc2_3) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_25 * rc2_25) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_24 * rc2_24) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_2 * rc2_24) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_22 * rc2_23) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_21 * rc2_21) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) + ((rc2_1 * rc2_21) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_19 * rc2_20) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_17 * rc2_18) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))));
		float rc2N_5 = (((((rc2_5 * rc2_5) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))) - ((rc2_23 * rc2_23) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_20 * rc2_20) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18))))) - ((rc2_18 * rc2_18) / (((((rc2_5 * rc2_5) + (rc2_23 * rc2_23)) + (rc2_20 * rc2_20)) + (rc2_18 * rc2_18)))));
		z11_0 = ((1.4142135f * (float) Math.sqrt((((((sat1_4 * rc1N_5) + rc1N_4) - (sat1z_0 * rc1N_3)) - (sat1y_0 * rc1N_2)) - (sat1x_0 * rc1N_1)))) - d1_0);
		z12_0 = ((1.4142135f * (float) Math.sqrt((((((sat1_4 * rc2N_5) + rc2N_4) - (sat1z_0 * rc2N_3)) - (sat1y_0 * rc2N_2)) - (sat1x_0 * rc2N_1)))) - d1_0);
		z21_0 = ((1.4142135f * (float) Math.sqrt((((((sat2_4 * rc1N_5) + rc1N_4) - (sat2z_0 * rc1N_3)) - (sat2y_0 * rc1N_2)) - (sat2x_0 * rc1N_1)))) - d2_0);
		z22_0 = ((1.4142135f * (float) Math.sqrt((((((sat2_4 * rc2N_5) + rc2N_4) - (sat2z_0 * rc2N_3)) - (sat2y_0 * rc2N_2)) - (sat2x_0 * rc2N_1)))) - d2_0);
		z31_0 = ((1.4142135f * (float) Math.sqrt((((((sat3_4 * rc1N_5) + rc1N_4) - (sat3z_0 * rc1N_3)) - (sat3y_0 * rc1N_2)) - (sat3x_0 * rc1N_1)))) - d3_0);
		z32_0 = ((1.4142135f * (float) Math.sqrt((((((sat3_4 * rc2N_5) + rc2N_4) - (sat3z_0 * rc2N_3)) - (sat3y_0 * rc2N_2)) - (sat3x_0 * rc2N_1)))) - d3_0);
	}
}
