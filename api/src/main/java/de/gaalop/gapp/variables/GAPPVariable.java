package de.gaalop.gapp.variables;

public class GAPPVariable extends GAPPVariableBase {

    private boolean constant;
    private float value;
    private String name;

    public GAPPVariable(String name) {
        constant = false;
        value = 0;
        this.name = name;
    }

    public GAPPVariable(float constantValue) {
        constant = true;
        this.value = constantValue;
        name = null;
    }

    public boolean isConstant() {
        return constant;
    }

    public float getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return (constant) ? Float.toString(value) : name;
    }
}
