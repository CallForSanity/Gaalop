package de.gaalop.gapp.variables;

/**
 * Represents a scalar in the GAPP IR.
 *
 * A multivector contains components, which are organized in a fixed blade list.
 *
 * @author christian
 */
public class GAPPScalarVariable extends GAPPVariableBase {

    private boolean constant;
    private float value;
  
    public GAPPScalarVariable(String name) {
        super(name);
        constant = false;
        value = 0;
    }

    public GAPPScalarVariable(float constantValue) {
        super(""); //TODO chs not good to choose "" for the variable name (BAD DATA STRUCTURE!)
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
    public String prettyPrint() {
        return (constant) ? Float.toString(value) : name;
    }

}
