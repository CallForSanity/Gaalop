package de.gaalop.tba;

import java.util.LinkedList;

/**
 * Defines an interface for a test
 * @author Christian Steinmetz
 */
public interface GenericTestable {

    /**
     * Returns the CLUScript to be used for testing
     * @return The CLUScript
     */
    public String getCLUScript();

   /**
    * Returns the InputOutputs for testing
    * @return The InputOutputs
    */
    public LinkedList<InputOutput> getInputOutputs();

    /**
     * Returns the used algebra in test program
     * @return The used algebra
     */
    public UseAlgebra getUsedAlgebra();

}
