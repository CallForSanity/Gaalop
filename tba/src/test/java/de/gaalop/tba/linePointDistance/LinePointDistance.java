package de.gaalop.tba.linePointDistance;

import de.gaalop.tba.GenericTestable;
import de.gaalop.tba.InputOutput;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.VariableValue;
import de.gaalop.tba.gps.Point3D;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class LinePointDistance implements GenericTestable {

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
        return
                "v1 = VecN3(p1x,p1y,p1z);\n"+
                "v2 = VecN3(p2x,p2y,p2z);\n"+
                "vTst = VecN3(pTstx,pTsty,pTstz);\n"+
                "pi = 3.141592;\n"+
                "L = *(v1^v2^einf);\n"+
                "La = L/abs(L);\n"+
                "R = cos(pi/4) - La*sin(pi/4);\n"+
                "V = R*vTst*(~R);\n"+
                "P = *(v1^v2^V^einf);\n"+
                "Pa = P/abs(P);\n"+
                "?abstand = abs(Pa.vTst);\n"+
                "?nor = Pa + (Pa.e0)*einf;\n";
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();

        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                result.add(new VariableValue("p1x$0",p1.x));
                result.add(new VariableValue("p1y$0",p1.y));
                result.add(new VariableValue("p1z$0",p1.z));
                result.add(new VariableValue("p2x$0",p2.x));
                result.add(new VariableValue("p2y$0",p2.y));
                result.add(new VariableValue("p2z$0",p2.z));
                result.add(new VariableValue("pTstx$0",pTest.x));
                result.add(new VariableValue("pTsty$0",pTest.y));
                result.add(new VariableValue("pTstz$0",pTest.z));
                return result;
            }

            @Override
            public String getCheckOutputsCode() {
                return 
                    "assertTrue(outputs.containsKey(\"abstand$0\"));\n"+
                    "assertTrue(outputs.containsKey(\"nor$1\"));\n"+
                    "assertTrue(outputs.containsKey(\"nor$2\"));\n"+
                    "assertTrue(outputs.containsKey(\"nor$3\"));\n"+

                    "float abstand = outputs.get(\"abstand$0\");\n"+

                    "Vec3D nor = new Vec3D(outputs.get(\"nor$1\"), outputs.get(\"nor$2\"), outputs.get(\"nor$3\"));\n"+
                    "nor.normalize();\n"+
                    "nor.scalarMultiplication(abstand);\n"+
                    "Point3D pBase = nor.applyToPoint(new Point3D("+pTest.x+"f,"+pTest.y+"f,"+pTest.z+"f));\n"+

                    "//pBase must lie on plane and on line\n"+

                    "//test if on line, assume that dx,dy,dz not zero\n"+
                    "double tx = (pBase.x-"+p1.x+")/("+(p2.x-p1.x)+");\n"+
                    "double ty = (pBase.y-"+p1.y+")/("+(p2.y-p1.y)+");\n"+
                    "double tz = (pBase.z-"+p1.z+")/("+(p2.z-p1.z)+");\n"+
                    "// since the normal is unique except of a sign:\n"+
                    "if (Math.abs(tx-ty)>0.001 || Math.abs(tz-ty)>0.001) {\n"+

                        "nor = new Vec3D(outputs.get(\"nor$1\"), outputs.get(\"nor$2\"), outputs.get(\"nor$3\"));\n"+
                        "nor.normalize();\n"+
                        "nor.scalarMultiplication(-abstand);\n"+
                        "pBase = nor.applyToPoint(new Point3D("+pTest.x+"f,"+pTest.y+"f,"+pTest.z+"f));\n"+

                        "//pBase must lie on plane and on line\n"+

                        "//test if on line, assume that dx,dy,dz not zero\n"+
                        "tx = (pBase.x-"+p1.x+")/("+(p2.x-p1.x)+");\n"+
                        "ty = (pBase.y-"+p1.y+")/("+(p2.y-p1.y)+");\n"+
                        "tz = (pBase.z-"+p1.z+")/("+(p2.z-p1.z)+");\n"+

                    "}\n"+

                    "assertEquals(tx,ty,0.001);\n"+
                    "assertEquals(ty,tz,0.001);\n"+

                    "//test if on plane\n"+
                    "Vec3D r = new Vec3D("+(p2.x-p1.x)+"f,"+(p2.y-p1.y)+"f,"+(p2.z-p1.z)+"f);\n"+
                    "r.normalize();\n"+

                    "Vec3D xmpTest = new Vec3D(pBase.x-"+pTest.x+"f,pBase.y-"+pTest.y+"f,pBase.z-"+pTest.z+"f);\n"+
                    "float dp = xmpTest.dotProduct(r);\n"+

                    "assertEquals(0,dp,0.001);\n";
            }

            @Override
            public int getNo() {
                return 0;
            }

 
        });
        return result;
    }

    @Override
    public UseAlgebra getUsedAlgebra() {
        return UseAlgebra.get5dConformalGA();
    }

}
