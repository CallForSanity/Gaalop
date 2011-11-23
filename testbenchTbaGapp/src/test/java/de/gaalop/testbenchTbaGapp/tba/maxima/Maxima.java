package de.gaalop.testbenchTbaGapp.tba.maxima;

import de.gaalop.dfg.Expression;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaOptimizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

/**
 * Class for manually testing the parser for maxima output
 * @author Christian Steinmetz
 */
public class Maxima {

    public Maxima() {
    }

    /**
     * Dummy method for testing the parser for maxima output
     * @param str The string to be parsed
     */
    private void test(String str) {
        try {
            Expression expr = MaximaOptimizer.getExpressionFromMaximaOutput(str);

            System.out.println(str + " -> " + expr);

        } catch (RecognitionException ex) {
            Logger.getLogger(Maxima.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testFloatConstant() {
        System.out.println(" ---- FloatConstant ----");
        test("5.0");
        test("-5.056");
        test("5e-10");
    }

    @Test
    public void testDeepOne() {
        System.out.println(" ---- DeepOne ----");
        test("x+3");
        test("5-x");
        test("3+x*4");
    }
}
