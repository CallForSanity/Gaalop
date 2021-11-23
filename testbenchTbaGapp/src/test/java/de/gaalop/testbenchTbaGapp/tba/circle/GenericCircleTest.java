package de.gaalop.testbenchTbaGapp.tba.circle;

import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.awt.Point;

/**
 * Implements a generic circle of three points test
 * @author Christian Steinmetz
 */
public abstract class GenericCircleTest implements TBATestCase {

    protected static final double EPSILON = 10E-4;

    protected abstract String constantDefinition();
    
    protected Point p1;
    protected Point p2;
    protected Point p3;

    public GenericCircleTest(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public String getCLUScript() {
        return "//#pragma output m e1 e2" + "\n"
                + "//#pragma output r 1.0" + "\n"
                + constantDefinition() + "\n"
                + "v1 = x1*e1+y1*e2;" + "\n"
                + "v2 = x2*e1+y2*e2;" + "\n"
                + "v3 = x3*e1+y3*e2;" + "\n"
                + "p1 = v1 + 0.5*v1*v1*einf + e0;" + "\n"
                + "p2 = v2 + 0.5*v2*v2*einf + e0;" + "\n"
                + "p3 = v3 + 0.5*v3*v3*einf + e0;" + "\n"
                + "c = *(p1^p2^p3);" + "\n"
                + "mtmp = c*einf*c;" + "\n"
                + "?m = -mtmp/(mtmp.einf);" + "\n"
                + "?r = sqrt(abs(c.c/((einf.c)*(einf.c))));" + "\n";
    }

    /**
     * Returns the distance of two points
     * @param p1x The x-coordinate from the first point
     * @param p1y The y-coordinate from the first point
     * @param p2x The x-coordinate from the second point
     * @param p2y The y-coordinate from the second point
     * @return The distance
     */
    protected double getDistance(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt((p2x-p1x)*(p2x-p1x) + (p2y-p1y)*(p2y-p1y));
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }

}
