package de.gaalop.tba;

import java.util.LinkedList;
import org.junit.Ignore;

/**
 * Tests the controlflowtest
 * @author Christian Steinmetz
 */
@Ignore
public class ControlFlowTest implements GenericTestable {

    protected static final double EPSILON = 10E-4;

    @Override
    public String getCLUScript() {
        return
            "//#pragma output a$0\n" +
            "//#pragma output b$0\n" +
            "//#pragma output c$0\n" +
            "\n" +
            "a = 1\n;" +
            "c = 4\n;" +
            "if (a == t) {\n" +
            "  a = 2\n;" +
            "} else {\n" +
            "  a = 3\n;" +
            "}\n" +
            "b = a + 1\n;" +
            "?a;\n" +
            "?b;\n" +
            "?c;"
            ;
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
       LinkedList<InputOutput> result = new LinkedList<InputOutput>();
       
       result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                result.add(new VariableValue("t$0",1));
                return result;
            }

            @Override
            public String getCheckOutputsCode() {
                return
                        "assertEquals(outputs.get(\"a\"),2,"+EPSILON+");\n" +
                        "assertEquals(outputs.get(\"b\"),3,"+EPSILON+");\n" +
                        "assertEquals(outputs.get(\"c\"),4,"+EPSILON+");\n"
                        ;
            }

            @Override
            public int getNo() {
                return 0;
            }
        });

        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                LinkedList<VariableValue> result = new LinkedList<VariableValue>();
                result.add(new VariableValue("t$0",2));
                return result;
            }

            @Override
            public String getCheckOutputsCode() {
                return
                        "assertEquals(outputs.get(\"a\"),3,"+EPSILON+");\n" +
                        "assertEquals(outputs.get(\"b\"),4,"+EPSILON+");\n" +
                        "assertEquals(outputs.get(\"c\"),4,"+EPSILON+");\n"
                        ;
            }

            @Override
            public int getNo() {
                return 1;
            }
        });

       return result;
    }

    @Override
    public UseAlgebra getUsedAlgebra() {
        return UseAlgebra.get5dConformalGA();
    }



}
