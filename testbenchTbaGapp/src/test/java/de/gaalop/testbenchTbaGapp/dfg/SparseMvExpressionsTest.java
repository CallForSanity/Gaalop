package de.gaalop.testbenchTbaGapp.dfg;

import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Adrian Kiesthardt
 *
 */
public class SparseMvExpressionsTest implements TBATestCase {


    /**
     *
     * @return
     */
    @Override
    public String getCLUScript() {
        return "?c = 1;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        // assertTrue(true);
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<Variable, Double>();
        //return null;
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }
}
