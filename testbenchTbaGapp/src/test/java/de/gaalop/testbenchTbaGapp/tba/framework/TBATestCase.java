package de.gaalop.testbenchTbaGapp.tba.framework;

import de.gaalop.dfg.Variable;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public interface TBATestCase {
    
    public String getCLUScript();
    
    public void testOutputs(HashMap<Variable, Double> outputs);

    public HashMap<Variable, Double> getInputValues();
    
    public String getAlgebraName();

}
