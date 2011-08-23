package de.gaalop.tba.circle;

import de.gaalop.tba.InputOutput;
import de.gaalop.tba.VariableValue;
import org.junit.Ignore;
import java.awt.Point;
import java.util.LinkedList;

/**
 * Implements a circle of three points test with no variables
 * @author christian
 */
@Ignore
public class CircleNoVarsTest extends GenericCircleTest {

    public CircleNoVarsTest(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected String constantDefinition() {
        return
            "x1 = "+p1.x+";"+"\n"+
            "y1 = "+p1.y+";"+"\n"+

            "x2 = "+p2.x+";"+"\n"+
            "y2 = "+p2.y+";"+"\n"+

            "x3 = "+p3.x+";"+"\n"+
            "y3 = "+p3.y+";"+"\n"
            ;
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();

        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                return new LinkedList<VariableValue>(); //no inputs
            }

            @Override
            public String getCheckOutputsCode() {
                return

                "// check containing r$0,m$1,m$2\n"+
                "assertTrue(outputs.containsKey(\"r$0\"));\n"+
                "assertTrue(outputs.containsKey(\"m$1\"));\n"+
                "assertTrue(outputs.containsKey(\"m$2\"));\n"+

                "float r$0 = outputs.get(\"r$0\");\n"+
                "float m$x = outputs.get(\"m$1\");\n"+
                "float m$y = outputs.get(\"m$2\");\n"+
                

                "// check radius (should be equal to distance from m to p1,p2,p3)\n"+
                "assertEquals("+getDistance("m$x", "m$y",p1.x+"",p1.y+"")+",r$0,"+EPSILON+");\n"+
                "assertEquals("+getDistance("m$x", "m$y",p2.x+"",p2.y+"")+",r$0,"+EPSILON+");\n"+
                "assertEquals("+getDistance("m$x", "m$y",p3.x+"",p3.y+"")+",r$0,"+EPSILON+");\n"+

                "// check number of outputs\n"+
                "assertEquals(3, outputs.size());\n"

                ;


            }

            @Override
            public int getNo() {
                return 0;
            }

        });


        return result;
    }



}
