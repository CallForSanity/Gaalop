package de.gaalop.testbenchTbaGapp.tbaNew;

import de.gaalop.testbenchTbaGapp.tba.generatedTests.CircleNoVars;
import de.gaalop.testbenchTbaGapp.tbaNew.circle.CircleNoVarsTest;
import de.gaalop.testbenchTbaGapp.tbaNew.circle.CircleOneVarTest;
import de.gaalop.testbenchTbaGapp.tbaNew.circle.CircleOnlyVarsTest;
import de.gaalop.testbenchTbaGapp.tbaNew.common.OneMacroTest;
import de.gaalop.testbenchTbaGapp.tbaNew.common.TrigonometricFunctions;
import de.gaalop.testbenchTbaGapp.tbaNew.common.TwoMacrosTest;
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
        TestDummy.compile(new CircleNoVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)));
    } 
//    
//    @Test
//    public void testCircleOnlyVars() {
//        TestDummy.compile(new CircleOnlyVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)));
//    } 
//    
//    @Test
//    public void testCircleOneVar() {
//        TestDummy.compile(new CircleOneVarTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)));
//    } 
//    
//    @Test
//    public void testOneMacro() {
//        TestDummy.compile(new OneMacroTest());
//    } 
//    
//    @Test
//    public void testTwoMacros() {
//        TestDummy.compile(new TwoMacrosTest());
//    } 
//    
//    @Test
//    public void testTrigonometric() {
//        TestDummy.compile(new TrigonometricFunctions());
//    } 
//    
}
