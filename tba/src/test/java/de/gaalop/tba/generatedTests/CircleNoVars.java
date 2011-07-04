package de.gaalop.tba.generatedTests;

public class CircleNoVars implements GAProgram {
	// input variables

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
		return false;
	}
	
	@Override
	public void calculate() {
		m_1 = 1.7727273f;
		m_2 = 4.863636f;
		r_0 = (float) Math.sqrt((float) Math.sqrt(Math.abs(346.54434f)));
	}
}
