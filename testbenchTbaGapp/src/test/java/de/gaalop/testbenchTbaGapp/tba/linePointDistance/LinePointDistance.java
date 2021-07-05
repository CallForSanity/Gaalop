package de.gaalop.testbenchTbaGapp.tba.linePointDistance;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.gps.Point3D;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Implements a test for TBA which test the distance between a point and a line in 3d euclidean space
 * @author Christian Steinmetz
 */
public class LinePointDistance implements TBATestCase {

    private Point3D p1;
    private Point3D p2;
    private Point3D pTest;

    public LinePointDistance(Point3D p1, Point3D p2, Point3D pTest) {
        this.p1 = p1;
        this.p2 = p2;
        this.pTest = pTest;
    }
    
    @Override
    public String getCLUScript() {
        return    "v1 = VecN3(p1x,p1y,p1z);\n"
                + "v2 = VecN3(p2x,p2y,p2z);\n"
                + "vTst = VecN3(pTstx,pTsty,pTstz);\n"
                + "pi = 3.141592;\n"
                + "?L = *(v1^v2^einf);\n"
                + "La = L/abs(L);\n"
                + "R = cos(pi/4) - La*sin(pi/4);\n"
                + "?V = R*vTst*(~R);\n"
                + "P = *(v1^v2^V^einf);\n"
                + "Pa = P/abs(P);\n"
                + "?abstand = abs(Pa.vTst);\n"
                + "?nor = Pa + (Pa.e0)*einf;\n";
    }

    @Override
    public void testOutputs(HashMap<Variable, Double> outputs) {
        assertTrue(outputs.containsKey(new MultivectorComponent("abstand", 0)));
        assertTrue(outputs.containsKey(new MultivectorComponent("nor", 1)));
        assertTrue(outputs.containsKey(new MultivectorComponent("nor", 2)));
        assertTrue(outputs.containsKey(new MultivectorComponent("nor", 3)));
        //assertTrue(outputs.containsKey(new MultivectorComponent("", )));
        double abstand = outputs.get(new MultivectorComponent("abstand", 0));
        Vec3D nor = new Vec3D(
                outputs.get(new MultivectorComponent("nor", 1)),
                outputs.get(new MultivectorComponent("nor", 2)),
                outputs.get(new MultivectorComponent("nor", 3))
                );
        nor.normalize();
        nor.scalarMultiplication(abstand);
        Point3D pBase = nor.applyToPoint(new Point3D(pTest.x, pTest.y, pTest.z));
        //pBase must lie on plane and on line
        //test if on line, assume that dx,dy,dz not zero
        double tx = (pBase.x-p1.x)/((p2.x - p1.x));
        double ty = (pBase.y-p1.y)/((p2.y - p1.y));
        double tz = (pBase.z-p1.z)/((p2.z - p1.z));
        // since the normal is unique except of a sign:
        if (Math.abs(tx-ty)>0.001 || Math.abs(tz-ty)>0.001) {
            nor = new Vec3D(
                outputs.get(new MultivectorComponent("nor", 1)),
                outputs.get(new MultivectorComponent("nor", 2)),
                outputs.get(new MultivectorComponent("nor", 3))
                );
            nor.normalize();
            nor.scalarMultiplication(-abstand);
            pBase = nor.applyToPoint(new Point3D(pTest.x, pTest.y, pTest.z));
            //pBase must lie on plane and on line
            //test if on line, assume that dx,dy,dz not zero
            tx = (pBase.x-p1.x)/((p2.x - p1.x));
            ty = (pBase.y-p1.y)/((p2.y - p1.y));
            tz = (pBase.z-p1.z)/((p2.z - p1.z));
        }
        assertEquals(tx,ty,0.001);
        assertEquals(ty,tz,0.001);
        //test if on plane
        Vec3D r = new Vec3D((p2.x - p1.x), (p2.y - p1.y), (p2.z - p1.z));
        r.normalize();
        Vec3D xmpTest = new Vec3D(pBase.x-pTest.x, pBase.y-pTest.y, pBase.z-pTest.z);
        double dp = xmpTest.dotProduct(r);
        assertEquals(0,dp,0.001);
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        HashMap<Variable, Double> result = new HashMap<Variable, Double>();
        result.put(new Variable("p1x"), p1.x);
        result.put(new Variable("p1y"), p1.y);
        result.put(new Variable("p1z"), p1.z);
        result.put(new Variable("p2x"), p2.x);
        result.put(new Variable("p2y"), p2.y);
        result.put(new Variable("p2z"), p2.z);
        result.put(new Variable("pTstx"), pTest.x);
        result.put(new Variable("pTsty"), pTest.y);
        result.put(new Variable("pTstz"), pTest.z);
        return result;
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }

}
