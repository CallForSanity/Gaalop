package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Implements a test for GCSE
 * @author CSteinmetz15
 */
public class GCSETest implements TBATestCase {

    public GCSETest() {
    }

    @Override
    public String getCLUScript() {
        return "?a = z+b+c;"
             + "?d = b+c+f;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("d", 0)));
        assertEquals(20,outputs.get(new MultivectorComponent("a", 0)),0.01);
        assertEquals(36,outputs.get(new MultivectorComponent("d", 0)),0.01);
        assertEquals(2, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        HashMap<Variable, Double> result = new HashMap<Variable, Double>();
        result.put(new Variable("z"), (double) 5);
        result.put(new Variable("c"), (double) 13);
        result.put(new Variable("b"), (double) 2);
        result.put(new Variable("f"), (double) 21);
        return result;
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }
    
}
