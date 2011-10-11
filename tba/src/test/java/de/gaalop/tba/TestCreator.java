package de.gaalop.tba;

import de.gaalop.tba.circle.CircleNoVarsTest;
import de.gaalop.tba.circle.CircleOneVarTest;
import de.gaalop.tba.circle.CircleOnlyVarsTest;
import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.gps.GPSNoVarsTest;
import de.gaalop.tba.gps.GPSOnlyVarsTest;
import de.gaalop.tba.gps.Point3D;
import de.gaalop.tba.linePointDistance.LinePointDistance;
import de.gaalop.tba.trigonFuncTest.TrigonometricFunctions;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Ignore;

/**
 * Produces test classes for automatic testing
 * @author Christian Steinmetz
 */
@Ignore
public class TestCreator {

    public static void main(String[] args) throws Exception {
        new TestCreator();
    }
    private PrintWriter out;

    /**
     * Starts a test case
     */
    private void beginTestCase() {
        try {
            out = new PrintWriter("src/test/java/de/gaalop/tba/generatedTests/TBATest.java");

            out.println("package de.gaalop.tba.generatedTests;\n");
            out.println("import java.util.HashMap;");
            out.println("import de.gaalop.tba.gps.Point3D;\n");
            out.println("import de.gaalop.tba.linePointDistance.Vec3D;\n");
            out.println("import org.junit.Test;\n");
            out.println("import static org.junit.Assert.*;\n");
            out.println("public class TBATest {");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ends a test case
     */
    private void endTestCase() {
        out.println("}");

        out.close();
    }

    public TestCreator() throws Exception {
        beginTestCase();


        // create all tests

        //positive tests - tests that should be compiled
        try {
            // test(new CircleOneVarTest(new Point(5,2),new Point(3,9),new Point(6,4),new boolean[]{true,false,false,false,false,false},1),"CircleOneVar");

            testTrigonometric();

            testCircleNoVars();
            testCircleOneVar();
            testCircleOnlyVars();
            testOutputCount();
            testUnusedTest();

            testGPSNoVars();
            testGPSOnlyVars();

            testLinePointDistance();
        } catch (OptimizationException e) {
            throw new Exception("CompileError in positive tests: " + e);
        }

        //negative tests - tests shouldn't be compiled, since they aren't conform to specification
        boolean valid = true;
        try {
            testMultipleAssignmentsTest();
        } catch (OptimizationException e) {
            valid = false;
        }
        if (valid) {
            throw new Exception("No CompileError in negative test MultipleAssignmentsTest");
        }


        valid = true;
        try {
            testControlFlowTest();
        } catch (OptimizationException e) {
            valid = false;
        }
        if (valid) {
            throw new Exception("No CompileError in negative test ControlFlowTest");
        }


        endTestCase();
    }

    /**
     * Tests a GenericTestable
     * @param testable The GenericTestable object
     * @param cluName The name of the CLUScript
     * @throws OptimizationException
     */
    private void test(GenericTestable testable, String cluName) throws OptimizationException {
        try {
            CodeParser parser = (new de.gaalop.clucalc.input.Plugin()).createCodeParser();
            ControlFlowGraph graph = parser.parseFile(new InputFile(cluName, testable.getCLUScript()));
            //writeFile(new OutputFile("test",testable.getCLUScript(), Charset.forName("UTF-8")));

            de.gaalop.tba.Plugin tbaPlugin = new de.gaalop.tba.Plugin();
            OptimizationStrategy tba = (tbaPlugin).createOptimizationStrategy();

            tba.transform(graph);

            CodeGenerator generator = (new de.gaalop.java.Plugin()).createCodeGenerator();
            Set<OutputFile> outputFiles = generator.generate(graph);

            for (OutputFile outputFile : outputFiles) {
                writeFile(outputFile);
            }

            for (InputOutput io : testable.getInputOutputs()) {

                out.println("  @Test");
                out.println("  public void test" + cluName + io.getNo() + "() {");
                out.println("    " + cluName + " inst = new " + cluName + "();");

                for (VariableValue curVal : io.getInputs()) {
                    out.println("    assertTrue(inst.setValue(\"" + curVal.getName() + "\"," + curVal.getValue() + "f));");
                }

                out.println("    inst.calculate();");
                out.println("    // collect outputs");

                out.println("    HashMap<String,Float> outputs = inst.getValues();");

                out.println("    // check outputs");
                out.println(io.getCheckOutputsCode());

                out.println("  }");


            }


        } catch (CodeParserException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CodeGeneratorException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testTrigonometric() throws OptimizationException {
        test(new TrigonometricFunctions(), "TrigonometricFunctions");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testLinePointDistance() throws OptimizationException {
        test(new LinePointDistance(
                new Point3D(3, 4, 5),
                new Point3D(7, 8, 10),
                new Point3D(3, 8, 10)), "LinePointDistance");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testCircleNoVars() throws OptimizationException {
        test(new CircleNoVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)), "CircleNoVars");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testCircleOneVar() throws OptimizationException {
        test(new CircleOneVarTest(new Point(5, 2), new Point(3, 9), new Point(6, 4), new boolean[]{true, false, false, false, false, false}, 50), "CircleOneVar");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testCircleOnlyVars() throws OptimizationException {
        test(new CircleOnlyVarsTest(new Point(5, 2), new Point(3, 9), new Point(6, 4)), "CircleOnlyVars");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testGPSNoVars() throws OptimizationException {
        test(new GPSNoVarsTest(new Point3D(1, 1, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0), 0.6f, 0.7f, 0.5f), "GPSNoVars");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testGPSOnlyVars() throws OptimizationException {
        test(new GPSOnlyVarsTest(new Point3D(1, 1, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0), 0.6f, 0.7f, 0.9f), "GPSOnlyVars");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testMultipleAssignmentsTest() throws OptimizationException {
        test(new MultipleAssignmentsTest(), "MultipleAssignments");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testControlFlowTest() throws OptimizationException {
        test(new ControlFlowTest(), "ControlFlow");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testOutputCount() throws OptimizationException {
        test(new OutputCountTest(), "OutputCount");
    }

    /**
     * Dummy method
     * @throws OptimizationException
     */
    private void testUnusedTest() throws OptimizationException {
        test(new Unused(), "Unused");
    }

    /**
     * Writes a file
     * @param outputFile The OutputFile object
     */
    private void writeFile(OutputFile outputFile) {
        try {
            PrintWriter out1 = new PrintWriter("src/test/java/de/gaalop/tba/generatedTests/" + outputFile.getName());
            out1.println("package de.gaalop.tba.generatedTests;\n");
            out1.print(outputFile.getContent());
            out1.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
