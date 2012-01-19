package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class GPSOnlyVars implements GAProgram {
	// input variables
	private double sat3x$0;
	private double sat3y$0;
	private double sat3z$0;
	private double d1$0;
	private double d2$0;
	private double d3$0;
	private double sat1z$0;
	private double sat2y$0;
	private double sat1y$0;
	private double sat2x$0;
	private double sat1x$0;
	private double sat2z$0;

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
		if (varName.equals("sat3x$0")) { sat3x$0 = value; return true; }
		if (varName.equals("sat3y$0")) { sat3y$0 = value; return true; }
		if (varName.equals("sat3z$0")) { sat3z$0 = value; return true; }
		if (varName.equals("d1$0")) { d1$0 = value; return true; }
		if (varName.equals("d2$0")) { d2$0 = value; return true; }
		if (varName.equals("d3$0")) { d3$0 = value; return true; }
		if (varName.equals("sat1z$0")) { sat1z$0 = value; return true; }
		if (varName.equals("sat2y$0")) { sat2y$0 = value; return true; }
		if (varName.equals("sat1y$0")) { sat1y$0 = value; return true; }
		if (varName.equals("sat2x$0")) { sat2x$0 = value; return true; }
		if (varName.equals("sat1x$0")) { sat1x$0 = value; return true; }
		if (varName.equals("sat2z$0")) { sat2z$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		sat1$4 = (((((sat1x$0 * sat1x$0) + (sat1y$0 * sat1y$0)) + (sat1z$0 * sat1z$0))) / 2.0d); // einf;
		sat2$4 = (((((sat2x$0 * sat2x$0) + (sat2y$0 * sat2y$0)) + (sat2z$0 * sat2z$0))) / 2.0d); // einf;
		sat3$4 = (((((sat3x$0 * sat3x$0) + (sat3y$0 * sat3y$0)) + (sat3z$0 * sat3z$0))) / 2.0d); // einf;
		sph1$4 = (sat1$4 - ((d1$0 / 2.0d) * d1$0)); // einf;
		sph2$4 = (sat2$4 - ((d2$0 / 2.0d) * d2$0)); // einf;
		sph3$4 = (sat3$4 - ((d3$0 / 2.0d) * d3$0)); // einf;
		rcPp$16 = ((((((sat1x$0 * sat2y$0) + (-((sat1y$0 * sat2x$0))))) * sat3z$0) + (-(((((sat1x$0 * sat2z$0) + (-((sat1z$0 * sat2x$0))))) * sat3y$0)))) + ((((sat1y$0 * sat2z$0) + (-((sat1z$0 * sat2y$0))))) * sat3x$0)); // e1 ^ (e2 ^ e3);
		rcPp$17 = ((((((sat1x$0 * sat2y$0) + (-((sat1y$0 * sat2x$0))))) * sph3$4) + (-(((((sat1x$0 * sph2$4) + (-((sph1$4 * sat2x$0))))) * sat3y$0)))) + ((((sat1y$0 * sph2$4) + (-((sph1$4 * sat2y$0))))) * sat3x$0)); // e1 ^ (e2 ^ einf);
		rcPp$18 = ((((sat1x$0 * sat2y$0) + (-((sat1y$0 * sat2x$0)))) + (-((((sat1x$0 + (-sat2x$0))) * sat3y$0)))) + (((sat1y$0 + (-sat2y$0))) * sat3x$0)); // e1 ^ (e2 ^ e0);
		rcPp$19 = ((((((sat1x$0 * sat2z$0) + (-((sat1z$0 * sat2x$0))))) * sph3$4) + (-(((((sat1x$0 * sph2$4) + (-((sph1$4 * sat2x$0))))) * sat3z$0)))) + ((((sat1z$0 * sph2$4) + (-((sph1$4 * sat2z$0))))) * sat3x$0)); // e1 ^ (e3 ^ einf);
		rcPp$20 = ((((sat1x$0 * sat2z$0) + (-((sat1z$0 * sat2x$0)))) + (-((((sat1x$0 + (-sat2x$0))) * sat3z$0)))) + (((sat1z$0 + (-sat2z$0))) * sat3x$0)); // e1 ^ (e3 ^ e0);
		rcPp$21 = ((((sat1x$0 * sph2$4) + (-((sph1$4 * sat2x$0)))) + (-((((sat1x$0 + (-sat2x$0))) * sph3$4)))) + (((sph1$4 + (-sph2$4))) * sat3x$0)); // e1 ^ (einf ^ e0);
		rcPp$22 = ((((((sat1y$0 * sat2z$0) + (-((sat1z$0 * sat2y$0))))) * sph3$4) + (-(((((sat1y$0 * sph2$4) + (-((sph1$4 * sat2y$0))))) * sat3z$0)))) + ((((sat1z$0 * sph2$4) + (-((sph1$4 * sat2z$0))))) * sat3y$0)); // e2 ^ (e3 ^ einf);
		rcPp$23 = ((((sat1y$0 * sat2z$0) + (-((sat1z$0 * sat2y$0)))) + (-((((sat1y$0 + (-sat2y$0))) * sat3z$0)))) + (((sat1z$0 + (-sat2z$0))) * sat3y$0)); // e2 ^ (e3 ^ e0);
		rcPp$24 = ((((sat1y$0 * sph2$4) + (-((sph1$4 * sat2y$0)))) + (-((((sat1y$0 + (-sat2y$0))) * sph3$4)))) + (((sph1$4 + (-sph2$4))) * sat3y$0)); // e2 ^ (einf ^ e0);
		rcPp$25 = ((((sat1z$0 * sph2$4) + (-((sph1$4 * sat2z$0)))) + (-((((sat1z$0 + (-sat2z$0))) * sph3$4)))) + (((sph1$4 + (-sph2$4))) * sat3z$0)); // e3 ^ (einf ^ e0);
		len$0 = (double) Math.sqrt((double) Math.sqrt((double) Math.abs(((((((((((((-((rcPp$16 * rcPp$16))) + (rcPp$17 * rcPp$18)) + (rcPp$18 * rcPp$17)) + (rcPp$19 * rcPp$20)) + (rcPp$20 * rcPp$19)) + (rcPp$21 * rcPp$21)) + (rcPp$22 * rcPp$23)) + (rcPp$23 * rcPp$22)) + (rcPp$24 * rcPp$24)) + (rcPp$25 * rcPp$25))) * (((((((((((-((rcPp$16 * rcPp$16))) + (rcPp$17 * rcPp$18)) + (rcPp$18 * rcPp$17)) + (rcPp$19 * rcPp$20)) + (rcPp$20 * rcPp$19)) + (rcPp$21 * rcPp$21)) + (rcPp$22 * rcPp$23)) + (rcPp$23 * rcPp$22)) + (rcPp$24 * rcPp$24)) + (rcPp$25 * rcPp$25))))))); // 1.0;
		rcPpDual$6 = (-rcPp$25); // e1 ^ e2;
		rcPpDual$8 = (-rcPp$22); // e1 ^ einf;
		rcPpDual$10 = (-rcPp$21); // e2 ^ e3;
		rcPpDual$12 = (-rcPp$20); // e2 ^ e0;
		rcPpDual$13 = (-rcPp$17); // e3 ^ einf;
		rc1$1 = ((((len$0 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (rcPpDual$6 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))) + (rcPp$24 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))) + (-((rcPp$23 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e1;
		rc1$2 = ((((len$0 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$6 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$10 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))) + (-((rcPpDual$12 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e2;
		rc1$3 = ((((len$0 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPp$24 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPpDual$10 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$18 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e3;
		rc1$4 = (((((len$0 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$8 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$19 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPpDual$13 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$16 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // einf;
		rc1$5 = (((-((rcPp$23 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (-((rcPpDual$12 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$18 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e0;
		rc1$16 = (((rcPpDual$6 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPp$24 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$10 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e2 ^ e3);
		rc1$17 = (((rcPpDual$6 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$8 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPp$19 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e2 ^ einf);
		rc1$18 = ((-((rcPp$23 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPpDual$12 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e2 ^ e0);
		rc1$19 = (((rcPp$24 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$8 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$13 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e3 ^ einf);
		rc1$20 = ((-((rcPp$23 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$18 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e3 ^ e0);
		rc1$21 = ((-((rcPp$23 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$16 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (einf ^ e0);
		rc1$22 = (((rcPpDual$10 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPp$19 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$13 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e2 ^ (e3 ^ einf);
		rc1$23 = ((-((rcPpDual$12 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$18 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e2 ^ (e3 ^ e0);
		rc1$24 = ((-((rcPpDual$12 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$16 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e2 ^ (einf ^ e0);
		rc1$25 = ((-((rcPp$18 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$16 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e3 ^ (einf ^ e0);
		rc1N$1 = ((((((((-rc1$1) * ((-rc1$5) / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23))))))) + (-(((-rc1$2) * (rc1$18 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$3) * (rc1$20 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + ((-rc1$5) * (rc1$21 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + (-(((-rc1$16) * (rc1$23 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + ((-rc1$18) * (rc1$24 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + ((-rc1$20) * (rc1$25 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))); // e1;
		rc1N$2 = ((((((((-rc1$1) * (rc1$18 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23))))))) + ((-rc1$2) * ((-rc1$5) / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + (-(((-rc1$3) * (rc1$23 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + ((-rc1$5) * (rc1$24 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + ((-rc1$16) * (rc1$20 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + (-(((-rc1$18) * (rc1$21 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + ((-rc1$23) * (rc1$25 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))); // e2;
		rc1N$3 = ((((((((-rc1$1) * (rc1$20 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23))))))) + ((-rc1$2) * (rc1$23 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + ((-rc1$3) * ((-rc1$5) / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + ((-rc1$5) * (rc1$25 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + (-(((-rc1$16) * (rc1$18 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$20) * (rc1$21 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$23) * (rc1$24 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))); // e3;
		rc1N$4 = (((((((((((-rc1$1) * (rc1$21 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23))))))) + ((-rc1$2) * (rc1$24 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + ((-rc1$3) * (rc1$25 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + ((-rc1$4) * ((-rc1$5) / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))) + (-(((-rc1$17) * (rc1$18 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$19) * (rc1$20 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$21) * (rc1$21 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$22) * (rc1$23 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$24) * (rc1$24 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$25) * (rc1$25 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))); // einf;
		rc1N$5 = (((((-rc1$5) * ((-rc1$5) / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23))))))) + (-(((-rc1$18) * (rc1$18 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$20) * (rc1$20 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))) + (-(((-rc1$23) * (rc1$23 / ((((((-rc1$5) * (-rc1$5)) + (-(((-rc1$18) * rc1$18)))) + (-(((-rc1$20) * rc1$20)))) + (-(((-rc1$23) * rc1$23)))))))))); // e0;
		rc2$1 = (((((-len$0) * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (rcPpDual$6 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))) + (rcPp$24 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))) + (-((rcPp$23 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e1;
		rc2$2 = (((((-len$0) * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$6 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$10 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))) + (-((rcPpDual$12 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e2;
		rc2$3 = (((((-len$0) * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPp$24 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPpDual$10 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$18 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e3;
		rc2$4 = ((((((-len$0) * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$8 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$19 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPpDual$13 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$16 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // einf;
		rc2$5 = (((-((rcPp$23 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (-((rcPpDual$12 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (-((rcPp$18 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))); // e0;
		rc2$16 = (((rcPpDual$6 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPp$24 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$10 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e2 ^ e3);
		rc2$17 = (((rcPpDual$6 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$8 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPp$19 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e2 ^ einf);
		rc2$18 = ((-((rcPp$23 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPpDual$12 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e2 ^ e0);
		rc2$19 = (((rcPp$24 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPpDual$8 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$13 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e3 ^ einf);
		rc2$20 = ((-((rcPp$23 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$18 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (e3 ^ e0);
		rc2$21 = ((-((rcPp$23 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$16 * (rcPp$23 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e1 ^ (einf ^ e0);
		rc2$22 = (((rcPpDual$10 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))) + (-((rcPp$19 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))))) + (rcPpDual$13 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e2 ^ (e3 ^ einf);
		rc2$23 = ((-((rcPpDual$12 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$18 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e2 ^ (e3 ^ e0);
		rc2$24 = ((-((rcPpDual$12 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$16 * (rcPpDual$12 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e2 ^ (einf ^ e0);
		rc2$25 = ((-((rcPp$18 * (rcPp$16 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18))))))) + (rcPp$16 * (rcPp$18 / ((((rcPp$23 * rcPp$23) + (rcPpDual$12 * rcPpDual$12)) + (rcPp$18 * rcPp$18)))))); // e3 ^ (einf ^ e0);
		rc2N$1 = ((((((((-rc2$1) * ((-rc2$5) / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23))))))) + (-(((-rc2$2) * (rc2$18 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$3) * (rc2$20 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + ((-rc2$5) * (rc2$21 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + (-(((-rc2$16) * (rc2$23 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + ((-rc2$18) * (rc2$24 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + ((-rc2$20) * (rc2$25 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))); // e1;
		rc2N$2 = ((((((((-rc2$1) * (rc2$18 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23))))))) + ((-rc2$2) * ((-rc2$5) / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + (-(((-rc2$3) * (rc2$23 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + ((-rc2$5) * (rc2$24 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + ((-rc2$16) * (rc2$20 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + (-(((-rc2$18) * (rc2$21 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + ((-rc2$23) * (rc2$25 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))); // e2;
		rc2N$3 = ((((((((-rc2$1) * (rc2$20 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23))))))) + ((-rc2$2) * (rc2$23 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + ((-rc2$3) * ((-rc2$5) / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + ((-rc2$5) * (rc2$25 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + (-(((-rc2$16) * (rc2$18 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$20) * (rc2$21 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$23) * (rc2$24 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))); // e3;
		rc2N$4 = (((((((((((-rc2$1) * (rc2$21 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23))))))) + ((-rc2$2) * (rc2$24 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + ((-rc2$3) * (rc2$25 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + ((-rc2$4) * ((-rc2$5) / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))) + (-(((-rc2$17) * (rc2$18 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$19) * (rc2$20 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$21) * (rc2$21 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$22) * (rc2$23 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$24) * (rc2$24 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$25) * (rc2$25 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))); // einf;
		rc2N$5 = (((((-rc2$5) * ((-rc2$5) / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23))))))) + (-(((-rc2$18) * (rc2$18 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$20) * (rc2$20 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))) + (-(((-rc2$23) * (rc2$23 / ((((((-rc2$5) * (-rc2$5)) + (-(((-rc2$18) * rc2$18)))) + (-(((-rc2$20) * rc2$20)))) + (-(((-rc2$23) * rc2$23)))))))))); // e0;
		z11$0 = ((double) Math.sqrt((-2.0d * ((((((rc1N$1 * sat1x$0) + (rc1N$2 * sat1y$0)) + (rc1N$3 * sat1z$0)) + (-rc1N$4)) + (-((rc1N$5 * sat1$4))))))) - d1$0); // 1.0;
		z12$0 = ((double) Math.sqrt((-2.0d * ((((((rc2N$1 * sat1x$0) + (rc2N$2 * sat1y$0)) + (rc2N$3 * sat1z$0)) + (-rc2N$4)) + (-((rc2N$5 * sat1$4))))))) - d1$0); // 1.0;
		z21$0 = ((double) Math.sqrt((-2.0d * ((((((rc1N$1 * sat2x$0) + (rc1N$2 * sat2y$0)) + (rc1N$3 * sat2z$0)) + (-rc1N$4)) + (-((rc1N$5 * sat2$4))))))) - d2$0); // 1.0;
		z22$0 = ((double) Math.sqrt((-2.0d * ((((((rc2N$1 * sat2x$0) + (rc2N$2 * sat2y$0)) + (rc2N$3 * sat2z$0)) + (-rc2N$4)) + (-((rc2N$5 * sat2$4))))))) - d2$0); // 1.0;
		z31$0 = ((double) Math.sqrt((-2.0d * ((((((rc1N$1 * sat3x$0) + (rc1N$2 * sat3y$0)) + (rc1N$3 * sat3z$0)) + (-rc1N$4)) + (-((rc1N$5 * sat3$4))))))) - d3$0); // 1.0;
		z32$0 = ((double) Math.sqrt((-2.0d * ((((((rc2N$1 * sat3x$0) + (rc2N$2 * sat3y$0)) + (rc2N$3 * sat3z$0)) + (-rc2N$4)) + (-((rc2N$5 * sat3$4))))))) - d3$0); // 1.0;
	}

	private double rc2$1;
	private double rc2$2;
	private double rc1$4;
	private double rc1$5;
	private double rc2N$4;
	private double rc2N$5;
	private double rc1$3;
	private double rc2$18;
	private double rc1$2;
	private double rc2$19;
	private double rc1$1;
	private double rc2$16;
	private double rc2$17;
	private double rc1$19;
	private double rc1$17;
	private double rc1$18;
	private double rc2$5;
	private double rc2$4;
	private double rc1$16;
	private double rc2$3;
	private double rcPpDual$6;
	private double sph2$4;
	private double sph1$4;
	private double rc2$23;
	private double rc2$24;
	private double rc2$25;
	private double rc2$20;
	private double rc2$21;
	private double rc2$22;
	private double rcPp$22;
	private double rcPp$21;
	private double rcPp$20;
	private double rcPp$25;
	private double rcPp$24;
	private double rcPp$23;
	private double len$0;
	private double sat1$4;
	private double rcPpDual$8;
	private double sph3$4;
	private double rcPpDual$13;
	private double rcPpDual$12;
	private double rcPp$17;
	private double rcPp$16;
	private double rcPp$19;
	private double rcPpDual$10;
	private double rcPp$18;
	private double rc1$20;
	private double sat3$4;
	private double rc1$21;
	private double rc1$22;
	private double rc1$23;
	private double rc1$24;
	private double sat2$4;
	private double rc1$25;
	private double rc1N$4;
	private double rc1N$5;

}
