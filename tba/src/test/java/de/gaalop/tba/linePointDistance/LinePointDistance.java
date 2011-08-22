package de.gaalop.tba.linePointDistance;

import de.gaalop.tba.GenericTestable;
import de.gaalop.tba.InputOutput;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.VariableValue;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class LinePointDistance implements GenericTestable {

    @Override
    public String getCLUScript() {
        return
                "v1 = VecN3(p1_x,p1_y,p1_z);\n"+
                "v2 = VecN3(p2_x,p2_y,p2_z);\n"+
                "vTest = VecN3(pTest_x,pTest_y,pTest_z);\n"+
                "pi = 3.141592;\n"+
                "L = *(v1^v2^einf);\n"+
                "La = L/abs(L);\n"+
                "R = cos(pi/4) - La*sin(pi/4);\n"+
                "V = R*vTest*(R~);\n"+
                "E = *(v1^v2^V^einf);\n"+
                "Ea = E/abs(E);\n"+
                "?abstand = abs(Ea.vTest);\n";
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();

        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                /*result.add(new VariableValue("p1_x",0));
                result.add(new VariableValue("p1_y",0));
                result.add(new VariableValue("p1_z",0));
                result.add(new VariableValue("p2_x",0));
                result.add(new VariableValue("p2_y",0));
                result.add(new VariableValue("p2_z",0));
                result.add(new VariableValue("pTest_x",0));
                result.add(new VariableValue("pTest_y",0));
                result.add(new VariableValue("pTest_z",0));*/
                return result;
            }

            @Override
            public String getCheckOutputsCode() {
                throw new UnsupportedOperationException("Not supported yet.");
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
