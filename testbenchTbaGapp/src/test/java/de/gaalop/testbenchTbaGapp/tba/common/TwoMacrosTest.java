package de.gaalop.testbenchTbaGapp.tba.common;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;
import org.junit.Ignore;

import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
@Ignore
public class TwoMacrosTest implements TBATestCase {

    @Override
    public String getCLUScript() {
        
        return 
                "myFunc1 = {\n" +
                "2*_P(1)\n" +
                "}\n" +
                "\n" +
                "myFunc2 = {\n" +
                "2*myFunc1(_P(1))\n" +
                "}\n" +
                "\n" +
                "?a = myFunc2(2);\n" +
                "?b = myFunc1(2);\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 0)));
        assertEquals(8,outputs.get(new MultivectorComponent("a", 0)),0.01);
        assertTrue(outputs.containsKey(new MultivectorComponent("b", 0)));
        assertEquals(4,outputs.get(new MultivectorComponent("b", 0)),0.01);
        assertEquals(2, outputs.size());
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
