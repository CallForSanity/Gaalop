package de.gaalop.testbenchTbaGapp.tbaNew.options;

import de.gaalop.*;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.Plugin;
import de.gaalop.testbenchTbaGapp.tbaNew.framework.TBATestCase;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian Steinmetz
 */
public class TestOptions {
    
    public static void main(String[] args) {
        de.gaalop.tba.Plugin optimPlugin = new Plugin();
        optimPlugin.optOneExpressionRemoval = true;
        ControlFlowGraph graphWithout = getGraph(optimPlugin, false);
        System.out.println("Without Maxima:");
        System.out.println(graphWithout);
        
    }
    
    
    private static ControlFlowGraph getGraph(de.gaalop.tba.Plugin optimPlugin, boolean optMaxima) {
        TBATestCase tBATestCase = new TBATestCase() {

            @Override
            public String getCLUScript() {
                return
                        "G = createEllipsoid(2,3,3,2,2,4);\n"+
                        ":Blue;\n"+
                        ":G;\n"+
                        "\n"+
                        "H = createCylinder(3,4,0,3);\n"+
                        ":Green;\n"+
                        ":H;\n"+
                        "\n"+
                        "S = (G^H); //String als Bivektor\n"+
                        ":Red;\n"+
                        ":S;\n";
            }

            @Override
            public void testOutputs(HashMap<Variable, Double> outputs) {
                //Do nothing
            }

            @Override
            public HashMap<Variable, Double> getInputValues() {
                return new HashMap<Variable, Double>();
            }

            @Override
            public String getAlgebraName() {
                return "9d";
            }
        };
        
        de.gaalop.globalSettings.Plugin globalPlugin = new de.gaalop.globalSettings.Plugin();
        globalPlugin.optMaxima = optMaxima;
        
        de.gaalop.visualizer.Plugin visPlugin = new de.gaalop.visualizer.Plugin();
        visPlugin.lwJglNativePath = "C:\\Libs\\lwjgl\\native\\windows";
        
        CodeGeneratorGraphStorage codeGeneratorPlugin = new CodeGeneratorGraphStorage();
        
        CodeParser parser                                       = new de.gaalop.clucalc.input.Plugin().createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy           = globalPlugin.createGlobalSettingsStrategy();
        VisualCodeInserterStrategy visualCodeInserterStrategy   = new de.gaalop.visualCodeInserter.Plugin().createVisualCodeInserterStrategy();
        AlgebraStrategy algebraStrategy                         = new de.gaalop.algebra.Plugin().createAlgebraStrategy();
        OptimizationStrategy optimizationStrategy               = optimPlugin.createOptimizationStrategy();
        CodeGenerator codeGenerator                             = visPlugin.createCodeGenerator();
        //codeGeneratorPlugin;//
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
            Logger.getLogger(TestOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return codeGeneratorPlugin.graph;
        //return null;
    }

}
