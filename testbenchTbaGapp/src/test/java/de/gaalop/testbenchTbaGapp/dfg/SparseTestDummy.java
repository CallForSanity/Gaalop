package de.gaalop.testbenchTbaGapp.dfg;

import de.gaalop.*;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCase;
import de.gaalop.testbenchTbaGapp.tba.framework.TBATestCodeGeneratorPlugin;
import de.gaalop.testbenchTbaGapp.tba.framework.TestDummy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SparseTestDummy extends TestDummy {

    /**
     * Uses a TBATestCase to run Gaalop once.
     *
     * TODO: reduce redundant code, make methods non-static
     *
     * @param tBATestCase the test case implementation to provide
     * @return
     */
    public static boolean compile(TBATestCase tBATestCase) {

        // Initialize Gaalop Plugins
        // The CodeGenerator plugin is used for Black Box Testing
        CodeParser parser                                       = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy           = new de.gaalop.globalSettings.Plugin().createGlobalSettingsStrategy();
        VisualCodeInserterStrategy visualCodeInserterStrategy   = new de.gaalop.visualCodeInserter.Plugin().createVisualCodeInserterStrategy();
        AlgebraStrategy algebraStrategy                         = new de.gaalop.algebra.Plugin().createAlgebraStrategy();

        // overridden behaviour begin
        de.gaalop.tba.Plugin optimizationStrategyPlugin = new de.gaalop.tba.Plugin();
        optimizationStrategyPlugin.useSparseExpressions = true;
        OptimizationStrategy optimizationStrategy       = optimizationStrategyPlugin.createOptimizationStrategy();
        // overridden behaviour end
        // TODO: reduce redundant code, make methods non-static

        CodeGenerator codeGenerator                             = new TBATestCodeGeneratorPlugin(tBATestCase.getInputValues()).createCodeGenerator();




        CompilerFacade facade = new CompilerFacade(
                parser,
                globalSettingsStrategy,
                visualCodeInserterStrategy,
                algebraStrategy,
                optimizationStrategy,
                codeGenerator,
                tBATestCase.getAlgebraName(),
                true,
                "");

        Set<OutputFile> outputFiles;
        try {
            // compile CLU script provided by test case
            outputFiles = facade.compile(new InputFile("TestCase", tBATestCase.getCLUScript()));



            OutputFile vars;
            OutputFile outputVars;

            Iterator<OutputFile> iterator = outputFiles.iterator();
            OutputFile f = iterator.next();
            if (f.getName().equals("Map Values")) {
                vars = f;
                outputVars = iterator.next();
            } else {
                outputVars = f;
                vars = iterator.next();
            }

            HashMap<Variable, Double> varsValues = TestDummy.contentToMap(vars.getContent());
            HashMap<Variable, Double> outputVarsValues = TestDummy.contentToMap(outputVars.getContent());


            tBATestCase.testOutputs(outputVarsValues);
            return true;
        } catch (CompilationException ex) {
            Logger.getLogger(TestDummy.class.getName()).log(Level.INFO, ex.getMessage());
            return false;
        }

    }



}
