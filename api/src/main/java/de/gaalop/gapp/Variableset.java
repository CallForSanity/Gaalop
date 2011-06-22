package de.gaalop.gapp;

import de.gaalop.gapp.variables.GAPPVariable;

import java.util.Vector;

public class Variableset {

    private Vector<GAPPVariable> variables;

    public Variableset() {
        variables = new Vector<GAPPVariable>();
    }

    public Variableset(Vector<GAPPVariable> variables) {
        this.variables = variables;
    }

    public int size() {
        return variables.size();
    }

    public GAPPVariable get(int index) {
        return variables.get(index);
    }

    public void add(GAPPVariable variable) {
        variables.add(variable);
    }

    public Vector<GAPPVariable> getVariables() {
        return variables;
    }
}
