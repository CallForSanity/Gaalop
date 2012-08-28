package de.gaalop.testbenchTbaGapp.tbaNew.framework;

import de.gaalop.*;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian Steinmetz
 */
public class TestDummy {
    
    public static void compile(TBATestCase tBATestCase, String algebraName) {
        
        CodeParser parser                                       = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy           = new de.gaalop.globalSettings.Plugin().createGlobalSettingsStrategy();
        VisualCodeInserterStrategy visualCodeInserterStrategy   = new de.gaalop.visualCodeInserter.Plugin().createVisualizerStrategy();
        AlgebraStrategy algebraStrategy                         = new de.gaalop.algebra.Plugin().createAlgebraStrategy();
        OptimizationStrategy optimizationStrategy               = new de.gaalop.tba.Plugin().createOptimizationStrategy();
        CodeGenerator codeGenerator                             = new TBATestCodeGeneratorPlugin(tBATestCase.getInputValues()).createCodeGenerator();
        
        CompilerFacade facade = new CompilerFacade(
                parser, 
                globalSettingsStrategy, 
                visualCodeInserterStrategy, 
                algebraStrategy, 
                optimizationStrategy, 
                codeGenerator, 
                algebraName, 
                true, 
                "");
        
        Set<OutputFile> compile;
        try {
            compile = facade.compile(new InputFile("TestCase", tBATestCase.getCLUScript()));
            
            String content = compile.iterator().next().getContent();
            BufferedReader reader = new BufferedReader(new StringReader(content));
            HashMap<Variable, Double> variables = new HashMap<Variable, Double>();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    
                    Variable variable;
                    if (parts[0].contains("[")) {
                        int indexOpen = parts[0].indexOf('[');
                        int indexClose = parts[0].indexOf(']');
                        variable = new MultivectorComponent(parts[0].substring(0, indexOpen).trim(), Integer.parseInt(parts[0].substring(indexOpen+1, indexClose).trim()));
                    } else {
                        variable = new Variable(parts[0].trim());
                    }

                    variables.put(variable, Double.parseDouble(parts[1].trim()));
                }
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(TestDummy.class.getName()).log(Level.SEVERE, null, ex);
            }
                tBATestCase.testOutputs(variables);
        } catch (CompilationException ex) {
            Logger.getLogger(TestDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
