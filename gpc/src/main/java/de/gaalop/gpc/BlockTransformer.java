/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gpc;

import de.gaalop.*;
import de.gaalop.algebra.AlStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick Charrier
 */
public class BlockTransformer {
    public static Vector<String> processOptimizationBlocks(List<String> gaalopInFileVector) throws Exception {
        // process gaalop files - call gaalop
        // imported multivector components
        Map<String, Map<String, String>> mvComponents = new HashMap<String, Map<String, String>>();
        Vector<String> gaalopOutFileVector = new Vector<String>();
        String line;
        for (int gaalopFileCount = 0; gaalopFileCount < gaalopInFileVector.size(); ++gaalopFileCount) {

            // import declarations
            StringBuffer variableDeclarations = new StringBuffer();

            // retrieve multivectors from previous block
            if (gaalopFileCount > 0) {
                // scan previous block
                Common.scanBlock(gaalopOutFileVector, gaalopFileCount-1, mvComponents);
                
                // generate import declarations
                generateImportDeclarations(mvComponents, variableDeclarations);
            }

            // run Gaalop
            {
                // log
                System.out.println("compiling");
                
                // compose file
                StringBuffer inputFileStream = new StringBuffer();
                inputFileStream.append(variableDeclarations.toString()).append(Main.LINE_END);
                inputFileStream.append(gaalopInFileVector.get(gaalopFileCount));

                System.out.println(inputFileStream.toString());
                
                // Configure the compiler
                CompilerFacade compiler = createCompiler();

                // Perform compilation
                final InputFile inputFile = new InputFile("inputFile", inputFileStream.toString());
                Set<OutputFile> outputFiles = compiler.compile(inputFile);

                StringBuffer gaalopOutFileStream = new StringBuffer();
                for (OutputFile output : outputFiles) {
                    String content = output.getContent();
                    content = filterHashedBladeCoefficients(content);
                    content = filterImportedMultivectorsFromExport(content, mvComponents.keySet());
                    gaalopOutFileStream.append(content).append(Main.LINE_END);
                }

                gaalopOutFileVector.add(gaalopOutFileStream.toString());
            }
        }
        return gaalopOutFileVector;
    }

    public static void generateImportDeclarations(Map<String, Map<String, String>> mvComponents, StringBuffer variableDeclarations) {
        // generate import declarations
        for (final Entry<String,Map<String,String>> mv : mvComponents.entrySet()) {
            variableDeclarations.append(mv.getKey()).append(" = 0");

            for (final Entry<String,String> mvComp : mv.getValue().entrySet()) {
                final String hashedBladeCoeff = NameTable.getInstance().add(mvComp.getValue());
                variableDeclarations.append(" +").append(hashedBladeCoeff);
                variableDeclarations.append('*').append(mvComp.getKey());
            }

            variableDeclarations.append(";\n");
        }
    }
    
    public static String filterImportedMultivectorsFromExport(final String content, final Set<String> importedMVs) throws Exception {
        final BufferedReader inStream = new BufferedReader(new StringReader(content));
        StringBuffer outStream = new StringBuffer();

        boolean skip = false;
        String line;
        while ((line = inStream.readLine()) != null) {

            // find multivector meta-statement
            int index = line.indexOf(Main.mvSearchString); // will also find components
            if (index >= 0) {
                // extract exported multivector
                final String exportedMV = line.substring(index).split(" ")[3];

                // determine if this MV was previously imported
                skip = importedMVs.contains(exportedMV);
            }
            
            // skip until next multivector meta-statement
            if (!skip) {
                outStream.append(line);
                outStream.append(Main.LINE_END);
            } else if(importedMVs.isEmpty())
                System.err.println("Internal Error: Gaalop Precompiler should not remove multivectors if there are none imported!");
        }

        return outStream.toString();
    }
    
    public static String filterHashedBladeCoefficients(final String content) throws Exception {
        final BufferedReader inStream = new BufferedReader(new StringReader(content));
        StringBuffer outStream = new StringBuffer();

        String line;
        while ((line = inStream.readLine()) != null) {
            for(Entry<String,String> entry : NameTable.getInstance().getTable().entrySet())
                line = line.replaceAll(entry.getKey(),entry.getValue());
            
            outStream.append(line).append(Main.LINE_END);
        }

        return outStream.toString();
    }
    
    public static CompilerFacade createCompiler() {
        CodeParser codeParser = createCodeParser();
        GlobalSettingsStrategy globalSettingsStrategy = createGlobalSettingsStrategy();
        AlgebraStrategy algebraStrategy = createAlgebraStrategy();
        VisualCodeInserterStrategy visualizerStrategy = createVisualizerStrategy();
        OptimizationStrategy optimizationStrategy = createOptimizationStrategy();
        CodeGenerator codeGenerator = createCodeGenerator();
        
        // check if algebra is resource or file
        boolean asResource = true;//false;
        /*try {
            InputStream inputStream = AlStrategy.class.getResourceAsStream("algebra/definedAlgebras.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) 
                if (line.trim().equals(Main.algebraName.trim()))
                    asResource = true;

            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        return new CompilerFacade(codeParser, globalSettingsStrategy, 
        		  visualizerStrategy, algebraStrategy,
				  optimizationStrategy, codeGenerator,
				  Main.algebraName, asResource,
				  Main.algebraBaseDirectory);
    }

    public static CodeParser createCodeParser() {
        Set<CodeParserPlugin> plugins = Plugins.getCodeParserPlugins();
        for (CodeParserPlugin plugin : plugins) {
            if (plugin.getClass().getName().equals(Main.codeParserPlugin)) {
                return plugin.createCodeParser();
            }
        }

        System.err.println("Unknown code parser plugin: " + Main.codeParserPlugin);
        System.exit(-2);
        return null;
    }
    
    public static GlobalSettingsStrategy createGlobalSettingsStrategy() {
        Set<GlobalSettingsStrategyPlugin> plugins = Plugins.getGlobalSettingsStrategyPlugins();
        for (GlobalSettingsStrategyPlugin plugin : plugins) {
          if (plugin.getClass().getName().equals(Main.globalSettingsStrategyPlugin)) {
            ((de.gaalop.globalSettings.Plugin) plugin).optMaxima = !Main.externalOptimizerPath.isEmpty();
            ((de.gaalop.globalSettings.Plugin) plugin).maximaCommand = Main.externalOptimizerPath;
          
            return plugin.createGlobalSettingsStrategy();
          }
        }

        System.err.println("Unknown algebra strategy plugin: " + Main.algebraStrategyPlugin);
        System.exit(-3);
        return null;
    }

    public static AlgebraStrategy createAlgebraStrategy() {
        Set<AlgebraStrategyPlugin> plugins = Plugins.getAlgebraStrategyPlugins();
        for (AlgebraStrategyPlugin plugin : plugins) {
            if (plugin.getClass().getName().equals(Main.algebraStrategyPlugin)) {
                return plugin.createAlgebraStrategy();
            }
        }

        System.err.println("Unknown algebra strategy plugin: " + Main.algebraStrategyPlugin);
        System.exit(-3);
        return null;
    }

   public static VisualCodeInserterStrategy createVisualizerStrategy() {
     Set<VisualCodeInserterStrategyPlugin> plugins = Plugins.getVisualizerStrategyPlugins();
     for (VisualCodeInserterStrategyPlugin plugin : plugins) {
       if (plugin.getClass().getName().equals(Main.visualizerStrategyPlugin)) {
         return plugin.createVisualizerStrategy();
       }
     }

     System.err.println("Unknown visualizer strategy plugin: " + Main.visualizerStrategyPlugin);
     System.exit(-4);
     return null;
    }

    public static OptimizationStrategy createOptimizationStrategy() {
        Set<OptimizationStrategyPlugin> plugins = Plugins.getOptimizationStrategyPlugins();
        for (OptimizationStrategyPlugin plugin : plugins) {
            if (plugin.getClass().getName().equals(Main.optimizationStrategyPlugin)) {
                if (Main.externalOptimizerPath.length() != 0) {
                    if(plugin instanceof de.gaalop.maple.Plugin) {
                        ((de.gaalop.maple.Plugin) plugin).setMapleBinaryPath(Main.externalOptimizerPath);
                        ((de.gaalop.maple.Plugin) plugin).setMapleJavaPath(Main.externalOptimizerPath + "/../java");
                    }
                }

                return plugin.createOptimizationStrategy();
            }
        }

        System.err.println("Unknown optimization strategy plugin: " + Main.optimizationStrategyPlugin);
        System.exit(-3);
        return null;
    }

    public static CodeGenerator createCodeGenerator() {
        Set<CodeGeneratorPlugin> plugins = Plugins.getCodeGeneratorPlugins();
        for (CodeGeneratorPlugin plugin : plugins) {
            if (plugin.getClass().getName().equals(Main.codeGeneratorPlugin)) {
                return plugin.createCodeGenerator();
            }
        }

        System.err.println("Unknown code generator plugin: " + Main.codeGeneratorPlugin);
        System.exit(-4);
        return null;
    }
}
