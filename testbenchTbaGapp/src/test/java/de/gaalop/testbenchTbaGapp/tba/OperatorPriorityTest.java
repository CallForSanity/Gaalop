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
import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class OperatorPriorityTest implements TBATestCase {

    public OperatorPriorityTest() {
    }

    @Override
    public String getCLUScript() {
        return  "?a = e1^einf.e1;\n" +
                "?b = (e1^einf).e1;\n" +
                "?c = einf^e1.e1;\n" +
                "?d = e1.e1^einf;\n" +
                "?f = e1.einf^e1;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 4)));   
        assertTrue(outputs.containsKey(new MultivectorComponent("b", 4)));   
        assertTrue(outputs.containsKey(new MultivectorComponent("c", 4)));  
        assertTrue(outputs.containsKey(new MultivectorComponent("d", 4)));   
        assertTrue(outputs.containsKey(new MultivectorComponent("f", 4)));  
        double a$4 = outputs.get(new MultivectorComponent("a", 4));
        double b$4 = outputs.get(new MultivectorComponent("b", 4));
        double c$4 = outputs.get(new MultivectorComponent("c", 4));
        double d$4 = outputs.get(new MultivectorComponent("d", 4));
        double f$4 = outputs.get(new MultivectorComponent("f", 4));
        
        assertEquals(-1, a$4, 0.01);
        assertEquals(-1, b$4, 0.01);
        assertEquals( 1, c$4, 0.01);
        assertEquals( 1, d$4, 0.01);
        assertEquals(-1, f$4, 0.01);
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<>();
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }
    
}
