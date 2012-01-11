package de.gaalop.testbenchTbaGapp.tba.trigonFuncTest;

import de.gaalop.testbenchTbaGapp.tba.GenericTestable;
import de.gaalop.testbenchTbaGapp.tba.InputOutput;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.testbenchTbaGapp.tba.VariableValue;
import java.util.LinkedList;

/**
 * Implements a simple test on trigonometric functions
 * @author Christian Steinmetz
 */
public class TrigonometricFunctions implements GenericTestable {

    @Override
    public String getCLUScript() {
        return "?r = cos(0);";
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();
        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                // no inputs
                return result;
            }

            @Override
            public String getCheckOutputsCode() {
                return "assertTrue(outputs.containsKey(\"r$0\"));\n"
                        + "double r$0 = outputs.get(\"r$0\");\n"
                        + "assertEquals(1,r$0,0.01);";
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
