package de.gaalop.testbenchTbaGapp.tba.framework;

import de.gaalop.dfg.Variable;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 *
 * This interface is to implemented for creating a test class to black box test Gaalop
 *
 */
public interface TBATestCase {

    /**
     * This function returns a CLU script as a string, which is used to run Gaalop.
     *
     * Empty Test: "?a = 1;" because there must be at least one line to optimize
     * (marked with "?") in order for Gaalop to execute without exceptions
     *
     * @return
     */
    public String getCLUScript();

    /**
     *
     *
     * Empty Test: do nothing
     *
     * @param outputs
     */
    public void testOutputs(HashMap<Variable, Double> outputs);

    /**
     *
     *
     * Empty Test: return an initialized and empty HashMap
     * @return
     */
    public HashMap<Variable, Double> getInputValues();

    /**
     *
     *
     *
     * Empty Test: return some common geometric algebra name like "cga"
     *
     * The algebra the test case is run in. Used to initialize Gaalop
     * @return
     */
    public String getAlgebraName();

}
