package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class GPSNoVars implements GAProgram {
	// input variables

	// output variables
	private double rc2N$3;
	private double z32$0;
	private double z12$0;
	private double rc2N$1;
	private double z11$0;
	private double rc2N$2;
	private double z22$0;
	private double rc1N$2;
	private double z31$0;
	private double rc1N$3;
	private double rc1N$1;
	private double z21$0;

	@Override
	public double getValue(String varName) {
		if (varName.equals("rc2N$3")) return rc2N$3;
		if (varName.equals("z32$0")) return z32$0;
		if (varName.equals("z12$0")) return z12$0;
		if (varName.equals("rc2N$1")) return rc2N$1;
		if (varName.equals("z11$0")) return z11$0;
		if (varName.equals("rc2N$2")) return rc2N$2;
		if (varName.equals("z22$0")) return z22$0;
		if (varName.equals("rc1N$2")) return rc1N$2;
		if (varName.equals("z31$0")) return z31$0;
		if (varName.equals("rc1N$3")) return rc1N$3;
		if (varName.equals("rc1N$1")) return rc1N$1;
		if (varName.equals("z21$0")) return z21$0;
		return 0.0d;
	}

	@Override
	public HashMap<String,Double> getValues() {
		HashMap<String,Double> result = new HashMap<String,Double>();
		result.put("rc2N$3",rc2N$3);
		result.put("z32$0",z32$0);
		result.put("z12$0",z12$0);
		result.put("rc2N$1",rc2N$1);
		result.put("z11$0",z11$0);
		result.put("rc2N$2",rc2N$2);
		result.put("z22$0",z22$0);
		result.put("rc1N$2",rc1N$2);
		result.put("z31$0",z31$0);
		result.put("rc1N$3",rc1N$3);
		result.put("rc1N$1",rc1N$1);
		result.put("z21$0",z21$0);
		return result;
	}
	@Override
	public boolean setValue(String varName, double value) {
		return false;
	}
	
	@Override
	public void calculate() {
		rc1N$1 = 0.01665797656578416d; // e1;
		rc1N$2 = 1.0483420007844506d; // e2;
		rc1N$3 = 0.9283420091291008d; // e3;
		rc2N$1 = 0.6566753321309624d; // e1;
		rc2N$2 = 0.4083246452192724d; // e2;
		rc2N$3 = 0.2883246535639226d; // e3;
	}


}
