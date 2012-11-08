package de.gaalop.testbenchTbaGapp.tbaNew.negative;

import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tbaNew.framework.TBATestCase;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class ControlFlowTest implements TBATestCase {

    @Override
    public String getCLUScript() {
        return    "a = 1\n;"
                + "c = 4\n;"
                + "if (a == t) {\n"
                + "  a = 2\n;"
                + "} else {\n"
                + "  a = 3\n;"
                + "}\n"
                + "b = a + 1\n;"
                + "?a;\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        
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
