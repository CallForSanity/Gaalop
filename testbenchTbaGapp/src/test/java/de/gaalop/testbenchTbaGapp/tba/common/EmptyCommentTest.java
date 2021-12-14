package de.gaalop.testbenchTbaGapp.tba.common;

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
public class EmptyCommentTest implements TBATestCase {

    public EmptyCommentTest() {
    }

    @Override
    public String getCLUScript() {
        
        return 
                "//\n" +
                "?a = 4;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 0)));
        assertEquals(4,outputs.get(new MultivectorComponent("a", 0)),0.01);
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
