package de.gaalop.testbenchTbaGapp.tbaNew.framework;

import de.gaalop.dfg.Variable;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public interface TBATestCase {
    
    public String getCLUScript();
    
    public void testOutputs(HashMap<Variable, Double> variables);

    public HashMap<Variable, Double> getInputValues();
    
    public String getAlgebraName();

}
