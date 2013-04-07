package de.gaalop.testbenchTbaGapp.tba.gps;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.HashMap;
import org.junit.Ignore;

import static org.junit.Assert.*;

/**
 * Implements a GPS test with only variables
 * @author Christian Steinmetz
 */
@Ignore
public class GPSOnlyVarsTest extends GenericGPSTest {

    public GPSOnlyVarsTest(Point3D sat1, Point3D sat2, Point3D sat3, double d1, double d2, double d3) {
        super(sat1, sat2, sat3, d1, d2, d3);
    }

    @Override
    protected String constantDefinition() {
        return "\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("rc1N",1)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc1N",2)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc1N",3)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc2N",1)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc2N",2)));
        assertTrue(outputs.containsKey(new MultivectorComponent("rc2N",3)));
        assertTrue(outputs.containsKey(new MultivectorComponent("z11",0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("z12",0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("z21",0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("z22",0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("z31",0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("z32",0)));
        double z11 = outputs.get(new MultivectorComponent("z11",0));
        double z12 = outputs.get(new MultivectorComponent("z12",0));
        double z21 = outputs.get(new MultivectorComponent("z21",0));
        double z22 = outputs.get(new MultivectorComponent("z22",0));
        double z31 = outputs.get(new MultivectorComponent("z31",0));
        double z32 = outputs.get(new MultivectorComponent("z32",0));
        assertEquals(0,z11,EPSILON);
        assertEquals(0,z12,EPSILON);
        assertEquals(0,z21,EPSILON);
        assertEquals(0,z22,EPSILON);
        assertEquals(0,z31,EPSILON);
        assertEquals(0,z32,EPSILON);
        assertEquals(12, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        HashMap<Variable, Double> result = new HashMap<Variable, Double>();
        result.put(new Variable("sat1x"), sat1.x);
        result.put(new Variable("sat1y"), sat1.y);
        result.put(new Variable("sat1z"), sat1.z);
        result.put(new Variable("sat2x"), sat2.x);
        result.put(new Variable("sat2y"), sat2.y);
        result.put(new Variable("sat2z"), sat2.z);
        result.put(new Variable("sat3x"), sat3.x);
        result.put(new Variable("sat3y"), sat3.y);
        result.put(new Variable("sat3z"), sat3.z);
        result.put(new Variable("d1"), d1);
        result.put(new Variable("d2"), d2);
        result.put(new Variable("d3"), d3);
        return result;
    }
}
