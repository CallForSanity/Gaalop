package de.gaalop.testbenchTbaGapp.tba.generatedTests;



import java.util.HashMap;

/**
 * Performs the calculations specified in a Geometric Algebra Program
 */
public interface GAProgram {

    /**
     * Performs the calculation
     */
    public void calculate();

    /**
     * Returns the value of a variable
     * @param varName The variable name, specified in the Geometric Algebra program
     * @return The value of the variable with the given name
     */
    public float getValue(String varName);

    /**
     * Sets the value of a variable
     * @param varName The variable name, specified in the Geometric Algebra program
     * @param value The value
     * @returns <value>true</value> if the setting was successful, <value>false</value> otherwise
     */
    public boolean setValue(String varName, float value);

    /**
      * Returns all values in a map name->value
      * @return The map which contains all values
      */
     public HashMap<String,Float> getValues();

}
