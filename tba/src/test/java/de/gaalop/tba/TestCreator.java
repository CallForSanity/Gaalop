package de.gaalop.tba;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Ignore;

/**
 *
 * @author christian
 */
@Ignore
public class TestCreator {

    public static void main(String[] args) {
        new TestCreator();
    }

    private PrintWriter out;

    private void beginTestCase() {
        try {
            out = new PrintWriter("src/test/java/de/gaalop/tba/generatedTests/TBATest.java");

            out.println("package de.gaalop.tba.generatedTests;\n");
            out.println("import java.util.HashMap;");
            out.println("import org.junit.Test;\n");
            out.println("import static org.junit.Assert.*;\n");
            out.println("public class TBATest {");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void endTestCase() {
            out.println("}");

            out.close();
    }

    public TestCreator() {
        beginTestCase();

        // create all tests
        testCircleNoVars();
        testCircleOneVar();
        testCircleOnlyVars();
        testMultipleAssignmentsTest();

        endTestCase();
    }

    private void test(GenericTestable testable, String cluName) {
        try {
            CodeParser parser = (new de.gaalop.clucalc.input.Plugin()).createCodeParser();
            ControlFlowGraph graph = parser.parseFile(new InputFile(cluName, testable.getCLUScript()));

            OptimizationStrategy tba = (new de.gaalop.tba.Plugin()).createOptimizationStrategy();
            tba.transform(graph);

            CodeGenerator generator = (new de.gaalop.java.Plugin()).createCodeGenerator();
            Set<OutputFile> outputFiles = generator.generate(graph);

            for (OutputFile outputFile: outputFiles)
                writeFile(outputFile);

            for (InputOutput io : testable.getInputOutputs()) {

                out.println("  @Test");
                out.println("  public void test"+cluName+io.getNo()+"() {");
                out.println("    "+cluName+" inst = new "+cluName+"();");

                for (VariableValue curVal: io.getInputs())
                    out.println("    assertTrue(inst.setValue(\""+curVal.getName()+"\","+curVal.getValue()+"f));");

                out.println("    inst.calculate();");
                out.println("    // collect outputs");

                out.println("    HashMap<String,Float> outputs = inst.getValues();");

                out.println("    // check outputs");
                out.println(io.getCheckOutputsCode());
                
                out.println("  }");
    

            }


        } catch (CodeParserException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OptimizationException ex1) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (CodeGeneratorException ex) {
                Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
            }


    }

    
    private void testCircleNoVars() {
        test(new CircleNoVarsTest(new Point(5,2),new Point(3,9),new Point(6,4)),"CircleNoVars");

        //TODO more tests
    }

   
    private void testCircleOneVar() {
        test(new CircleOneVarTest(new Point(5,2),new Point(3,9),new Point(6,4),new boolean[]{true,false,false,false,false,false},50),"CircleOneVar");
        
        //TODO more tests
    }

    
    private void testCircleOnlyVars() {
        test(new CircleOnlyVarsTest(new Point(5,2),new Point(3,9),new Point(6,4)),"CircleOnlyVars");

        //TODO more tests
    }

    private void testMultipleAssignmentsTest() {
        test(new MultipleAssignmentsTest(),"MultipleAssignments");
    }

    private void writeFile(OutputFile outputFile) {
        try {
            PrintWriter out = new PrintWriter("src/test/java/de/gaalop/tba/generatedTests/"+outputFile.getName());
            out.println("package de.gaalop.tba.generatedTests;\n");
            out.print(outputFile.getContent());
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }





}