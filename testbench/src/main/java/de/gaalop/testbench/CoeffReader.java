package de.gaalop.testbench;

import java.util.HashMap;
import java.util.Map;

public class CoeffReader {
	
	private static final class BladeInfo {
		public final int sign;
		public final int index;
		
		public BladeInfo(int sign, int index) {
			this.sign = sign;
			this.index = index;
		}
	}

	private static final String MINUS = "-";
	
	private static final Map<String, BladeInfo> clucalcBlades;
	
	static {
		clucalcBlades = new HashMap<String, BladeInfo>();
		
		clucalcBlades.put(null, new BladeInfo(1, 0));
		
		clucalcBlades.put("e1", new BladeInfo(1, 1));
		clucalcBlades.put("e2", new BladeInfo(1, 2));
		clucalcBlades.put("e3", new BladeInfo(1, 3));
		clucalcBlades.put("e", new BladeInfo(1, 4));
		clucalcBlades.put("e0", new BladeInfo(1, 5));
		
		clucalcBlades.put("e12", new BladeInfo(1, 6));
		clucalcBlades.put("e31", new BladeInfo(-1, 7)); // e31 == -e1^e3
		clucalcBlades.put("(e1^e)", new BladeInfo(1, 8));
		clucalcBlades.put("(e1^e0)", new BladeInfo(1, 9));
		clucalcBlades.put("e23", new BladeInfo(1, 10));
		clucalcBlades.put("(e2^e)", new BladeInfo(1, 11));
		clucalcBlades.put("(e2^e0)", new BladeInfo(1, 12));
		clucalcBlades.put("(e3^e)", new BladeInfo(1, 13));
		clucalcBlades.put("(e3^e0)", new BladeInfo(1, 14));
		clucalcBlades.put("E", new BladeInfo(1, 15));
		
		clucalcBlades.put("e123", new BladeInfo(1, 16));
		clucalcBlades.put("(e12^e)", new BladeInfo(1, 17));
		clucalcBlades.put("(e12^e0)", new BladeInfo(1, 18));
		clucalcBlades.put("(e31^e)", new BladeInfo(-1, 19)); // e31^einf == -e1^e3^einf
		clucalcBlades.put("(e31^e0)", new BladeInfo(-1, 20)); // e31^e0 == -e1^e3^e0
		clucalcBlades.put("(e1^E)", new BladeInfo(1, 21));
		clucalcBlades.put("(e23^e)", new BladeInfo(1, 22));
		clucalcBlades.put("(e23^e0)", new BladeInfo(1, 23));
		clucalcBlades.put("(e2^E)", new BladeInfo(1, 24));
		clucalcBlades.put("(e3^E)", new BladeInfo(1, 25));
		
		clucalcBlades.put("(e123^e)", new BladeInfo(1, 26));
		clucalcBlades.put("(e123^e0)", new BladeInfo(1, 27));
		clucalcBlades.put("(e12^E)", new BladeInfo(1, 28));
		clucalcBlades.put("(e31^E)", new BladeInfo(-1, 29)); // e31^E == -e1^e3^einf^e0
		clucalcBlades.put("(e23^E)", new BladeInfo(1, 30));
		
		clucalcBlades.put("I", new BladeInfo(1, 31));
	}
	
	private double[] coeffs = new double[32];

	public void addCoeff(String sign, String coeff, String blade) {
		double value = Double.parseDouble(coeff);
		if (MINUS.equals(sign)) {
			value = -value;
		}
		BladeInfo info = clucalcBlades.get(blade);
		if (info.sign < 0) {
			value = -value;
		}
		coeffs[info.index] = value;
	}
	
	public double[] getCoeffs() {
		return coeffs;
	}

}
