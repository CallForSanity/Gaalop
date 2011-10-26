package de.gaalop.tba;

import java.util.LinkedList;

/**
 * Represents a test that performs a transformation.
 * It uses inverses and reverses
 * @author Christian Steinmetz
 */
public class TrafoTst implements GenericTestable {

    protected static final double EPSILON = 10E-4;

    @Override
    public String getCLUScript() {
        return
	"x1 = 3;\n"+
	"y1 = 6;\n"+

	"tx = 1;\n"+
	"ty = 2;\n"+

        "t = tx*e1+ty*e2;\n"+

        "v1 = x1*e1+y1*e2;\n"+
        "?p = v1 + 0.5*v1*v1*einf + e0;\n"+
        "T = 1 - 0.5*t*einf;\n"+
        "?pt = T*p*(~T);\n"+

        "iT = (~T)/(T*(~T));\n"+
        "?pt2 = iT*pt*(~iT);\n"+

        "iT3 = 1/T;\n"+
        "?pt3 = iT3*pt*(~iT3);";
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
                          "float p$1 = outputs.get(\"p$1\");\n"
                        + "float p$2 = outputs.get(\"p$2\");\n"
                        + "float p$5 = outputs.get(\"p$5\");\n"
                        + "float pt2$1 = outputs.get(\"pt2$1\");\n"
                        + "float pt2$2 = outputs.get(\"pt2$2\");\n"
                        + "float pt2$5 = outputs.get(\"pt2$5\");\n"
                        + "float pt3$1 = outputs.get(\"pt3$1\");\n"
                        + "float pt3$2 = outputs.get(\"pt3$2\");\n"
                        + "float pt3$5 = outputs.get(\"pt3$5\");\n"
                        + "assertEquals(p$1,pt2$1,"+EPSILON+");\n"
                        + "assertEquals(p$2,pt2$2,"+EPSILON+");\n"
                        + "assertEquals(p$5,pt2$5,"+EPSILON+");\n"
                        + "assertEquals(p$1,pt3$1,"+EPSILON+");\n"
                        + "assertEquals(p$2,pt3$2,"+EPSILON+");\n"
                        + "assertEquals(p$5,pt3$5,"+EPSILON+");\n"
                        ;

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
