package de.gaalop.tba.generatedTests;

public class CircleOneVar implements GAProgram {
	// input variables
	private float x1_0;

	// output variables
	private float m_1;
	private float r_0;
	private float m_2;

	@Override
	public float getValue(String varName) {
		if (varName.equals("m_1")) return m_1;
		if (varName.equals("r_0")) return r_0;
		if (varName.equals("m_2")) return m_2;
		return 0.0f;
	}

	@Override
	public boolean setValue(String varName, float value) {
		if (varName.equals("x1_0")) { x1_0 = value; return true; }
		return false;
	}
	
	@Override
	public void calculate() {
		float p1_4 = (((x1_0 / 2.0f) * x1_0) + 2.0f);
		float c_7 = (((90.0f + (-((p1_4 * 9.0f)))) + 182.0f) + (((p1_4 + -45.0f)) * 4.0f));
		float c_10 = (-(((((x1_0 * 45.0f) + (-((p1_4 * 3.0f)))) + (-((((x1_0 + -3.0f)) * 26.0f)))) + (((p1_4 + -45.0f)) * 6.0f))));
		float c_13 = (-(((((((x1_0 * 9.0f) + -6.0f)) * 26.0f) + (-(((((x1_0 * 45.0f) + (-((p1_4 * 3.0f))))) * 4.0f)))) + (((90.0f + (-((p1_4 * 9.0f))))) * 6.0f))));
		float c_14 = ((((x1_0 * 9.0f) + -6.0f) + (-((((x1_0 + -3.0f)) * 4.0f)))) + -42.0f);
		m_1 = ((-(((-c_14) * c_7))) + (c_7 * c_14));
		m_2 = ((-(((-c_14) * c_10))) + (c_10 * c_14));
		float m_5 = (((-c_14) * c_14) + ((-c_14) * c_14));
		float m_21 = ((-((c_7 * c_14))) + (-(((-c_14) * c_7))));
		float m_24 = ((-((c_10 * c_14))) + (-(((-c_14) * c_10))));
		m_1 = (((-m_1) * ((-m_5) / ((-m_5) * (-m_5)))) + ((-m_5) * ((-m_21) / ((-m_5) * (-m_5)))));
		m_2 = (((-m_2) * ((-m_5) / ((-m_5) * (-m_5)))) + ((-m_5) * ((-m_24) / ((-m_5) * (-m_5)))));
		r_0 = (float) Math.sqrt((float) Math.sqrt(Math.abs((((((((-((c_7 * c_7))) + (-((c_10 * c_10)))) + (c_13 * c_14)) + (c_14 * c_13))) * ((c_14 * c_14) / ((c_14 * c_14) * (c_14 * c_14)))) * ((((((-((c_7 * c_7))) + (-((c_10 * c_10)))) + (c_13 * c_14)) + (c_14 * c_13))) * ((c_14 * c_14) / ((c_14 * c_14) * (c_14 * c_14))))))));
	}
}
