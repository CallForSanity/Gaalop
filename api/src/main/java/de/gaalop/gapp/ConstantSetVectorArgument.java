package de.gaalop.gapp;

/**
 *
 * @author Christian Steinmetz
 */
public class ConstantSetVectorArgument extends SetVectorArgument {

    private float value;

    public ConstantSetVectorArgument(float value) {
        this.value = value;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
