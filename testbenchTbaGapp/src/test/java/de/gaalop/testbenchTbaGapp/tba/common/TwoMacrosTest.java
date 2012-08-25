package de.gaalop.testbenchTbaGapp.tba.common;

import de.gaalop.tba.UseAlgebra;
import de.gaalop.testbenchTbaGapp.tba.GenericTestable;
import de.gaalop.testbenchTbaGapp.tba.InputOutput;
import de.gaalop.testbenchTbaGapp.tba.VariableValue;
import java.util.LinkedList;
import org.junit.Ignore;

/**
 *
 * @author Christian Steinmetz
 */
@Ignore
public class TwoMacrosTest implements GenericTestable {

    @Override
    public String getCLUScript() {
        
        return 
                "myFunc1 = {\n" +
                "2*_P(1)\n" +
                "}\n" +
                "\n" +
                "myFunc2 = {\n" +
                "2*myFunc1(_P(1))\n" +
                "}\n" +
                "\n" +
                "?a = myFunc2(2);\n" +
                "?b = myFunc1(2);\n";
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
                return "assertTrue(outputs.containsKey(\"a$0\"));\n"+
                       "assertEquals(8,outputs.get(\"a$0\"),0.01);\n"+
                       "assertTrue(outputs.containsKey(\"b$0\"));\n"+
                       "assertEquals(4,outputs.get(\"b$0\"),0.01);\n";
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
        return UseAlgebra.get5dConformalGALive();
    }

}
