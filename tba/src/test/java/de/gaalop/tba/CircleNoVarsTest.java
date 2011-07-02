package de.gaalop.tba;

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

                "// check containing r_0,m_1,m_2\n"+
                "assertTrue(outputs.containsKey(\"r_0\"));\n"+
                "assertTrue(outputs.containsKey(\"m_1\"));\n"+
                "assertTrue(outputs.containsKey(\"m_2\"));\n"+

                "float r_0 = outputs.get(\"r_0\");\n"+
                "float m_x = outputs.get(\"m_1\");\n"+
                "float m_y = outputs.get(\"m_2\");\n"+
                

                "// check radius (should be equal to distance from m to p1,p2,p3)\n"+
                "assertEquals("+getDistance("m_x", "m_y",p1.x+"",p1.y+"")+",r_0,"+EPSILON+");\n"+
                "assertEquals("+getDistance("m_x", "m_y",p2.x+"",p2.y+"")+",r_0,"+EPSILON+");\n"+
                "assertEquals("+getDistance("m_x", "m_y",p3.x+"",p3.y+"")+",r_0,"+EPSILON+");\n"+

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
