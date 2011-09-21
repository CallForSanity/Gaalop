package de.gaalop.gapp;

import de.gaalop.gapp.executer.Executer;
import de.gaalop.gapp.executer.MultivectorWithValues;
import de.gaalop.tba.UseAlgebra;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * Defines a test program for calculating the radius and center point of a circle,
 * which is described by three 2d points
 * @author Christian Steinmetz
 */
public class Circle implements GAPPTestable {

    // The three points which describes the circle
    private Point p1 = new Point(2,3);
    private Point p2 = new Point(5,6);
    private Point p3 = new Point(8,12);

    @Override
    public String getSource() {
        return
            "DefVarsN3();"+"\n"+
            ":IPNS;"+"\n"+

            "//#pragma output m$1"+"\n"+
            "//#pragma output m$2"+"\n"+
            "//#pragma output m$4"+"\n"+
            "//#pragma output m$5"+"\n"+
            "//#pragma output r$0"+"\n"+

            "\n"+
            "?v1 = x1*e1+y1*e2;"+"\n"+
            "?v2 = x2*e1+y2*e2;"+"\n"+
            "?v3 = x3*e1+y3*e2;"+"\n"+

            "?p1 = v1 + 0.5*v1*v1*einf + e0;"+"\n"+
            "?p2 = v2 + 0.5*v2*v2*einf + e0;"+"\n"+
            "?p3 = v3 + 0.5*v3*v3*einf + e0;"+"\n"+

            "?c = *(p1^p2^p3);"+"\n"+

            "?ma = c*einf*c;"+"\n"+
            "?m = -ma/(ma.einf);"+"\n"+
            "?r = sqrt(abs((c.c)/((einf.c)*(einf.c))));"+"\n"
            ;
    }

    @Override
    public HashMap<String, Float> getInputs() {
            HashMap<String, Float> inputValues = new HashMap<String, Float>();
            inputValues.put("x1", new Float(p1.x));
            inputValues.put("y1", new Float(p1.y));
            inputValues.put("x2", new Float(p2.x));
            inputValues.put("y2", new Float(p2.y));
            inputValues.put("x3", new Float(p3.x));
            inputValues.put("y3", new Float(p3.y));
            return inputValues;
    }



    @Override
    public void testOutput(Executer executer) {

        MultivectorWithValues valM = executer.getValue("m");
        MultivectorWithValues valR = executer.getValue("r");

        assertNotNull(valM);
        assertNotNull(valR);

        Point2D.Float m = new Point2D.Float(valM.getEntry(1),valM.getEntry(2));
        float r = valR.getEntry(0);

        assertEquals(p1.distance(m),r,10E-4);
        assertEquals(p2.distance(m),r,10E-4);
        assertEquals(p3.distance(m),r,10E-4);

        for (int i=1;i<32;i++)
            assertEquals(0, valR.getEntry(i),10E-04);

        // m is a (normalized) point!
        assertEquals(0, valM.getEntry(0),10E-04); //1
        assertEquals(0, valM.getEntry(3),10E-04); //e3
        assertEquals(0.5*m.distanceSq(new Point2D.Float(0,0)), valM.getEntry(4),10E-04); //einf
        assertEquals(1, valM.getEntry(5),10E-04); //e0

        for (int i=6;i<32;i++)
            assertEquals(0, valM.getEntry(i),10E-04);
        
    }

    @Override
    public UseAlgebra getUsedAlgebra() {
        return UseAlgebra.get5dConformalGA();
    }



}
