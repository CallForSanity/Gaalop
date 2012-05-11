package de.gaalop.cfg;

import de.gaalop.dfg.Variable;

/**
 * This class represents a Slider in the input file. A slider consists of a label string, and values for minimum and maximum 
 * values, increment step and initial value. Sliders are not intended to be an actual node in the control flow graph.
 * 
 * @author Christian Schwinn
 */
public class Slider {

	private Variable variable;
	private final String label;
	private final double min, max, step, init;

	/**
	 * Creates a new slider object which is associated with the given variable.
	 * 
	 * @param variable associated variable 
	 * @param label label string
	 * @param min minimum value
	 * @param max maximum value
	 * @param step increment step
	 * @param init initial value
	 */
	public Slider(Variable variable, String label, double min, double max, double step, double init) {
		this.variable = variable;
		this.label = label;
		this.min = min;
		this.max = max;
		this.step = step;
		this.init = init;
	}

	public void setAssociatedVariable(Variable variable) {
		this.variable = variable;
	}
	
	public Variable getAssociatedVariable() {
		return variable;
	}

	public String getLabel() {
		return label;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getStep() {
		return step;
	}

	public double getInitalValue() {
		return init;
	}
}
