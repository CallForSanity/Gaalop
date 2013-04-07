package de.gaalop.testbenchTbaGapp.tba.gps;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.HashMap;
import org.junit.Ignore;

import static org.junit.Assert.*;

/**
 * Implements a GPS test with no variables
 * @author Christian Steinmetz
 */
@Ignore
public class GPSNoVarsTest extends GenericGPSTest {

    public GPSNoVarsTest(Point3D sat1, Point3D sat2, Point3D sat3, double d1, double d2, double d3) {
        super(sat1, sat2, sat3, d1, d2, d3);
    }

    @Override
    protected String constantDefinition() {
        return "sat1x = " + sat1.x + ";" + "\n"
                + "sat1y = " + sat1.y + ";" + "\n"
                + "sat1z = " + sat1.z + ";" + "\n"
                + "sat2x = " + sat2.x + ";" + "\n"
                + "sat2y = " + sat2.y + ";" + "\n"
                + "sat2z = " + sat2.z + ";" + "\n"
                + "sat3x = " + sat3.x + ";" + "\n"
                + "sat3y = " + sat3.y + ";" + "\n"
                + "sat3z = " + sat3.z + ";" + "\n"
                + "d1 = " + d1 + ";" + "\n"
                + "d2 = " + d2 + ";" + "\n"
                + "d3 = " + d3 + ";" + "\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("rc1N",1)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc1N",2)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc1N",3)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc2N",1)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc2N",2)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc2N",3)));
        assertEquals(6, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<Variable, Double>();
    }
}
