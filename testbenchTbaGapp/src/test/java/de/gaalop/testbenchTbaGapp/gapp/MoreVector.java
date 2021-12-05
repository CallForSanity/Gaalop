package de.gaalop.testbenchTbaGapp.gapp;

import de.gaalop.gapp.executer.Executer;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class MoreVector implements GAPPTestable {

    @Override
    public String getSource() {
        return "d=a*(b+c)*(g+f);\n"+
                "?d;\n";
    }

    @Override
    public HashMap<String, Double> getInputs() {
        HashMap<String, Double> result = new HashMap<String, Double>();
        result.put("a", 2.0d);
        result.put("b", 1.0d);
        result.put("c", 3.0d);
        result.put("g", 4.0d);
        result.put("f", 2.0d);
        return result;
    }

    @Override
    public void testOutput(Executer executer) {
        assertEquals(48, executer.getValue("d").getEntry(0),10E-4);
    }

}
