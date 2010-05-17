package de.gaalop.cfg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.gaalop.dfg.Variable;

public final class VariableScope {
	/** Special scope representing global scope. */
	public static final VariableScope GLOBAL = new VariableScope();
	private static int num;

	private String name;
	private Set<Variable> variables = new HashSet<Variable>();
	private VariableScope parent;

	/**
	 * Private constructor for global scope only.
	 */
	private VariableScope() {
		parent = this;
		name = "GLOBAL";
	}

	public VariableScope(VariableScope parent) {
		this.parent = parent;
		name = Integer.toString(++num);
	}

	public void addVariable(Variable v) {
		variables.add(v);
	}

	public void removeVariable(Variable v) {
		variables.remove(v);
	}

	public VariableScope getParent() {
		return parent;
	}
	
	public Set<Variable> getDefinedVariables() {
		return variables;
	}

	/**
	 * Checks if the given variable name is defined in this scope or its parents.
	 * 
	 * @param name name of variable to be checked
	 * @return true, if given name is defined in this or one of the parents' scope
	 */
	public boolean containsDefinition(String name) {
		for (Variable v : variables) {
			if (v.getName().equals(name)) {
				return true;
			}
		}
		if (this != GLOBAL) {
			return parent.containsDefinition(name);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		String result = name + "=";
		result += Arrays.toString(variables.toArray());
		return result;
	}
}
