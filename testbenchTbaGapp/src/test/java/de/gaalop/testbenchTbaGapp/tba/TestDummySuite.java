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
            message += "\n [WARNING]  unequal tests:" +
                       "\n            test with denseExpressions returned ";
            message += denseResult ? "TRUE" : "FALSE";
            message += "\n            while sparseExpressions test returned ";
            message += sparseResult ? "TRUE" : "FALSE";
        } else if(overallResult == false) {
            message += "\n [INFO]     both tests of this suite failed";
        } else {
            message += "\n SUCCESS:   both test succeeded with TRUE";
        }
        message += "\n";

        Logger.getLogger(TestDummy.class.getName()).log(Level.INFO, message);
        return overallResult;

    }


}
