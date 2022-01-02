package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.AlgebraStrategy;
import de.gaalop.CodeGenerator;
import de.gaalop.CodeParser;
import de.gaalop.CompilerFacade;
import de.gaalop.GlobalSettingsStrategy;
import de.gaalop.OptimizationStrategy;
import de.gaalop.VisualCodeInserterStrategy;
import de.gaalop.testbenchTbaGapp.tba.common.LocalVarsTest;
import de.gaalop.testbenchTbaGapp.tba.gps.Point3D;
import de.gaalop.testbenchTbaGapp.tba.circle.CircleNoVarsTest;
import de.gaalop.testbenchTbaGapp.tba.circle.CircleOneVarTest;
import de.gaalop.testbenchTbaGapp.tba.circle.CircleOnlyVarsTest;
import de.gaalop.testbenchTbaGapp.tba.common.*;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCodeGeneratorPlugin;
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
    public void testNormalBase() {
        
        TBATestCase testCase = new NormalBaseTest();
        
        de.gaalop.globalSettings.Plugin g = new de.gaalop.globalSettings.Plugin();
        g.outputToNormalBase = true;
        
        CodeParser parser                                       = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy           = g.createGlobalSettingsStrategy();
        VisualCodeInserterStrategy visualCodeInserterStrategy   = new de.gaalop.visualCodeInserter.Plugin().createVisualCodeInserterStrategy();
        AlgebraStrategy algebraStrategy                         = new de.gaalop.algebra.Plugin().createAlgebraStrategy();
        OptimizationStrategy optimizationStrategy               = new de.gaalop.tba.Plugin().createOptimizationStrategy();
        CodeGenerator codeGenerator                             = new TBATestCodeGeneratorPlugin(testCase.getInputValues()).createCodeGenerator();
        
        CompilerFacade facade = new CompilerFacade(
                parser, 
                globalSettingsStrategy, 
                visualCodeInserterStrategy, 
                algebraStrategy, 
                optimizationStrategy, 
                codeGenerator, 
                testCase.getAlgebraName(), 
                true, 
                "");
        
        
        assertTrue(
            TestDummy.compileWithCompilerFacade(testCase, facade)
                );
    } 
    
    @Test
    public void testNoLineMacroTest() {
        assertTrue(
            TestDummy.compile(new NoLineMacroTest())
        );
    }
    
    @Test
    public void testSingleLineMacroTest() {
        assertTrue(
            TestDummy.compile(new SingleLineMacroTest())
        );
    }
    
    @Test
    public void testMultipleLineMacroTest() {
        assertTrue(
            TestDummy.compile(new MultipleLineMacroTest())
        );
    }
    
    @Test
    public void testQuestionLineMacroTest() {
        assertTrue(
            TestDummy.compile(new QuestionLineMacroTest())
        );
    }
    
    @Test
    public void testExklamationLineMacroTest() {
        assertTrue(
            TestDummy.compile(new ExklamationLineMacroTest())
        );
    }

    @Test
    public void testCoefficientTest() {
        assertTrue(
            TestDummy.compile(new CoefficientTest())
        );
    }


    @Test
    public void testMultipleLocalVars() {
        assertTrue(
            TestDummy.compile(new FunctionTest())
                );
    } 
    
    @Test
    public void testEmptyComments() {
        assertTrue(
            TestDummy.compile(new EmptyCommentTest())
                );
    } 
    
    @Test
    public void testNoLocalVars() {
        assertTrue(
            TestDummy.compile(new NoLocalVarsTest())
                );
    } 
    
    @Test
    public void testLocalVars() {
        assertTrue(
            TestDummy.compile(new LocalVarsTest())
                );
    } 

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
    
    @Test
    public void operatorPriority() {
        assertTrue(
            TestDummy.compile(new OperatorPriorityTest())
                );
    }
    
    
}
