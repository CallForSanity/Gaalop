package de.gaalop.tba.gps;

import de.gaalop.tba.InputOutput;
import de.gaalop.tba.VariableValue;
import java.awt.Point;
import java.util.LinkedList;
import org.junit.Ignore;

/**
 *
 * @author christian
 */
@Ignore
public class GPSOnlyVarsTest extends GenericGPSTest {

    public GPSOnlyVarsTest(Point3D sat1, Point3D sat2, Point3D sat3, float d1, float d2, float d3) {
        super(sat1, sat2, sat3, d1, d2, d3);
    }

    @Override
    protected String constantDefinition() {
        return "\n";
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();

        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                result.add(new VariableValue("sat1x_0",sat1.x));
                result.add(new VariableValue("sat1y_0",sat1.y));
                result.add(new VariableValue("sat1z_0",sat1.z));
                result.add(new VariableValue("sat2x_0",sat2.x));
                result.add(new VariableValue("sat2y_0",sat2.y));
                result.add(new VariableValue("sat2z_0",sat2.z));
                result.add(new VariableValue("sat3x_0",sat3.x));
                result.add(new VariableValue("sat3y_0",sat3.y));
                result.add(new VariableValue("sat3z_0",sat3.z));
                result.add(new VariableValue("d1_0",d1));
                result.add(new VariableValue("d2_0",d2));
                result.add(new VariableValue("d3_0",d3));
                return result;
            }

            @Override
            public String getCheckOutputsCode() {
                return
               getChecksForAllInstances();
            }

            @Override
            public int getNo() {
                return 0;
            }
        });

        return result;
    }

}
