package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.testbenchTbaGapp.dfg.SparseMvExpressionsTest;
import de.gaalop.testbenchTbaGapp.dfg.SparseTestDummy;
import de.gaalop.testbenchTbaGapp.tba.common.LocalVarsTest;
import de.gaalop.testbenchTbaGapp.tba.gps.Point3D;
import de.gaalop.testbenchTbaGapp.tba.circle.CircleNoVarsTest;
import de.gaalop.testbenchTbaGapp.tba.circle.CircleOneVarTest;
import de.gaalop.testbenchTbaGapp.tba.circle.CircleOnlyVarsTest;
import de.gaalop.testbenchTbaGapp.tba.common.*;
import de.gaalop.testbenchTbaGapp.tba.framework.TestDummy;
import de.gaalop.testbenchTbaGapp.tba.gps.GPSNoVarsTest;
import de.gaalop.testbenchTbaGapp.tba.gps.GPSOnlyVarsTest;
import de.gaalop.testbenchTbaGapp.tba.linePointDistance.LinePointDistance;
import de.gaalop.testbenchTbaGapp.tba.negative.ControlFlowTest;
import de.gaalop.testbenchTbaGapp.tba.negative.MultipleAssignmentsTest;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Steinmetz
 */
public class TestAllTBA {


    @Test
    public void testSparseMvExpressionsDummyTest() {
        assertTrue(
            TestDummySuite.compile(new SparseMvExpressionsTest())
        );
    }

    @Test
    public void testCoefficientTest() {
        assertTrue(
            TestDummySuite.compile(new CoefficientTest())
        );
    }





    @Test
    public void testMultipleLocalVars() {
        assertTrue(
            TestDummySuite.compile(new FunctionTest())
        );
    }




    @Test
    public void testNoLocalVars() {
        assertTrue(
            TestDummySuite.compile(new NoLocalVarsTest())
                );
    } 
    
    @Test
    public void testLocalVars() {
        assertTrue(
            TestDummySuite.compile(new LocalVarsTest())
                );
    } 

    @Test
    public void testCircleNoVars() {
        assertTrue(
            TestDummySuite.compile(new CircleNoVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)))
                );
    } 
    
    @Test
    public void testCircleOnlyVars() {
        assertTrue(
            TestDummySuite.compile(new CircleOnlyVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)))
                );
    } 
    
    @Test
    public void testCircleOneVar() {
        assertTrue(
            TestDummySuite.compile(new CircleOneVarTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)))
                );
    } 
    
    @Test
    public void testGPSNoVars() {
        assertTrue(
            TestDummySuite.compile(new GPSNoVarsTest(new Point3D(1, 1, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0), 0.6f, 0.7f, 0.5f))
                );
    } 
    
    @Test
    public void testGPSOnlyVars() {
        assertTrue(
            TestDummySuite.compile(new GPSOnlyVarsTest(new Point3D(1, 1, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0), 0.6f, 0.7f, 0.9f))
                );
    } 
    
    @Test
    public void testOneMacro() {
        assertTrue(
            TestDummySuite.compile(new OneMacroTest())
                );
    } 
    
    @Test
    public void testTwoMacros() {
        assertTrue(
            TestDummySuite.compile(new TwoMacrosTest())
                );
    } 
    
    @Test
    public void testTrigonometric() {
        assertTrue(
            TestDummySuite.compile(new TrigonometricFunctions())
                );
    } 
    
    @Test
    public void testOutputCount() {
        assertTrue(
            TestDummySuite.compile(new OutputCountTest())
                );
    } 
    
    @Test
    public void testUnused() {
        assertTrue(
            TestDummySuite.compile(new UnusedTest())
                );
    }
    
    @Test
    public void testTrafoTst() {
        assertTrue(
            TestDummySuite.compile(new TrafoTest())
                );
    }
    
    @Test
    public void testLinePointDistance() {
        assertTrue(
            TestDummySuite.compile(new LinePointDistance(new Point3D(3, 4, 5),
                new Point3D(7, 8, 10),
                new Point3D(3, 8, 10)))
                );
    } 
    
    @Test
    public void testNegativeMultipleAssignements() {
        assertFalse(
                TestDummySuite.compile(new MultipleAssignmentsTest())
                );
    } 
    
    @Test
    public void testNegativeControlFlow() {
        assertFalse(
            TestDummySuite.compile(new ControlFlowTest())
                );
    } 
    
    @Test
    public void operatorPriority() {
        assertTrue(
            TestDummySuite.compile(new OperatorPriorityTest())
                );
    }
    
    
}
