package de.gaalop.testbenchTbaGapp.tbaNew.circle;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.InputOutput;
import de.gaalop.testbenchTbaGapp.tba.VariableValue;
import org.junit.Ignore;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;

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
    public void testOutputs(HashMap<Variable, Double> variables) {
/*

                        + "double r$0 = outputs.get(\"r$0\");\n"
                        + "double m$x = outputs.get(\"m$1\");\n"
                        + "double m$y = outputs.get(\"m$2\");\n"
                        + "// check radius (should be equal to distance from m to p1,p2,p3)\n"
                        + "assertEquals(" + getDistance("m$x", "m$y", p1.x + "", p1.y + "") + ",r$0," + EPSILON + ");\n"
                        + "assertEquals(" + getDistance("m$x", "m$y", p2.x + "", p2.y + "") + ",r$0," + EPSILON + ");\n"
                        + "assertEquals(" + getDistance("m$x", "m$y", p3.x + "", p3.y + "") + ",r$0," + EPSILON + ");\n"
                        + "// check number of outputs\n"
                        + "assertEquals(3, outputs.size());\n";
 */
            
        // check containing r$0,m$1,m$2
        assertTrue(variables.containsKey(new MultivectorComponent("r", 0)));   
        assertTrue(variables.containsKey(new MultivectorComponent("m", 1)));   
        assertTrue(variables.containsKey(new MultivectorComponent("m", 2)));  
        double r$0 = variables.get(new MultivectorComponent("r", 0));
        double m$1 = variables.get(new MultivectorComponent("m", 1));
        double m$2 = variables.get(new MultivectorComponent("m", 2));
        
        // check radius (should be equal to distance from m to p1,p2,p3)
        assertEquals(p1.distance(m$1, m$2),r$0, EPSILON);

    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<Variable, Double>(); //no inputs
    }
}
