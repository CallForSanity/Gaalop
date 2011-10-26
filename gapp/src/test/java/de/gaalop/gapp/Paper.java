package de.gaalop.gapp;

import de.gaalop.gapp.executer.Executer;
import de.gaalop.gapp.executer.MultivectorWithValues;
import de.gaalop.tba.UseAlgebra;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * Tests with unoptimized calculation from the GAPP Paper
 * @author Christian Steinmetz
 */
public class Paper implements GAPPTestable {

    private float a1 = 3;
    private float a2 = 5;
    private float a3 = 2;
    private float b1 = 4;
    private float b2 = 6;
    private float b3 = 7;

    @Override
    public String getSource() {
        return "DefVarsN3();" + "\n"
                + "\n"
                + "a=a1*e1+a2*e2+a3*e3;" + "\n"
                + "b=b1*e1+b2*e2+b3*e3;" + "\n"
                + "?f=a^(a+a*b);" + "\n";
    }

    @Override
    public HashMap<String, Float> getInputs() {
        HashMap<String, Float> inputValues = new HashMap<String, Float>();
        inputValues.put("a1", new Float(a1));
        inputValues.put("a2", new Float(a2));
        inputValues.put("a3", new Float(a3));
        inputValues.put("b1", new Float(b1));
        inputValues.put("b2", new Float(b2));
        inputValues.put("b3", new Float(b3));
        return inputValues;
    }

    @Override
    public void testOutput(Executer executer) {
        MultivectorWithValues valF = executer.getValue("f");
        assertEquals(0, valF.getEntry(0), 10E-04);

        assertEquals(168, valF.getEntry(1), 10E-04);
        assertEquals(280, valF.getEntry(2), 10E-04);
        assertEquals(112, valF.getEntry(3), 10E-04);

        for (int i = 4; i < 32; i++) {
            assertEquals(0, valF.getEntry(i), 10E-04);
        }

    }

    @Override
    public UseAlgebra getUsedAlgebra() {
        return UseAlgebra.get5dConformalGA();
    }
}
