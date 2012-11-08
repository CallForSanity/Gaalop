package de.gaalop.testbenchTbaGapp.tba.gps;

import de.gaalop.testbenchTbaGapp.tba.InputOutput;
import de.gaalop.testbenchTbaGapp.tba.VariableValue;
import org.junit.Ignore;
import java.util.LinkedList;

/**
 * Implements a GPS test with no variables
 * @author Christian Steinmetz
 */
@Ignore
public class GPSNoVarsTest extends GenericGPSTest {

    public GPSNoVarsTest(Point3D sat1, Point3D sat2, Point3D sat3, double d1, double d2, double d3) {
        super(sat1, sat2, sat3, d1, d2, d3);
    }

    @Override
    protected String constantDefinition() {
        return "sat1x = " + sat1.x + ";" + "\n"
                + "sat1y = " + sat1.y + ";" + "\n"
                + "sat1z = " + sat1.z + ";" + "\n"
                + "sat2x = " + sat2.x + ";" + "\n"
                + "sat2y = " + sat2.y + ";" + "\n"
                + "sat2z = " + sat2.z + ";" + "\n"
                + "sat3x = " + sat3.x + ";" + "\n"
                + "sat3y = " + sat3.y + ";" + "\n"
                + "sat3z = " + sat3.z + ";" + "\n"
                + "d1 = " + d1 + ";" + "\n"
                + "d2 = " + d2 + ";" + "\n"
                + "d3 = " + d3 + ";" + "\n";
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
                return getChecksForAllInstances();
            }

            @Override
            public int getNo() {
                return 0;
            }
            
            protected String getChecksForAllInstances() {
        return "// check containing all outputs\n"
                + "assertTrue(outputs.containsKey(\"rc1N$1\"));\n"
                + "assertTrue(outputs.containsKey(\"rc1N$2\"));\n"
                + "assertTrue(outputs.containsKey(\"rc1N$3\"));\n"
                + "assertTrue(outputs.containsKey(\"rc2N$1\"));\n"
                + "assertTrue(outputs.containsKey(\"rc2N$2\"));\n"
                + "assertTrue(outputs.containsKey(\"rc2N$3\"));\n"
                + "double rc1Nx = outputs.get(\"rc1N$1\");\n"
                + "double rc1Ny = outputs.get(\"rc1N$2\");\n"
                + "double rc1Nz = outputs.get(\"rc1N$3\");\n"
                + "double rc2Nx = outputs.get(\"rc2N$1\");\n"
                + "double rc2Ny = outputs.get(\"rc2N$2\");\n"
                + "double rc2Nz = outputs.get(\"rc2N$3\");\n"
                + "// check number of outputs\n"
                + "assertEquals(6, outputs.size());\n";
    }
        });
        
        


        return result;
    }
}
