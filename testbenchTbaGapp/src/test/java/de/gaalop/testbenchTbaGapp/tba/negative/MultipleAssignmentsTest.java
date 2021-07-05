package de.gaalop.testbenchTbaGapp.tba.negative;

import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class MultipleAssignmentsTest implements TBATestCase {

    @Override
    public String getCLUScript() {
        return
                "b = a^e2;" + "\n"
                + "b = a^e3;" + "\n"
                + "?d = b;";
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
        return "cga";
    }

}
