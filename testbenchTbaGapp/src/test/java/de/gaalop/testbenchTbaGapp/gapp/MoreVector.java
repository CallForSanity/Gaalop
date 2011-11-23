package de.gaalop.testbenchTbaGapp.gapp;

import de.gaalop.gapp.executer.Executer;
import de.gaalop.tba.UseAlgebra;
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
    public HashMap<String, Float> getInputs() {
        HashMap<String, Float> result = new HashMap<String, Float>();
        result.put("a", 2.0f);
        result.put("b", 1.0f);
        result.put("c", 3.0f);
        result.put("g", 4.0f);
        result.put("f", 2.0f);
        return result;
    }

    @Override
    public void testOutput(Executer executer) {
        assertEquals(48, executer.getValue("d").getEntry(0),10E-4);
    }

}
