package de.gaalop.gapp;

/**
 * Stores a constant which is an argument of setVectors
 * @author Christian Steinmetz
 */
public class ConstantSetVectorArgument extends SetVectorArgument {

    private double value;

    public ConstantSetVectorArgument(double value) {
        this.value = value;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
