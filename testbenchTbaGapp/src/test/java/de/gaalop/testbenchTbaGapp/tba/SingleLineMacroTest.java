package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author csteinmetz15
 */
public class SingleLineMacroTest implements TBATestCase {

    public SingleLineMacroTest() {
    }
    
    @Override
    public String getCLUScript() {
        
        return 
            "myMac = {\n" +
            "	_P(1) + _P(2)\n" +
            "}\n" +
            "\n" +
            "?a = b;\n" +
            "?c = b + x;\n" +
            "?d = myMac(a, c);\n" +
            "?f = d - b;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        // a3 is omitted in the output, because its value is 0
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("c", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("d", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("f", 0)));
        assertEquals(5,outputs.get(new MultivectorComponent("a", 0)),0.01);
        assertEquals(8,outputs.get(new MultivectorComponent("c", 0)),0.01);
        assertEquals(13,outputs.get(new MultivectorComponent("d", 0)),0.01);
        assertEquals(8,outputs.get(new MultivectorComponent("f", 0)),0.01);
        assertEquals(4, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        HashMap<Variable, Double> result = new HashMap<Variable, Double>();
        result.put(new Variable("b"), (double) 5);
        result.put(new Variable("x"), (double) 3);
        return result;
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }
    
}
