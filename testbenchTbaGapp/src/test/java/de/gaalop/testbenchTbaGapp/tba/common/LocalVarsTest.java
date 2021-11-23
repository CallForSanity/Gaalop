package de.gaalop.testbenchTbaGapp.tba.common;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Christian Steinmetz, 2019
 */
public class LocalVarsTest implements TBATestCase {

    public LocalVarsTest() {
    }

    @Override
    public String getCLUScript() {
        
        return 
                "myFunc = {\n" +
                "z = _P(1);\n" + 
                "z+_P(2)+_P(3)\n" +
                "}\n" +
                "\n" +
                "?a = myFunc(1,2,3);\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("a", 0)));
        assertEquals(6,outputs.get(new MultivectorComponent("a", 0)),0.01);
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
