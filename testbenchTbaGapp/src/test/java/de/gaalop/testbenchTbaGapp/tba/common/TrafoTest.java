package de.gaalop.testbenchTbaGapp.tba.common;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class TrafoTest implements TBATestCase {
    
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
    public void testOutputs(HashMap<Variable, Double> outputs) {
            double p$1 = outputs.get(new MultivectorComponent("p", 1));
            double p$2 = outputs.get(new MultivectorComponent("p", 2));
            double p$5 = outputs.get(new MultivectorComponent("p", 5));
            double pt2$1 = outputs.get(new MultivectorComponent("pt2",1));
            double pt2$2 = outputs.get(new MultivectorComponent("pt2",2));
            double pt2$5 = outputs.get(new MultivectorComponent("pt2",5));
            double pt3$1 = outputs.get(new MultivectorComponent("pt3",1));
            double pt3$2 = outputs.get(new MultivectorComponent("pt3",2));
            double pt3$5 = outputs.get(new MultivectorComponent("pt3",5));
            assertEquals(p$1,pt2$1,EPSILON);
            assertEquals(p$2,pt2$2,EPSILON);
            assertEquals(p$5,pt2$5,EPSILON);
            assertEquals(p$1,pt3$1,EPSILON);
            assertEquals(p$2,pt3$2,EPSILON);
            assertEquals(p$5,pt3$5,EPSILON);
        ;
    }

    @Override
    public HashMap<Variable, Double> getInputValues() {
        return new HashMap<Variable, Double>();
    }

    @Override
    public String getAlgebraName() {
        return "cga";
    }

}
