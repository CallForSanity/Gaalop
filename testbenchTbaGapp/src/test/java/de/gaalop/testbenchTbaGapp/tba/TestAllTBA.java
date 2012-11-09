package de.gaalop.testbenchTbaGapp.tbaNew;

import de.gaalop.testbenchTbaGapp.tbaNew.gps.Point3D;
import de.gaalop.testbenchTbaGapp.tbaNew.circle.CircleNoVarsTest;
import de.gaalop.testbenchTbaGapp.tbaNew.circle.CircleOneVarTest;
import de.gaalop.testbenchTbaGapp.tbaNew.circle.CircleOnlyVarsTest;
import de.gaalop.testbenchTbaGapp.tbaNew.common.*;
import de.gaalop.testbenchTbaGapp.tbaNew.framework.TestDummy;
import de.gaalop.testbenchTbaGapp.tbaNew.gps.GPSNoVarsTest;
import de.gaalop.testbenchTbaGapp.tbaNew.gps.GPSOnlyVarsTest;
import de.gaalop.testbenchTbaGapp.tbaNew.linePointDistance.LinePointDistance;
import de.gaalop.testbenchTbaGapp.tbaNew.negative.ControlFlowTest;
import de.gaalop.testbenchTbaGapp.tbaNew.negative.MultipleAssignmentsTest;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class TestAllTBA {

    @Test
    public void testCircleNoVars() {
        assertTrue(
            TestDummy.compile(new CircleNoVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)))
                );
    } 
    
    @Test
    public void testCircleOnlyVars() {
        assertTrue(
            TestDummy.compile(new CircleOnlyVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)))
                );
    } 
    
    @Test
    public void testCircleOneVar() {
        assertTrue(
            TestDummy.compile(new CircleOneVarTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)))
                );
    } 
    
    @Test
    public void testGPSNoVars() {
        assertTrue(
            TestDummy.compile(new GPSNoVarsTest(new Point3D(1, 1, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0), 0.6f, 0.7f, 0.5f))
                );
    } 
    
    @Test
    public void testGPSOnlyVars() {
        assertTrue(
            TestDummy.compile(new GPSOnlyVarsTest(new Point3D(1, 1, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0), 0.6f, 0.7f, 0.9f))
                );
    } 
    
    @Test
    public void testOneMacro() {
        assertTrue(
            TestDummy.compile(new OneMacroTest())
                );
    } 
    
    @Test
    public void testTwoMacros() {
        assertTrue(
            TestDummy.compile(new TwoMacrosTest())
                );
    } 
    
    @Test
    public void testTrigonometric() {
        assertTrue(
            TestDummy.compile(new TrigonometricFunctions())
                );
    } 
    
    @Test
    public void testOutputCount() {
        assertTrue(
            TestDummy.compile(new OutputCountTest())
                );
    } 
    
    @Test
    public void testUnused() {
        assertTrue(
            TestDummy.compile(new UnusedTest())
                );
    }
    
    @Test
    public void testTrafoTst() {
        assertTrue(
            TestDummy.compile(new TrafoTest())
                );
    }
    
    @Test
    public void testLinePointDistance() {
        assertTrue(
            TestDummy.compile(new LinePointDistance(new Point3D(3, 4, 5),
                new Point3D(7, 8, 10),
                new Point3D(3, 8, 10)))
                );
    } 
    
    @Test
    public void testNegativeMultipleAssignements() {
        assertFalse(
            TestDummy.compile(new MultipleAssignmentsTest())
                );
    } 
    
    @Test
    public void testNegativeControlFlow() {
        assertFalse(
            TestDummy.compile(new ControlFlowTest())
                );
    } 
    
}
