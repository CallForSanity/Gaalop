package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class NormalBaseTest implements TBATestCase {

    @Override
    public String getCLUScript() {
        return  "P1y=0; P1z=1; \n"
                + "P2x=0; P2y=1; P2z=0;\n"
                + "P3x=1; P3y=0; P3z=0;\n"
                + "P4x=0; P4y=-1; P4z=0;\n"
                + "\n"
                + "?P1 =  createPoint(P1x, P1y, P1z);\n"
                + "P2 =  createPoint(P2x, P2y, P2z);\n"
                + "P3 =  createPoint(P3x, P3y, P3z);\n"
                + "P4 =  createPoint(P4x, P4y, P4z);\n"
                + "\n"
                + "S= *(P1^P2^P3^P4);\n"
                + "?m = S*einf*S;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("m", 3)));
        assertEquals("m.z must be zero", 0.0d, outputs.get(new MultivectorComponent("m", 3)), 10E-7);
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        HashMap<Variable, Double> result = new HashMap<Variable, Double>();
        result.put(new Variable("P1x"), 0.0d);
        return result;
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }

}
