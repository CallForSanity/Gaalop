package de.gaalop.testbenchTbaGapp;

import de.gaalop.testbenchTbaGapp.gapp.GAPPTestCreator;
import de.gaalop.testbenchTbaGapp.tba.TestCreator;

/**
 * Running this class creates all creatable tests in the testbench
 * @author Christian Steinmetz
 */
public class CreateAllTests {

    public static void main(String[] args) throws Exception {
        TestCreator.main(args);
        GAPPTestCreator.main(args);
    }

}
