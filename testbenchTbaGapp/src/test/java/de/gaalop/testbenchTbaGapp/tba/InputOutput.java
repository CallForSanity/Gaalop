package de.gaalop.testbenchTbaGapp.tba;

import java.util.LinkedList;

/**
 * Stores the input data for testing and provides code for checking the output of the test
 * @author Christian Steinmetz
 */
public abstract class InputOutput {

    /**
     * Returns the variable values used for input
     * @return The variable values to be used for input
     */
    public abstract LinkedList<VariableValue> getInputs();

    /**
     * Returns the check outputs code for testing
     * @return The code for checking the output
     */
    public abstract String getCheckOutputsCode();

    /**
     * Returns the index of the test instance
     * @return The index
     */
    public abstract int getNo();
}
