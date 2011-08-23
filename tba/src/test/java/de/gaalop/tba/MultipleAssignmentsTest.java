package de.gaalop.tba;

import java.util.LinkedList;
import org.junit.Ignore;

/**
 * Checks the test for multiple assignments
 * @author christian
 */
@Ignore
public class MultipleAssignmentsTest implements GenericTestable {

    protected static final double EPSILON = 10E-4;

    @Override
    public String getCLUScript() {
        return
            "DefVarsN3();"+"\n"+
            ":IPNS;"+"\n"+

            pragmaOutputMvAll("b") +
            pragmaOutputMvAll("d") +

            "a = e1;"+"\n"+
            "b = a^e2;"+"\n"+
            "b = a^e3;"+"\n"+

            "d = a^e3;"+"\n"+
            "?b;"+"\n"+
            "?d;"+"\n"
            ;
    }

    @Override
    public LinkedList<InputOutput> getInputOutputs() {
        LinkedList<InputOutput> result = new LinkedList<InputOutput>();
        result.add(new InputOutput() {

            @Override
            public LinkedList<VariableValue> getInputs() {
                return new LinkedList<VariableValue>(); // no inputs
            }

            @Override
            public String getCheckOutputsCode() {
                StringBuilder result = new StringBuilder();

                compareTwoCompleteMv(result,"b","d");

                return result.toString();
            }

            @Override
            public int getNo() {
                return 0;
            }
        });
        return result;
    }

    private void outputOfCompleteMv(LinkedList<String> outputs, String mvName) {
        for (int blade=0;blade<32;blade++) 
            outputs.add(mvName+"$"+blade);
    }

    private void compareTwoCompleteMv(StringBuilder result, String mvName1, String mvName2) {
        for (int blade=0;blade<32;blade++) {
                    result.append("assertEquals(outputs.get(\"");
                    result.append(mvName1);
                    result.append("$");
                    result.append(Integer.toString(blade));
                    result.append("\"),outputs.get(\"");
                    result.append(mvName2);
                    result.append('$');
                    result.append(Integer.toString(blade));
                    result.append("\"),");
                    result.append(Double.toString(EPSILON));
                    result.append(")");
                    result.append(";\n");
                }
    }

    private String pragmaOutputMvAll(String mvName) {
        StringBuilder result = new StringBuilder();

        for (int blade=0;blade<32;blade++) {
            result.append("//#pragma output ");
            result.append(mvName);
            result.append("$");
            result.append(Integer.toString(blade));
            result.append("\n");
        }

        return result.toString();
    }

    @Override
    public UseAlgebra getUsedAlgebra() {
        return UseAlgebra.get5dConformalGA();
    }

}