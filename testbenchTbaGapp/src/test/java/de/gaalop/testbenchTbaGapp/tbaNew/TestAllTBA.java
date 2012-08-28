package de.gaalop.testbenchTbaGapp.tbaNew;

import de.gaalop.testbenchTbaGapp.tba.generatedTests.CircleNoVars;
import de.gaalop.testbenchTbaGapp.tbaNew.circle.CircleNoVarsTest;
import de.gaalop.testbenchTbaGapp.tbaNew.framework.TestDummy;
import java.awt.Point;
import org.junit.Test;

/**
 *
 * @author Christian Steinmetz
 */
public class TestAllTBA {

    @Test
    public void testCircleNoVars() {
        TestDummy.compile(new CircleNoVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)), "5d");
        
        
    } 
    
}
