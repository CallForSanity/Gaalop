package de.gaalop.gapp;

import de.gaalop.tba.UseAlgebra;
import de.gaalop.gapp.executer.Executer;
import de.gaalop.gapp.executer.MultivectorWithValues;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * Defines a test program for calculating the mathematical positions of a GPS receiver,
 * given the positions of the tree satellites and the distances from each satellite to the
 * GPS receiver.
 * 
 * @author christian
 */
public class GPS implements GAPPTestable {

    //The position of the three satellites
    protected Point3D sat1;
    protected Point3D sat2;
    protected Point3D sat3;

    //The three distances from each satellite to the GPS receiver
    protected float d1;
    protected float d2;
    protected float d3;

    public GPS() {
        this(new Point3D(1,1,1), new Point3D(0,0,1), new Point3D(0,1,0), 0.6f, 0.7f, 0.5f);
    }

    public GPS(Point3D sat1, Point3D sat2, Point3D sat3, float d1, float d2, float d3) {
        this.sat1 = sat1;
        this.sat2 = sat2;
        this.sat3 = sat3;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    @Override
    public String getSource() {
         return

            "//#pragma output rc1N_1"+"\n"+
            "//#pragma output rc1N_2"+"\n"+
            "//#pragma output rc1N_3"+"\n"+
            "//#pragma output rc2N_1"+"\n"+
            "//#pragma output rc2N_2"+"\n"+
            "//#pragma output rc2N_3"+"\n"+
            "//#pragma output z11_0"+"\n"+
            "//#pragma output z12_0"+"\n"+
            "//#pragma output z21_0"+"\n"+
            "//#pragma output z22_0"+"\n"+
            "//#pragma output z31_0"+"\n"+
            "//#pragma output z32_0"+"\n"+

            "sat1 = VecN3(sat1x,sat1y,sat1z);\n"+
            "sat2 = VecN3(sat2x,sat2y,sat2z);\n"+
            "sat3 = VecN3(sat3x,sat3y,sat3z);\n"+
            "// create the three spheres\n"+
            "sph1 = sat1 - 0.5*d1*d1*einf;\n"+
            "sph2 = sat2 - 0.5*d2*d2*einf;\n"+
            "sph3 = sat3 - 0.5*d3*d3*einf;\n"+
            "// calculate the intersection point pair of three spheres\n"+
            "rcPp = sph1^sph2^sph3;\n"+
            "len = sqrt(abs(rcPp.rcPp));\n"+
            "rcPpDual = *rcPp;\n"+
            "nen = einf.rcPpDual;\n"+
            "// get first point\n"+
            "rc1 = (rcPpDual + len) / nen;\n"+
            "?rc1N = - rc1/(rc1.einf);\n"+
            "// get second point\n"+
            "rc2 = (rcPpDual - len) / nen;\n"+
            "?rc2N = - rc2/(rc2.einf);\n"+
            "// the following mv must be zero\n"+
            "?z11 = (sqrt(-2*rc1N.sat1))-d1;\n"+
            "?z12 = (sqrt(-2*rc2N.sat1))-d1;\n"+
            "?z21 = (sqrt(-2*rc1N.sat2))-d2;\n"+
            "?z22 = (sqrt(-2*rc2N.sat2))-d2;\n"+
            "?z31 = (sqrt(-2*rc1N.sat3))-d3;\n"+
            "?z32 = (sqrt(-2*rc2N.sat3))-d3;\n";
    }

    @Override
    public HashMap<String, Float> getInputs() {
            HashMap<String, Float> inputValues = new HashMap<String, Float>();
            inputValues.put("sat1x", new Float(sat1.x));
            inputValues.put("sat1y", new Float(sat1.y));
            inputValues.put("sat1z", new Float(sat1.z));
            inputValues.put("sat2x", new Float(sat2.x));
            inputValues.put("sat2y", new Float(sat2.y));
            inputValues.put("sat2z", new Float(sat2.z));
            inputValues.put("sat3x", new Float(sat3.x));
            inputValues.put("sat3y", new Float(sat3.y));
            inputValues.put("sat3z", new Float(sat3.z));
            inputValues.put("d1", new Float(d1));
            inputValues.put("d2", new Float(d2));
            inputValues.put("d3", new Float(d3));
            return inputValues;
    }



    @Override
    public void testOutput(Executer executer) {
        MultivectorWithValues valrc1N = executer.getValue("rc1N");
        MultivectorWithValues valrc2N = executer.getValue("rc2N");
        MultivectorWithValues valz11  = executer.getValue("z11");
        MultivectorWithValues valz12  = executer.getValue("z12");
        MultivectorWithValues valz21  = executer.getValue("z21");
        MultivectorWithValues valz22  = executer.getValue("z22");
        MultivectorWithValues valz31  = executer.getValue("z31");
        MultivectorWithValues valz32  = executer.getValue("z32");

        assertNotNull(valrc1N);
        assertNotNull(valrc2N);
        
        assertNotNull(valz11);
        assertNotNull(valz12);
        assertNotNull(valz21);
        assertNotNull(valz22);
        assertNotNull(valz31);
        assertNotNull(valz32);

        assertEquals(0,valz11.getEntry(0),10E-4);
        assertEquals(0,valz12.getEntry(0),10E-4);
        assertEquals(0,valz21.getEntry(0),10E-4);
        assertEquals(0,valz22.getEntry(0),10E-4);
        assertEquals(0,valz31.getEntry(0),10E-4);
        assertEquals(0,valz32.getEntry(0),10E-4);

        for (int i=1;i<32;i++) {
            assertEquals(0, valz11.getEntry(i),10E-04);
            assertEquals(0, valz12.getEntry(i),10E-04);
            assertEquals(0, valz21.getEntry(i),10E-04);
            assertEquals(0, valz22.getEntry(i),10E-04);
            assertEquals(0, valz31.getEntry(i),10E-04);
            assertEquals(0, valz32.getEntry(i),10E-04);
        }
        
    }

    @Override
    public UseAlgebra getUsedAlgebra() {
        return UseAlgebra.get5dConformalGA();
    }

}
