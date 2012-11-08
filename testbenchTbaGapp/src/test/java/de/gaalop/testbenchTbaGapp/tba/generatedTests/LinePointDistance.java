package de.gaalop.testbenchTbaGapp.tba.generatedTests;

import java.util.HashMap;

public class LinePointDistance implements GAProgram {
		
	// input variables
	private double p1x$0;
	private double p2y$0;
	private double p2x$0;
	private double p2z$0;
	private double pTsty$0;
	private double p1z$0;
	private double pTstz$0;
	private double p1y$0;
	private double pTstx$0;

	// local variables
	private double La$10;
	private double La$11;
	private double La$13;
	private double La$6;
	private double La$7;
	private double La$8;
	private double P$1;
	private double P$2;
	private double P$3;
	private double P$4;
	private double Pa$1;
	private double Pa$2;
	private double Pa$3;
	private double Pa$4;
	private double R$0;
	private double R$10;
	private double R$11;
	private double R$13;
	private double R$6;
	private double R$7;
	private double R$8;
	private double vTst$4;

	// output variables
	private double L$10;
	private double L$11;
	private double L$13;
	private double L$6;
	private double L$7;
	private double L$8;
	private double V$1;
	private double V$16;
	private double V$17;
	private double V$18;
	private double V$19;
	private double V$2;
	private double V$20;
	private double V$21;
	private double V$22;
	private double V$23;
	private double V$24;
	private double V$25;
	private double V$3;
	private double V$31;
	private double V$4;
	private double V$5;
	private double abstand$0;
	private double nor$1;
	private double nor$2;
	private double nor$3;
	private double nor$4;

	@Override
	public double getValue(String varName) {
		if (varName.equals("V$3")) return V$3;
		if (varName.equals("V$21")) return V$21;
		if (varName.equals("V$2")) return V$2;
		if (varName.equals("V$20")) return V$20;
		if (varName.equals("V$5")) return V$5;
		if (varName.equals("V$23")) return V$23;
		if (varName.equals("V$4")) return V$4;
		if (varName.equals("V$22")) return V$22;
		if (varName.equals("V$1")) return V$1;
		if (varName.equals("L$7")) return L$7;
		if (varName.equals("L$6")) return L$6;
		if (varName.equals("V$24")) return V$24;
		if (varName.equals("V$25")) return V$25;
		if (varName.equals("abstand$0")) return abstand$0;
		if (varName.equals("V$31")) return V$31;
		if (varName.equals("L$8")) return L$8;
		if (varName.equals("L$10")) return L$10;
		if (varName.equals("L$11")) return L$11;
		if (varName.equals("L$13")) return L$13;
		if (varName.equals("nor$1")) return nor$1;
		if (varName.equals("nor$2")) return nor$2;
		if (varName.equals("nor$4")) return nor$4;
		if (varName.equals("nor$3")) return nor$3;
		if (varName.equals("V$16")) return V$16;
		if (varName.equals("V$19")) return V$19;
		if (varName.equals("V$17")) return V$17;
		if (varName.equals("V$18")) return V$18;
		return 0.0d;
	}

	@Override
	public HashMap<String,Double> getValues() {
		HashMap<String,Double> result = new HashMap<String,Double>();
		result.put("V$3",V$3);
		result.put("V$21",V$21);
		result.put("V$2",V$2);
		result.put("V$20",V$20);
		result.put("V$5",V$5);
		result.put("V$23",V$23);
		result.put("V$4",V$4);
		result.put("V$22",V$22);
		result.put("V$1",V$1);
		result.put("L$7",L$7);
		result.put("L$6",L$6);
		result.put("V$24",V$24);
		result.put("V$25",V$25);
		result.put("abstand$0",abstand$0);
		result.put("V$31",V$31);
		result.put("L$8",L$8);
		result.put("L$10",L$10);
		result.put("L$11",L$11);
		result.put("L$13",L$13);
		result.put("nor$1",nor$1);
		result.put("nor$2",nor$2);
		result.put("nor$4",nor$4);
		result.put("nor$3",nor$3);
		result.put("V$16",V$16);
		result.put("V$19",V$19);
		result.put("V$17",V$17);
		result.put("V$18",V$18);
		return result;
	}
	@Override
	public boolean setValue(String varName, double value) {
		if (varName.equals("p1x$0")) { p1x$0 = value; return true; }
		if (varName.equals("p2y$0")) { p2y$0 = value; return true; }
		if (varName.equals("p2x$0")) { p2x$0 = value; return true; }
		if (varName.equals("p2z$0")) { p2z$0 = value; return true; }
		if (varName.equals("pTsty$0")) { pTsty$0 = value; return true; }
		if (varName.equals("p1z$0")) { p1z$0 = value; return true; }
		if (varName.equals("pTstz$0")) { pTstz$0 = value; return true; }
		if (varName.equals("p1y$0")) { p1y$0 = value; return true; }
		if (varName.equals("pTstx$0")) { pTstx$0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		vTst$4 = (((((pTstx$0 * pTstx$0) + (pTsty$0 * pTsty$0)) + (pTstz$0 * pTstz$0))) / 2.0d); // einf;
		L$6 = (p1z$0 + (-p2z$0)); // e1 ^ e2;
		L$7 = (-((p1y$0 + (-p2y$0)))); // e1 ^ e3;
		L$8 = (-(((p1y$0 * p2z$0) + (-((p1z$0 * p2y$0)))))); // e1 ^ einf;
		L$10 = (p1x$0 + (-p2x$0)); // e2 ^ e3;
		L$11 = ((p1x$0 * p2z$0) + (-((p1z$0 * p2x$0)))); // e2 ^ einf;
		L$13 = (-(((p1x$0 * p2y$0) + (-((p1y$0 * p2x$0)))))); // e3 ^ einf;
		La$6 = (L$6 * ((double) Math.sqrt((double) Math.abs((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))) / (double) Math.sqrt((double) Math.abs((((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10)))))) * ((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))))))); // e1 ^ e2;
		La$7 = (L$7 * ((double) Math.sqrt((double) Math.abs((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))) / (double) Math.sqrt((double) Math.abs((((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10)))))) * ((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))))))); // e1 ^ e3;
		La$8 = (L$8 * ((double) Math.sqrt((double) Math.abs((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))) / (double) Math.sqrt((double) Math.abs((((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10)))))) * ((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))))))); // e1 ^ einf;
		La$10 = (L$10 * ((double) Math.sqrt((double) Math.abs((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))) / (double) Math.sqrt((double) Math.abs((((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10)))))) * ((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))))))); // e2 ^ e3;
		La$11 = (L$11 * ((double) Math.sqrt((double) Math.abs((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))) / (double) Math.sqrt((double) Math.abs((((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10)))))) * ((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))))))); // e2 ^ einf;
		La$13 = (L$13 * ((double) Math.sqrt((double) Math.abs((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))) / (double) Math.sqrt((double) Math.abs((((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10)))))) * ((((-((L$6 * (-L$6)))) + (-((L$7 * (-L$7))))) + (-((L$10 * (-L$10))))))))))); // e3 ^ einf;
		R$0 = (double) Math.cos(0.785398006439209d); // 1.0;
		R$6 = (-((La$6 * (double) Math.sin(0.785398006439209d)))); // e1 ^ e2;
		R$7 = (-((La$7 * (double) Math.sin(0.785398006439209d)))); // e1 ^ e3;
		R$8 = (-((La$8 * (double) Math.sin(0.785398006439209d)))); // e1 ^ einf;
		R$10 = (-((La$10 * (double) Math.sin(0.785398006439209d)))); // e2 ^ e3;
		R$11 = (-((La$11 * (double) Math.sin(0.785398006439209d)))); // e2 ^ einf;
		R$13 = (-((La$13 * (double) Math.sin(0.785398006439209d)))); // e3 ^ einf;
		V$1 = ((((((((((((R$0 * pTstx$0) + (R$6 * pTsty$0)) + (R$7 * pTstz$0)) + (-R$8))) * R$0) + (-(((((((R$0 * pTsty$0) + (-((R$6 * pTstx$0)))) + (R$10 * pTstz$0)) + (-R$11))) * (-R$6))))) + (-(((((((R$0 * pTstz$0) + (-((R$7 * pTstx$0)))) + (-((R$10 * pTsty$0)))) + (-R$13))) * (-R$7))))) + (R$0 * (-R$8))) + (-((((((R$6 * pTstz$0) + (-((R$7 * pTsty$0)))) + (R$10 * pTstx$0))) * (-R$10))))) + (R$6 * (-R$11))) + (R$7 * (-R$13))); // e1;
		V$2 = ((((((((((((R$0 * pTstx$0) + (R$6 * pTsty$0)) + (R$7 * pTstz$0)) + (-R$8))) * (-R$6)) + ((((((R$0 * pTsty$0) + (-((R$6 * pTstx$0)))) + (R$10 * pTstz$0)) + (-R$11))) * R$0)) + (-(((((((R$0 * pTstz$0) + (-((R$7 * pTstx$0)))) + (-((R$10 * pTsty$0)))) + (-R$13))) * (-R$10))))) + (R$0 * (-R$11))) + (((((R$6 * pTstz$0) + (-((R$7 * pTsty$0)))) + (R$10 * pTstx$0))) * (-R$7))) + (-((R$6 * (-R$8))))) + (R$10 * (-R$13))); // e2;
		V$3 = ((((((((((((R$0 * pTstx$0) + (R$6 * pTsty$0)) + (R$7 * pTstz$0)) + (-R$8))) * (-R$7)) + ((((((R$0 * pTsty$0) + (-((R$6 * pTstx$0)))) + (R$10 * pTstz$0)) + (-R$11))) * (-R$10))) + ((((((R$0 * pTstz$0) + (-((R$7 * pTstx$0)))) + (-((R$10 * pTsty$0)))) + (-R$13))) * R$0)) + (R$0 * (-R$13))) + (-((((((R$6 * pTstz$0) + (-((R$7 * pTsty$0)))) + (R$10 * pTstx$0))) * (-R$6))))) + (-((R$7 * (-R$8))))) + (-((R$10 * (-R$11))))); // e3;
		V$4 = (((((((((((((((R$0 * pTstx$0) + (R$6 * pTsty$0)) + (R$7 * pTstz$0)) + (-R$8))) * (-R$8)) + ((((((R$0 * pTsty$0) + (-((R$6 * pTstx$0)))) + (R$10 * pTstz$0)) + (-R$11))) * (-R$11))) + ((((((R$0 * pTstz$0) + (-((R$7 * pTstx$0)))) + (-((R$10 * pTsty$0)))) + (-R$13))) * (-R$13))) + ((((((R$0 * vTst$4) + (-((R$8 * pTstx$0)))) + (-((R$11 * pTsty$0)))) + (-((R$13 * pTstz$0))))) * R$0)) + (-((((((R$6 * vTst$4) + (-((R$8 * pTsty$0)))) + (R$11 * pTstx$0))) * (-R$6))))) + (-((((((R$7 * vTst$4) + (-((R$8 * pTstz$0)))) + (R$13 * pTstx$0))) * (-R$7))))) + (-((R$8 * (-R$8))))) + (-((((((R$10 * vTst$4) + (-((R$11 * pTstz$0)))) + (R$13 * pTsty$0))) * (-R$10))))) + (-((R$11 * (-R$11))))) + (-((R$13 * (-R$13))))); // einf;
		V$5 = ((((R$0 * R$0) + (-((R$6 * (-R$6))))) + (-((R$7 * (-R$7))))) + (-((R$10 * (-R$10))))); // e0;
		V$16 = ((((((((((((R$0 * pTstx$0) + (R$6 * pTsty$0)) + (R$7 * pTstz$0)) + (-R$8))) * (-R$10)) + (-(((((((R$0 * pTsty$0) + (-((R$6 * pTstx$0)))) + (R$10 * pTstz$0)) + (-R$11))) * (-R$7))))) + ((((((R$0 * pTstz$0) + (-((R$7 * pTstx$0)))) + (-((R$10 * pTsty$0)))) + (-R$13))) * (-R$6))) + (((((R$6 * pTstz$0) + (-((R$7 * pTsty$0)))) + (R$10 * pTstx$0))) * R$0)) + (R$6 * (-R$13))) + (-((R$7 * (-R$11))))) + (R$10 * (-R$8))); // e1 ^ (e2 ^ e3);
		V$17 = ((((((((((((((R$0 * pTstx$0) + (R$6 * pTsty$0)) + (R$7 * pTstz$0)) + (-R$8))) * (-R$11)) + (-(((((((R$0 * pTsty$0) + (-((R$6 * pTstx$0)))) + (R$10 * pTstz$0)) + (-R$11))) * (-R$8))))) + ((((((R$0 * vTst$4) + (-((R$8 * pTstx$0)))) + (-((R$11 * pTsty$0)))) + (-((R$13 * pTstz$0))))) * (-R$6))) + (((((R$6 * pTstz$0) + (-((R$7 * pTsty$0)))) + (R$10 * pTstx$0))) * (-R$13))) + (((((R$6 * vTst$4) + (-((R$8 * pTsty$0)))) + (R$11 * pTstx$0))) * R$0)) + (-((((((R$7 * vTst$4) + (-((R$8 * pTstz$0)))) + (R$13 * pTstx$0))) * (-R$10))))) + (-((R$8 * (-R$11))))) + (((((R$10 * vTst$4) + (-((R$11 * pTstz$0)))) + (R$13 * pTsty$0))) * (-R$7))) + (R$11 * (-R$8))); // e1 ^ (e2 ^ einf);
		V$18 = ((((R$0 * (-R$6)) + (R$6 * R$0)) + (-((R$7 * (-R$10))))) + (R$10 * (-R$7))); // e1 ^ (e2 ^ e0);
		V$19 = ((((((((((((((R$0 * pTstx$0) + (R$6 * pTsty$0)) + (R$7 * pTstz$0)) + (-R$8))) * (-R$13)) + (-(((((((R$0 * pTstz$0) + (-((R$7 * pTstx$0)))) + (-((R$10 * pTsty$0)))) + (-R$13))) * (-R$8))))) + ((((((R$0 * vTst$4) + (-((R$8 * pTstx$0)))) + (-((R$11 * pTsty$0)))) + (-((R$13 * pTstz$0))))) * (-R$7))) + (-((((((R$6 * pTstz$0) + (-((R$7 * pTsty$0)))) + (R$10 * pTstx$0))) * (-R$11))))) + (((((R$6 * vTst$4) + (-((R$8 * pTsty$0)))) + (R$11 * pTstx$0))) * (-R$10))) + (((((R$7 * vTst$4) + (-((R$8 * pTstz$0)))) + (R$13 * pTstx$0))) * R$0)) + (-((R$8 * (-R$13))))) + (-((((((R$10 * vTst$4) + (-((R$11 * pTstz$0)))) + (R$13 * pTsty$0))) * (-R$6))))) + (R$13 * (-R$8))); // e1 ^ (e3 ^ einf);
		V$20 = ((((R$0 * (-R$7)) + (R$6 * (-R$10))) + (R$7 * R$0)) + (-((R$10 * (-R$6))))); // e1 ^ (e3 ^ e0);
		V$21 = ((((((R$0 * (-R$8)) + (R$6 * (-R$11))) + (R$7 * (-R$13))) + (R$8 * R$0)) + (-((R$11 * (-R$6))))) + (-((R$13 * (-R$7))))); // e1 ^ (einf ^ e0);
		V$22 = ((((((((((((((R$0 * pTsty$0) + (-((R$6 * pTstx$0)))) + (R$10 * pTstz$0)) + (-R$11))) * (-R$13)) + (-(((((((R$0 * pTstz$0) + (-((R$7 * pTstx$0)))) + (-((R$10 * pTsty$0)))) + (-R$13))) * (-R$11))))) + ((((((R$0 * vTst$4) + (-((R$8 * pTstx$0)))) + (-((R$11 * pTsty$0)))) + (-((R$13 * pTstz$0))))) * (-R$10))) + (((((R$6 * pTstz$0) + (-((R$7 * pTsty$0)))) + (R$10 * pTstx$0))) * (-R$8))) + (-((((((R$6 * vTst$4) + (-((R$8 * pTsty$0)))) + (R$11 * pTstx$0))) * (-R$7))))) + (((((R$7 * vTst$4) + (-((R$8 * pTstz$0)))) + (R$13 * pTstx$0))) * (-R$6))) + (((((R$10 * vTst$4) + (-((R$11 * pTstz$0)))) + (R$13 * pTsty$0))) * R$0)) + (-((R$11 * (-R$13))))) + (R$13 * (-R$11))); // e2 ^ (e3 ^ einf);
		V$23 = ((((R$0 * (-R$10)) + (-((R$6 * (-R$7))))) + (R$7 * (-R$6))) + (R$10 * R$0)); // e2 ^ (e3 ^ e0);
		V$24 = ((((((R$0 * (-R$11)) + (-((R$6 * (-R$8))))) + (R$8 * (-R$6))) + (R$10 * (-R$13))) + (R$11 * R$0)) + (-((R$13 * (-R$10))))); // e2 ^ (einf ^ e0);
		V$25 = ((((((R$0 * (-R$13)) + (-((R$7 * (-R$8))))) + (R$8 * (-R$7))) + (-((R$10 * (-R$11))))) + (R$11 * (-R$10))) + (R$13 * R$0)); // e3 ^ (einf ^ e0);
		V$31 = ((((((R$6 * (-R$13)) + (-((R$7 * (-R$11))))) + (R$8 * (-R$10))) + (R$10 * (-R$8))) + (-((R$11 * (-R$7))))) + (R$13 * (-R$6))); // e1 ^ (e2 ^ (e3 ^ (einf ^ e0)));
		P$1 = (-(((((((p1y$0 * p2z$0) + (-((p1z$0 * p2y$0))))) * V$5) + (-((((p1y$0 + (-p2y$0))) * V$3)))) + (((p1z$0 + (-p2z$0))) * V$2)))); // e1;
		P$2 = ((((((p1x$0 * p2z$0) + (-((p1z$0 * p2x$0))))) * V$5) + (-((((p1x$0 + (-p2x$0))) * V$3)))) + (((p1z$0 + (-p2z$0))) * V$1)); // e2;
		P$3 = (-(((((((p1x$0 * p2y$0) + (-((p1y$0 * p2x$0))))) * V$5) + (-((((p1x$0 + (-p2x$0))) * V$2)))) + (((p1y$0 + (-p2y$0))) * V$1)))); // e3;
		P$4 = (-(((((((p1x$0 * p2y$0) + (-((p1y$0 * p2x$0))))) * V$3) + (-(((((p1x$0 * p2z$0) + (-((p1z$0 * p2x$0))))) * V$2)))) + ((((p1y$0 * p2z$0) + (-((p1z$0 * p2y$0))))) * V$1)))); // einf;
		Pa$1 = (P$1 * ((double) Math.sqrt((double) Math.abs((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))) / (double) Math.sqrt((double) Math.abs((((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3))) * ((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))))))); // e1;
		Pa$2 = (P$2 * ((double) Math.sqrt((double) Math.abs((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))) / (double) Math.sqrt((double) Math.abs((((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3))) * ((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))))))); // e2;
		Pa$3 = (P$3 * ((double) Math.sqrt((double) Math.abs((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))) / (double) Math.sqrt((double) Math.abs((((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3))) * ((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))))))); // e3;
		Pa$4 = (P$4 * ((double) Math.sqrt((double) Math.abs((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))) / (double) Math.sqrt((double) Math.abs((((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3))) * ((((P$1 * P$1) + (P$2 * P$2)) + (P$3 * P$3)))))))); // einf;
		abstand$0 = (double) Math.sqrt((double) Math.abs(((((((Pa$1 * pTstx$0) + (Pa$2 * pTsty$0)) + (Pa$3 * pTstz$0)) + (-Pa$4))) * (((((Pa$1 * pTstx$0) + (Pa$2 * pTsty$0)) + (Pa$3 * pTstz$0)) + (-Pa$4)))))); // 1.0;
		nor$1 = Pa$1; // e1;
		nor$2 = Pa$2; // e2;
		nor$3 = Pa$3; // e3;
		nor$4 = (Pa$4 + (-Pa$4)); // einf;
	}


}
