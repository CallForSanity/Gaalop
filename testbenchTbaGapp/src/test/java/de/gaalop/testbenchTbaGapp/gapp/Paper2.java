package de.gaalop.testbenchTbaGapp.gapp;

import de.gaalop.gapp.executer.Executer;
import de.gaalop.gapp.executer.MultivectorWithValues;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * Tests with optimized calculation from the GAPP Paper
 * @author Christian Steinmetz
 */
public class Paper2 implements GAPPTestable {

    private double a1 = 3;
    private double a2 = 5;
    private double a3 = 2;
    private double b1 = 4;
    private double b2 = 6;
    private double b3 = 7;

    @Override
    public String getSource() {
        return "a=a1*e1+a2*e2+a3*e3;" + "\n"
                + "b=b1*e1+b2*e2+b3*e3;" + "\n"
                + //"?f=a^(a+a*b);"+"\n";
                "?c =a*b;" + "\n"
                + "d =a+c;" + "\n"
                + "?f =a^d;" + "\n";
    }

    @Override
    public HashMap<String, Double> getInputs() {
        HashMap<String, Double> inputValues = new HashMap<String, Double>();
        inputValues.put("a1", new Double(a1));
        inputValues.put("a2", new Double(a2));
        inputValues.put("a3", new Double(a3));
        inputValues.put("b1", new Double(b1));
        inputValues.put("b2", new Double(b2));
        inputValues.put("b3", new Double(b3));
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

}
