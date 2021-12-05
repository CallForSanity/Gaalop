package de.gaalop.testbenchTbaGapp.gapp;

import de.gaalop.gapp.executer.Executer;
import java.util.HashMap;

/**
 * Declares an interface for comfortable creation of new tests.
 * All classes to be used by GAPPTestCreator must implement this interface.
 *
 * @author Christian Steinmetz
 */
public interface GAPPTestable {

    /**
     * Returns the source of the test program
     * @return The clucalc source
     */
    public String getSource();

    /**
     * Returns the input values for execution
     * @return A map with the variable names and according float values
     */
    public HashMap<String, Double> getInputs();

    /**
     * This method is called, when the programm was compiled and executed.
     * This method should test the values in the given executer instance.
     *
     * @param executer The executer instance of the test program
     * which shoult be tested
     */
    public void testOutput(Executer executer);

}
