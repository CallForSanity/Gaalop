package de.gaalop.testbenchTbaGapp.tbaNew.common;

import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tbaNew.framework.TBATestCase;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class OutputCountTest implements TBATestCase {

    @Override
    public String getCLUScript() {
        return "a = 0;\n"+
                "?a;";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertEquals(0, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<Variable, Double>();
    }

    @Override
    public String getAlgebraName() {
        return "5d";
    }

}
