package de.gaalop.testbenchTbaGapp.tba.circle;

import de.gaalop.testbenchTbaGapp.tba.InputOutput;
import de.gaalop.testbenchTbaGapp.tba.VariableValue;
import java.awt.Point;
import java.util.LinkedList;
import org.junit.Ignore;

/**
 * Implements a circle of three points test with only variables
 * @author Christian Steinmetz
 */
@Ignore
public class CircleOnlyVarsTest extends GenericCircleTest {

    public CircleOnlyVarsTest(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected String constantDefinition() {
        return "\n";
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();

        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                result.add(new VariableValue("x1$0", p1.x));
                result.add(new VariableValue("y1$0", p1.y));
                result.add(new VariableValue("x2$0", p2.x));
                result.add(new VariableValue("y2$0", p2.y));
                result.add(new VariableValue("x3$0", p3.x));
                result.add(new VariableValue("y3$0", p3.y));
                return result;
            }

            @Override
            public String getCheckOutputsCode() {
                return "// check containing r$0,m$1,m$2\n"
                        + "assertTrue(outputs.containsKey(\"r$0\"));\n"
                        + "assertTrue(outputs.containsKey(\"m$1\"));\n"
                        + "assertTrue(outputs.containsKey(\"m$2\"));\n"
                        + "double r$0 = outputs.get(\"r$0\");\n"
                        + "double m$x = outputs.get(\"m$1\");\n"
                        + "double m$y = outputs.get(\"m$2\");\n"
                        + "// check radius (should be equal to distance from m to p1,p2,p3)\n"
                        + "assertEquals(" + getDistance("m$x", "m$y", p1.x + "", p1.y + "") + ",r$0," + EPSILON + ");\n"
                        + "assertEquals(" + getDistance("m$x", "m$y", p2.x + "", p2.y + "") + ",r$0," + EPSILON + ");\n"
                        + "assertEquals(" + getDistance("m$x", "m$y", p3.x + "", p3.y + "") + ",r$0," + EPSILON + ");\n"
                        + "// check number of outputs\n"
                        + "assertEquals(3, outputs.size());\n";


            }

            @Override
            public int getNo() {
                return 0;
            }
        });

        return result;
    }
}
