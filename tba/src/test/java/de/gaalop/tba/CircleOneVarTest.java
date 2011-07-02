package de.gaalop.tba;

import org.junit.Ignore;
import java.awt.Point;
import java.util.LinkedList;

/**
 * Implements a circle of three points test with one variable
 * @author christian
 */
@Ignore
public class CircleOneVarTest extends GenericCircleTest {

    boolean[] commented;

    private int n;

    public CircleOneVarTest(Point p1, Point p2, Point p3, boolean[] commented, int n) {
        super(p1,p2,p3);
        this.commented = commented;
        this.n = n;
    }

    @Override
    protected String constantDefinition() {
        return
            ((commented[0]) ? "// " : "") + "x1 = "+p1.x+";"+"\n"+
            ((commented[1]) ? "// " : "") + "y1 = "+p1.y+";"+"\n"+

            ((commented[2]) ? "// " : "") + "x2 = "+p2.x+";"+"\n"+
            ((commented[3]) ? "// " : "") + "y2 = "+p2.y+";"+"\n"+

            ((commented[4]) ? "// " : "") + "x3 = "+p3.x+";"+"\n"+
            ((commented[5]) ? "// " : "") + "y3 = "+p3.y+";"+"\n"
            ;
    }

     private int currentI;
     private float currentValue;

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();



        for (int i=-n/2;i<=n/2;i++) {
            currentValue = i*1;
            currentI = i+n/2;

            result.add(new InputOutput() {

                int currentIs = currentI;
                float currentValues = currentValue;

                @Override
                public LinkedList<VariableValue> getInputs() {
                    LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                    for (int i=0;i<6;i++)
                        if (commented[i])
                        {
                            String varName = ((i%2==0) ? "x" : "y" ) + (i/2+1)+"_0";
                            result.add(new VariableValue(varName, currentValues));
                        }


                    return result;
                }

                @Override
                public String getCheckOutputsCode() {

                    // TODO check if points aren't collinear

                    return 
                    "// check containing r_0,m_1,m_2\n"+
                    "assertTrue(outputs.containsKey(\"r_0\"));\n"+
                    "assertTrue(outputs.containsKey(\"m_1\"));\n"+
                    "assertTrue(outputs.containsKey(\"m_2\"));\n"+

                    "float r_0 = outputs.get(\"r_0\");\n"+
                    "float m_x = outputs.get(\"m_1\");\n"+
                    "float m_y = outputs.get(\"m_2\");\n"+


                    "// check radius (should be equal to distance from m to p1,p2,p3)\n"+
                    "assertEquals("+getDistance("m_x","m_y", getCoordinate(0, currentValues)+"", getCoordinate(1, currentValues)+"")+",r_0,"+EPSILON+");\n"+
                    "assertEquals("+getDistance("m_x", "m_y",getCoordinate(2, currentValues)+"", getCoordinate(3, currentValues)+"")+",r_0,"+EPSILON+");\n"+
                    "assertEquals("+getDistance("m_x", "m_y",getCoordinate(4, currentValues)+"", getCoordinate(5, currentValues)+"")+",r_0,"+EPSILON+");\n"+

                    "// check number of outputs\n"+
                    "assertEquals(3, outputs.size());\n"
                            ;


                }

                @Override
                public int getNo() {
                    return currentIs;
                }



            });

        }


        return result;
    }

    /**
     * Returns the currentValue if no is commented, otherwise the coordinate of the related point
     * @param no The number of the coordinate
     * @param currentValue The currentValue of the successive incrementation
     * @return The correct coordinate
     */
    private float getCoordinate(int no, float currentValue) {
        if (commented[no]) {
            return currentValue;
        } else {
            switch (no) {
                case 0:
                    return p1.x;
                case 1:
                    return p1.y;
                case 2:
                    return p2.x;
                case 3:
                    return p2.y;
                case 4:
                    return p3.x;
                case 5:
                    return p3.y; 
            }
            System.err.println("Programmierfehler");
            return currentValue;
        }
    }


}
