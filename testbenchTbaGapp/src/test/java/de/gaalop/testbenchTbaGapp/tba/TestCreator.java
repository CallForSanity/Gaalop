package de.gaalop.testbenchTbaGapp.tba;

import de.gaalop.AlgebraStrategy;
import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.testbenchTbaGapp.tba.gps.GPSNoVarsTest;
import de.gaalop.testbenchTbaGapp.tba.gps.GPSOnlyVarsTest;
import de.gaalop.testbenchTbaGapp.tba.gps.Point3D;
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
            out = new PrintWriter("src/test/java/de/gaalop/testbenchTbaGapp/tba/generatedTests/TBATest.java");

            out.println("package de.gaalop.testbenchTbaGapp.tba.generatedTests;\n");
            out.println("import java.util.HashMap;");
            out.println("import de.gaalop.testbenchTbaGapp.tba.gps.Point3D;");
            out.println("import de.gaalop.testbenchTbaGapp.tba.linePointDistance.Vec3D;");
            out.println("import org.junit.Test;");
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

            testGPSNoVars();
            testGPSOnlyVars();
            
        } catch (OptimizationException e) {
            throw new Exception("CompileError in positive tests: " + e);
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
            graph.algebraName = "5d";
            graph.asRessource = true;
            
            de.gaalop.algebra.Plugin plugin = new de.gaalop.algebra.Plugin();
            plugin.usePrecalulatedTables = true;       
            AlgebraStrategy algebraStrategy = plugin.createAlgebraStrategy();
            algebraStrategy.transform(graph);

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

                out.println("    HashMap<String,Double> outputs = inst.getValues();");

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
     * Writes a file
     * @param outputFile The OutputFile object
     */
    private void writeFile(OutputFile outputFile) {
        try {
            PrintWriter out1 = new PrintWriter("src/test/java/de/gaalop/testbenchTbaGapp/tba/generatedTests/" + outputFile.getName());
            out1.println("package de.gaalop.testbenchTbaGapp.tba.generatedTests;\n");
            out1.print(outputFile.getContent());
            out1.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
