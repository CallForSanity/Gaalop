package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Christian Steinmetz
 */
public class CoefficientTest implements TBATestCase {

    public CoefficientTest() {
    }
    
    @Override
    public String getCLUScript() {
        
        return 
            "?m = e1 + 2*e0 + 3*e1^e2;"
           + "?a1 = coefficient(m, e1);"
           + "?a0 = coefficient(m, e0);"
           + "?a12 = coefficient(m, e1^e2);"
           + "?a3 = coefficient(m, e3);"
            ;
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        // a3 is omitted in the output, because its value is 0
        assertTrue(outputs.containsKey(new MultivectorComponent("m", 1)));
        assertTrue(outputs.containsKey(new MultivectorComponent("m", 5)));
        assertTrue(outputs.containsKey(new MultivectorComponent("m", 6)));
        assertTrue(outputs.containsKey(new MultivectorComponent("a1", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("a0", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("a12", 0)));
        assertEquals(1,outputs.get(new MultivectorComponent("a1", 0)),0.01);
        assertEquals(2,outputs.get(new MultivectorComponent("a0", 0)),0.01);
        assertEquals(3,outputs.get(new MultivectorComponent("a12", 0)),0.01);
        assertEquals(6, outputs.size());
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
