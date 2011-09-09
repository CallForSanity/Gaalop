package de.gaalop.tba;

import java.util.LinkedList;

/**
 * Implements a test for testing if an used variable will be removed in dead-code elimination
 * @author Christian Steinmetz
 */
public class Unused implements GenericTestable {

    @Override
    public String getCLUScript() {
        return 
        "rotor = arw + arx * e2 ^ e3 + ary * e3 ^ e1 + arz * e1 ^ e2;\n"+
	"translator = 1 - (0.5 * lpx * e1 ^ einf + 0.5 * lpy * e2 ^ einf + 0.5 * lpz * e3 ^ einf);\n"+
	"?Din = translator*rotor;\n";
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();
        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                return new LinkedList<VariableValue>();
            }

            @Override
            public String getCheckOutputsCode() {
                return
                   "// check number of outputs\n"+
                    "assertEquals(32, outputs.size());\n";
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
        return new UseAlgebra("conf5d");
    }

}
