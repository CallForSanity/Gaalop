/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class FunctionTest implements TBATestCase {

    public FunctionTest() {
    }
    
    @Override
    public String getCLUScript() {
        
        return 
            "myFunc = {\n" +
            "  rx = _P(1); ry = _P(2); rz = _P(3);\n" +
            "  e1*rx+e2*ry+e3*rz\n" +
            "}\n" +
            "\n" +
            "?a=myFunc(1,2,3);";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 1)));
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 2)));
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 3)));
        assertEquals(1,outputs.get(new MultivectorComponent("a", 1)),0.01);
        assertEquals(2,outputs.get(new MultivectorComponent("a", 2)),0.01);
        assertEquals(3,outputs.get(new MultivectorComponent("a", 3)),0.01);
        assertEquals(3, outputs.size());
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
