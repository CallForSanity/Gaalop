package de.gaalop.testbenchTbaGapp.tba.circle;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import org.junit.Ignore;
import java.awt.Point;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Implements a circle of three points test with no variables
 * @author Christian Steinmetz
 */
@Ignore
public class CircleNoVarsTest extends GenericCircleTest {

    public CircleNoVarsTest(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected String constantDefinition() {
        return "x1 = " + p1.x + ";" + "\n"
                + "y1 = " + p1.y + ";" + "\n"
                + "x2 = " + p2.x + ";" + "\n"
                + "y2 = " + p2.y + ";" + "\n"
                + "x3 = " + p3.x + ";" + "\n"
                + "y3 = " + p3.y + ";" + "\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {

        // check containing r$0,m$1,m$2
        assertTrue(outputs.containsKey(new MultivectorComponent("r", 0)));   
        assertTrue(outputs.containsKey(new MultivectorComponent("m", 1)));   
        assertTrue(outputs.containsKey(new MultivectorComponent("m", 2)));  
        double r$0 = outputs.get(new MultivectorComponent("r", 0));
        double m$1 = outputs.get(new MultivectorComponent("m", 1));
        double m$2 = outputs.get(new MultivectorComponent("m", 2));
        
        // check radius (should be equal to distance from m to p1,p2,p3)
        assertEquals(p1.distance(m$1, m$2),r$0, EPSILON);
        assertEquals(p2.distance(m$1, m$2),r$0, EPSILON);
        assertEquals(p3.distance(m$1, m$2),r$0, EPSILON);
        assertEquals(3, outputs.size());
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<Variable, Double>(); //no inputs
    }
}
