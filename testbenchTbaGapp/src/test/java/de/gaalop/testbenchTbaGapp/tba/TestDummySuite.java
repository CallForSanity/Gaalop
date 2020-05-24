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

        String message = "[Test] testing "+tBATestCase.getClass().getName();

        if(denseResult != sparseResult) {
            message += "\n [WARNING]\t test with denseExpressions returned ";
            message += denseResult ? "TRUE" : "FALSE";
            message += " while sparseExpressions test returned ";
            message += sparseResult ? "TRUE" : "FALSE";
        }

        if(overallResult == false) {
            message += "\n [WARNING]\t one test of this suite failed \n\t\t denseExpressions returned ";
            message += denseResult ? "TRUE" : "FALSE";
            message += "\n\t\t sparseExpressions test returned ";
            message += sparseResult ? "TRUE" : "FALSE";
        } else {
            message += "\n SUCCESS both test succeeded with TRUE";
        }
        message += "\n";

        Logger.getLogger(TestDummy.class.getName()).log(Level.INFO, message);
        return overallResult;

    }


}
