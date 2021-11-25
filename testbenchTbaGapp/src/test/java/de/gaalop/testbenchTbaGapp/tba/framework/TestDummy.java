package de.gaalop.testbenchTbaGapp.tba.framework;



import de.gaalop.*;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian Steinmetz
 *
 *
 *
 */
public class TestDummy {

    /**
     * Uses a TBATestCase to run Gaalop once
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
        OptimizationStrategy optimizationStrategy               = new de.gaalop.tba.Plugin().createOptimizationStrategy();
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
            
            HashMap<Variable, Double> varsValues = contentToMap(vars.getContent());
            HashMap<Variable, Double> outputVarsValues = contentToMap(outputVars.getContent());
            

            tBATestCase.testOutputs(outputVarsValues);
            return true;
        } catch (CompilationException ex) {
            return false;
        }
        
    }
    
    public static boolean compile(TBATestCase tBATestCase, CodeGeneratorPlugin codegenPlugin) {
        
        CodeParser parser                                       = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy           = new de.gaalop.globalSettings.Plugin().createGlobalSettingsStrategy();
        VisualCodeInserterStrategy visualCodeInserterStrategy   = new de.gaalop.visualCodeInserter.Plugin().createVisualCodeInserterStrategy();
        AlgebraStrategy algebraStrategy                         = new de.gaalop.algebra.Plugin().createAlgebraStrategy();
        OptimizationStrategy optimizationStrategy               = new de.gaalop.tba.Plugin().createOptimizationStrategy();
        CodeGenerator codeGenerator                             = codegenPlugin.createCodeGenerator();
        
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
            outputFiles = facade.compile(new InputFile("TestCase", tBATestCase.getCLUScript()));

            return true;
        } catch (CompilationException ex) {
            return false;
        }
        
    }

    /**
     * This method converts a string in the form of "" to a Map
     * TODO: extend comment
     * @param content
     * @return
     */
    protected static HashMap<Variable, Double> contentToMap(String content) {
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
        return variables;
    }

    public static void compileWithOptions(TBATestCase tBATestCase, de.gaalop.tba.Plugin optimPlugin) {
        
        CodeParser parser                                       = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy           = new de.gaalop.globalSettings.Plugin().createGlobalSettingsStrategy();
        VisualCodeInserterStrategy visualCodeInserterStrategy   = new de.gaalop.visualCodeInserter.Plugin().createVisualCodeInserterStrategy();
        AlgebraStrategy algebraStrategy                         = new de.gaalop.algebra.Plugin().createAlgebraStrategy();
        OptimizationStrategy optimizationStrategy               = optimPlugin.createOptimizationStrategy();
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
            
            HashMap<Variable, Double> varsValues = contentToMap(vars.getContent());
            HashMap<Variable, Double> outputVarsValues = contentToMap(outputVars.getContent());
            

            tBATestCase.testOutputs(outputVarsValues);
        } catch (CompilationException ex) {
            Logger.getLogger(TestDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void compileWithOptions(TBATestCase tBATestCase, de.gaalop.globalSettings.Plugin globalPlugin, CodeGenerator codeGenerator) {
        
        CodeParser parser                                       = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy           = globalPlugin.createGlobalSettingsStrategy();
        VisualCodeInserterStrategy visualCodeInserterStrategy   = new de.gaalop.visualCodeInserter.Plugin().createVisualCodeInserterStrategy();
        AlgebraStrategy algebraStrategy                         = new de.gaalop.algebra.Plugin().createAlgebraStrategy();
        OptimizationStrategy optimizationStrategy               = new de.gaalop.tba.Plugin().createOptimizationStrategy();
        
        
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
            outputFiles = facade.compile(new InputFile("TestCase", tBATestCase.getCLUScript()));
        } catch (CompilationException ex) {
            Logger.getLogger(TestDummy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public static boolean compileWithCompilerFacade(TBATestCase tBATestCase, CompilerFacade facade) {

        Set<OutputFile> outputFiles;
        try {
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
            
            HashMap<Variable, Double> varsValues = contentToMap(vars.getContent());
            HashMap<Variable, Double> outputVarsValues = contentToMap(outputVars.getContent());
            

            tBATestCase.testOutputs(outputVarsValues);
            return true;
        } catch (CompilationException ex) {
            return false;
        }
        
    }
}
