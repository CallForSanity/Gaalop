package de.gaalop.tba;

import java.util.LinkedList;
import org.junit.Ignore;

/**
 * Produces a test, that counts the outputted blades
 * @author christian
 */
@Ignore
public class OutputCountTest implements GenericTestable {

    @Override
    public String getCLUScript() {
        return "a=0;\n"
             + "?a;";
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
                        "assertEquals(32,outputs.size());";
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
