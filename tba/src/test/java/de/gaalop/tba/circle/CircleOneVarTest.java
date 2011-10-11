package de.gaalop.tba.circle;

import de.gaalop.tba.InputOutput;
import de.gaalop.tba.VariableValue;
import org.junit.Ignore;
import java.awt.Point;
import java.util.LinkedList;

/**
 * Implements a circle of three points test with one variable
 * @author Christian Steinmetz
 */
@Ignore
public class CircleOneVarTest extends GenericCircleTest {

    private boolean[] commented;
    private int n;

    public CircleOneVarTest(Point p1, Point p2, Point p3, boolean[] commented, int n) {
        super(p1, p2, p3);
        this.commented = commented;
        this.n = n;
    }

    @Override
    protected String constantDefinition() {
        return ((commented[0]) ? "// " : "") + "x1 = " + p1.x + ";" + "\n"
                + ((commented[1]) ? "// " : "") + "y1 = " + p1.y + ";" + "\n"
                + ((commented[2]) ? "// " : "") + "x2 = " + p2.x + ";" + "\n"
                + ((commented[3]) ? "// " : "") + "y2 = " + p2.y + ";" + "\n"
                + ((commented[4]) ? "// " : "") + "x3 = " + p3.x + ";" + "\n"
                + ((commented[5]) ? "// " : "") + "y3 = " + p3.y + ";" + "\n";
    }
    private int currentI;
    private float currentValue;

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();



        for (int i = -n / 2; i <= n / 2; i++) {
            currentValue = i * 1;
            currentI = i + n / 2;

            result.add(new InputOutput() {

                int currentIs = currentI;
                float currentValues = currentValue;

                @Override
                public LinkedList<VariableValue> getInputs() {
                    LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                    for (int i = 0; i < 6; i++) {
                        if (commented[i]) {
                            String varName = ((i % 2 == 0) ? "x" : "y") + (i / 2 + 1) + "$0";
                            result.add(new VariableValue(varName, currentValues));
                        }
                    }


                    return result;
                }

                @Override
                public String getCheckOutputsCode() {

                    // assume that points aren't collinear

                    return "// check containing r$0,m$1,m$2\n"
                            + "assertTrue(outputs.containsKey(\"r$0\"));\n"
                            + "assertTrue(outputs.containsKey(\"m$1\"));\n"
                            + "assertTrue(outputs.containsKey(\"m$2\"));\n"
                            + "float r$0 = outputs.get(\"r$0\");\n"
                            + "float m$x = outputs.get(\"m$1\");\n"
                            + "float m$y = outputs.get(\"m$2\");\n"
                            + "// check radius (should be equal to distance from m to p1,p2,p3)\n"
                            + "assertEquals(" + getDistance("m$x", "m$y", getCoordinate(0, currentValues) + "", getCoordinate(1, currentValues) + "") + ",r$0," + EPSILON + ");\n"
                            + "assertEquals(" + getDistance("m$x", "m$y", getCoordinate(2, currentValues) + "", getCoordinate(3, currentValues) + "") + ",r$0," + EPSILON + ");\n"
                            + "assertEquals(" + getDistance("m$x", "m$y", getCoordinate(4, currentValues) + "", getCoordinate(5, currentValues) + "") + ",r$0," + EPSILON + ");\n"
                            + "// check number of outputs\n"
                            + "assertEquals(3, outputs.size());\n";


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
            System.err.println("Programming error");
            return currentValue;
        }
    }
}
