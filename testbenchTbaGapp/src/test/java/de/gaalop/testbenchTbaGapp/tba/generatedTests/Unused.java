package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class Unused implements GAProgram {
	// input variables
	private double arw$0;
	private double arx$0;
	private double ary$0;
	private double lpz$0;
	private double arz$0;
	private double lpx$0;
	private double lpy$0;

	// output variables
	private double Din$0;
	private double Din$1;
	private double Din$2;
	private double Din$3;
	private double Din$4;
	private double Din$5;
	private double Din$6;
	private double Din$7;
	private double Din$8;
	private double Din$9;
	private double Din$10;
	private double Din$11;
	private double Din$12;
	private double Din$13;
	private double Din$14;
	private double Din$15;
	private double Din$16;
	private double Din$17;
	private double Din$18;
	private double Din$19;
	private double Din$20;
	private double Din$21;
	private double Din$22;
	private double Din$23;
	private double Din$24;
	private double Din$25;
	private double Din$26;
	private double Din$27;
	private double Din$28;
	private double Din$29;
	private double Din$30;
	private double Din$31;

	@Override
	public double getValue(String varName) {
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
		return 0.0d;
	}

	@Override
	public HashMap<String,Double> getValues() {
		HashMap<String,Double> result = new HashMap<String,Double>();
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
	public boolean setValue(String varName, double value) {
		if (varName.equals("arw$0")) { arw$0 = value; return true; }
		if (varName.equals("arx$0")) { arx$0 = value; return true; }
		if (varName.equals("ary$0")) { ary$0 = value; return true; }
		if (varName.equals("lpz$0")) { lpz$0 = value; return true; }
		if (varName.equals("arz$0")) { arz$0 = value; return true; }
		if (varName.equals("lpx$0")) { lpx$0 = value; return true; }
		if (varName.equals("lpy$0")) { lpy$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		rotor$7 = (-ary$0); // e1 ^ e3;
		translator$8 = (-((lpx$0 / 2.0d))); // e1 ^ einf;
		translator$11 = (-((lpy$0 / 2.0d))); // e2 ^ einf;
		translator$13 = (-((lpz$0 / 2.0d))); // e3 ^ einf;
		Din$0 = arw$0; // 1.0;
		Din$6 = arz$0; // e1 ^ e2;
		Din$7 = rotor$7; // e1 ^ e3;
		Din$8 = (((translator$8 * arw$0) + (-((translator$11 * arz$0)))) + (-((translator$13 * rotor$7)))); // e1 ^ einf;
		Din$10 = arx$0; // e2 ^ e3;
		Din$11 = (((translator$8 * arz$0) + (translator$11 * arw$0)) + (-((translator$13 * arx$0)))); // e2 ^ einf;
		Din$13 = (((translator$8 * rotor$7) + (translator$11 * arx$0)) + (translator$13 * arw$0)); // e3 ^ einf;
		Din$26 = (((translator$8 * arx$0) + (-((translator$11 * rotor$7)))) + (translator$13 * arz$0)); // e1 ^ (e2 ^ (e3 ^ einf));
	}

	private double rotor$7;
	private double translator$8;
	private double translator$13;
	private double translator$11;

}
