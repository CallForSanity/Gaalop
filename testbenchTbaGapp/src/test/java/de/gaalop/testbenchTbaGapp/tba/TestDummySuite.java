package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.testbenchTbaGapp.dfg.SparseTestDummy;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import de.gaalop.testbenchTbaGapp.tba.framework.TestDummy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestDummySuite {


    public static boolean compile(TBATestCase tBATestCase) {

        boolean denseResult = TestDummy.compile(tBATestCase);
        boolean sparseResult = SparseTestDummy.compile(tBATestCase);
        boolean overallResult = denseResult && sparseResult;

        String message = "";

        if(denseResult != sparseResult) {
            message += "\n [ERROR] test with denseExpressions returned ";
            message += denseResult ? "TRUE" : "FALSE";
            message += " while sparseExpressions test returned ";
            message += sparseResult ? "TRUE" : "FALSE";
        }

        if(overallResult == false) {
            message += "\n [ERROR] one test of this suite failed (denseExpressions returned ";
            message += denseResult ? "TRUE" : "FALSE";
            message += ", sparseExpressions test returned ";
            message += sparseResult ? "TRUE" : "FALSE";
            message += ")";
        } else {
            message += "\n [SUCCESS] both test of this suite succeeded (denseExpressions ";
            message += denseResult ? "TRUE" : "FALSE";
            message += ", sparseExpressions ";
            message += sparseResult ? "TRUE" : "FALSE";
            message += ")";
        }
        message += "\n";

        Logger.getLogger(TestDummySuite.class.getName()).log(Level.INFO, message);
        return overallResult;

    }


}
