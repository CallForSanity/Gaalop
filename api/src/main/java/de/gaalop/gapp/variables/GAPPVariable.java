package de.gaalop.gapp.variables;

public class GAPPVariable extends GAPPVariableBase {

    private boolean constant;
    private float value;
  

    public GAPPVariable(String name) {
        super(name);
        constant = false;
        value = 0;
    }

    public GAPPVariable(float constantValue) {
        super(null);
        constant = true;
        this.value = constantValue;
    }

    public boolean isConstant() {
        return constant;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        return (constant) ? Float.toString(value) : name;
    }
}
