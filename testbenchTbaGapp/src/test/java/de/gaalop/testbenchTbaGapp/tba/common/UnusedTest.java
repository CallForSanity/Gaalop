package de.gaalop.testbenchTbaGapp.tba.common;

import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class UnusedTest implements TBATestCase {

    @Override
    public String getCLUScript() {
        return "rotor = arw + arx * e2 ^ e3 + ary * e3 ^ e1 + arz * e1 ^ e2;\n"
                + "translator = 1 - (0.5 * lpx * e1 ^ einf + 0.5 * lpy * e2 ^ einf + 0.5 * lpz * e3 ^ einf);\n"
                + "?Din = translator*rotor;\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertEquals(8, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        HashMap<Variable, Double> result = new HashMap<Variable, Double>();
        result.put(new Variable("arw"), 1d);
        result.put(new Variable("arx"), 2d);
        result.put(new Variable("ary"), 3d);
        result.put(new Variable("arz"), 4d);
        result.put(new Variable("lpx"), 4d);
        result.put(new Variable("lpy"), 1d);
        result.put(new Variable("lpz"), 2d);
        return result;
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }

}
