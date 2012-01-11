package de.gaalop.testbenchTbaGapp.tba;

/**
 * Stores a variable and its value
 * @author Christian Steinmetz
 */
public class VariableValue {

    private String name;
    private double value;

    public VariableValue(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
