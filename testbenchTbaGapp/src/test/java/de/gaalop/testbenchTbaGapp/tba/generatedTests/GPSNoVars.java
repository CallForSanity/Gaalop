package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class GPSNoVars implements GAProgram {
		
	// input variables

	// local variables

	// output variables
	private double rc1N$1;
	private double rc1N$2;
	private double rc1N$3;
	private double rc2N$1;
	private double rc2N$2;
	private double rc2N$3;

	@Override
	public double getValue(String varName) {
		if (varName.equals("rc2N$3")) return rc2N$3;
		if (varName.equals("rc2N$1")) return rc2N$1;
		if (varName.equals("rc2N$2")) return rc2N$2;
		if (varName.equals("rc1N$2")) return rc1N$2;
		if (varName.equals("rc1N$3")) return rc1N$3;
		if (varName.equals("rc1N$1")) return rc1N$1;
		return 0.0d;
	}

	@Override
	public HashMap<String,Double> getValues() {
		HashMap<String,Double> result = new HashMap<String,Double>();
		result.put("rc2N$3",rc2N$3);
		result.put("rc2N$1",rc2N$1);
		result.put("rc2N$2",rc2N$2);
		result.put("rc1N$2",rc1N$2);
		result.put("rc1N$3",rc1N$3);
		result.put("rc1N$1",rc1N$1);
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
