package de.gaalop.testbenchTbaGapp.tba.common;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Implements a simple test on trigonometric functions
 * @author Christian Steinmetz
 */
public class TrigonometricFunctions implements TBATestCase {

    @Override
    public String getCLUScript() {
        return "?r = cos(0);";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("r", 0)));
        assertEquals(1,outputs.get(new MultivectorComponent("r", 0)),0.01);
        assertEquals(1, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<Variable, Double>();
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }
}
