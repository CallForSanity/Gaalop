package de.gaalop.cli;

import de.gaalop.*;
import de.gaalop.algebra.DefinedAlgebra;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of the Gaalop command line interface. Arguments are parsed using the args4j plugin.
 * 
 * @author Christian Schwinn, Christian Steinmetz
 * 
 */
public class Main {

  private Log log = LogFactory.getLog(Main.class);
  
  // General settings
  @Option(name = "--cli", required = false, usage = "Required to use this cli.")
  private boolean cli = false;
  
  @Option(name = "--input", aliases = { "-i" }, required = true, usage = "The input file.")
  private String inputFile;

  @Option(name = "--outputDir", aliases = { "-o" },required = false, usage = "Sets the directory where the output files are created.")
  private String outputDirectory = "";
  
  @Option(name = "--algebraName", aliases = { "-a" }, required = false, usage = "Sets the name of the algebra that should be used.")
  private String algebraName = "cga";
  
  @Option(name = "--algebraBaseDir", aliases = { "--ad" }, required = false, usage = "Sets the base directory path of the user-defined algebras.")
  private String algebraBaseDirectory = null;
  
  @Option(name = "--maximaPath", aliases = { "-m" }, required = false, usage = "Sets the maxima path and enables the usage of maxima.")
  private String maximaPath = "";
  
  // Stages

  @Option(name = "--parserPlugin", aliases = { "--pa" }, required = false, usage = "Sets the class name of the code parser plugin that should be used.")
  private String codeParserPlugin = "de.gaalop.clucalc.input.Plugin";
  
  @Option(name = "--globalSettingsPlugin", aliases = { "--gs" }, required = false, usage = "Sets the class name of the Global Settings Strategy plugin that should be used.")
  private String globalSettingsPlugin = "de.gaalop.globalSettings.Plugin";
  
  @Option(name = "--visualCodeInserterPlugin", aliases = { "--vc" }, required = false, usage = "Sets the class name of the visual code inserter plugin that should be used.")
  private String visualCodeInserterPlugin = "de.gaalop.visualCodeInserter.Plugin";
  
  @Option(name = "--algebraPlugin", aliases = { "--al" }, required = false, usage = "Sets the class name of the algebra strategy plugin that should be used.")
  private String algebraPlugin = "de.gaalop.algebra.Plugin";
  
  @Option(name = "--optimizationPlugin", aliases = { "--op" }, required = false, usage = "Sets the class name of the optimization strategy plugin that should be used.")
  private String optimizationPlugin = "de.gaalop.tba.Plugin";

  @Option(name = "--codeGeneratorPlugin", aliases = { "--cg" }, required = false, usage = "Sets the class name of the code generator plugin that should be used.")
  private String codeGeneratorPlugin = "de.gaalop.cpp.Plugin";
  
  // Convenience options
  @Option(name = "--gapp", aliases = { "-p" }, required = false, usage = "Use the GAPP Optimization Plugin. Default: Disabled.")
  private boolean useGAPP = false;
  
  @Option(name = "--specific",  aliases = { "--spec" }, required = false, usage = "Sets specific configuration properties for plugins by a comma separated list. Syntax {Plugin1-Classname}:{Plugin1-Optionname}={Plugin1-Value},{Plugin2-Classname}:{Plugin2-Optionname}={Plugin2-Value}, ... . Defaults to ''.")
  private String specificOptions = "";
  
  private LinkedList<SpecificOption> specificOptionsList;
  
  /**
   * Starts the command line interface of Gaalop.
   */
  public static void main(String[] args) throws Exception {
    Main main = new Main();
    CmdLineParser parser = new CmdLineParser(main);
    try {
       parser.parseArgument(args);
       main.run();
    } catch (CmdLineException e) {
       System.err.println(e.getMessage());
       parser.printUsage(System.err);
    }
  }

  /**
   * Runs the command line interface. Should be invoked after setup.
   */
  public void run() throws Exception {
      if (outputDirectory.trim().isEmpty()) {
          outputDirectory = new File(inputFile).getParent();
      }
      
    log.debug("Starting up compilation process.");
    
    if (useGAPP) 
        optimizationPlugin = "de.gaalop.gapp.Plugin";

    // Configure the compiler
    CompilerFacade compiler = createCompiler();

    // Perform compilation
    InputFile inputFile = getInputFile();
    Set<OutputFile> outputFiles = compiler.compile(inputFile);
    
    for (OutputFile output : outputFiles) {
        writeFile(output);
    }
  }

  private void writeFile(OutputFile output) throws FileNotFoundException,
      UnsupportedEncodingException {
    if (outputDirectory.equals("-")) {
        printFileToConsole(output);
    } else {
        File outFile = new File(output.getName());
        if (!outputDirectory.trim().isEmpty()) 
            outFile = new File(outputDirectory, outFile.getName());
        
        PrintWriter writer = new PrintWriter(outFile, output.getEncoding().name());
        writer.print(output.getContent());
        writer.close();
    }
  }

  private void printFileToConsole(OutputFile output) {
    System.out.println("----------------------------------------------------------");
    System.out.println("Output File: " + output.getName());
    System.out.println("----------------------------------------------------------");
    System.out.println(output.getContent());
    System.out.println("----------------------------------------------------------");
  }

  private CompilerFacade createCompiler() {
    specificOptionsList = SpecificOption.parseSpecificOptions(specificOptions);
      
    if (!maximaPath.trim().isEmpty()) { 
        specificOptionsList.add(new SpecificOption("de.gaalop.globalSettings.Plugin", "optMaxima", "true"));
        specificOptionsList.add(new SpecificOption("de.gaalop.globalSettings.Plugin", "maximaCommand", maximaPath.trim()));
    } else {
        specificOptionsList.add(new SpecificOption("de.gaalop.globalSettings.Plugin", "optMaxima", "false"));
        specificOptionsList.add(new SpecificOption("de.gaalop.globalSettings.Plugin", "maximaCommand", ""));
    }
      
    CodeParser codeParser = createCodeParser();
    
    GlobalSettingsStrategy globalSettingsStrategy = createGlobalSettingsStrategy();

    AlgebraStrategy algebraStrategy = createAlgebraStrategy();

    VisualCodeInserterStrategy visualizerStrategy = createVisualizerStrategy();

    OptimizationStrategy optimizationStrategy = createOptimizationStrategy();

    CodeGenerator codeGenerator = createCodeGenerator();
    
    boolean asRessource = false;
    
    for (DefinedAlgebra definedAlgebra: de.gaalop.algebra.Plugin.getDefinedAlgebras()) 
        if (definedAlgebra.id.equals(algebraName.trim())) 
            asRessource = true;
   
    return new CompilerFacade(codeParser, globalSettingsStrategy, visualizerStrategy, algebraStrategy, optimizationStrategy, codeGenerator, algebraName, asRessource, algebraBaseDirectory);
  }
  
  private void setSpecificOptionsForPlugin(Object plugin) {
      for (SpecificOption option: specificOptionsList) {
          if (option.classname.equals(plugin.getClass().getName())) {
              try {
                  Field optionField = plugin.getClass().getDeclaredField(option.optionname);
                  optionField.setAccessible(true);
                  Class<?> type = optionField.getType();
                  if (type.equals(Boolean.TYPE)) optionField.setBoolean(plugin, Boolean.parseBoolean(option.value));
                  else if (type.equals(Integer.TYPE)) optionField.setInt(plugin, Integer.parseInt(option.value));
                  else if (type.equals(Long.TYPE)) optionField.setLong(plugin, Long.parseLong(option.value));
                  else 
                      optionField.set(plugin, option.value);
              } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                  Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      }
  }

  private CodeParser createCodeParser() {
    Set<CodeParserPlugin> plugins = Plugins.getCodeParserPlugins();
    for (CodeParserPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(codeParserPlugin)) {
          setSpecificOptionsForPlugin(plugin);
        return plugin.createCodeParser();
      }
    }

    System.err.println("Unknown code parser plugin: " + codeParserPlugin);
    System.exit(-2);
    return null;
  }
  
  private GlobalSettingsStrategy createGlobalSettingsStrategy() {
    Set<GlobalSettingsStrategyPlugin> plugins = Plugins.getGlobalSettingsStrategyPlugins();
    for (GlobalSettingsStrategyPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(globalSettingsPlugin)) {
            setSpecificOptionsForPlugin(plugin);
         return plugin.createGlobalSettingsStrategy();
      }
    }

    System.err.println("Unknown global settings plugin: " + globalSettingsPlugin);
    System.exit(-3);
    return null;
  }

  private AlgebraStrategy createAlgebraStrategy() {
    Set<AlgebraStrategyPlugin> plugins = Plugins.getAlgebraStrategyPlugins();
    for (AlgebraStrategyPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(algebraPlugin)) {
          setSpecificOptionsForPlugin(plugin);
        return plugin.createAlgebraStrategy();
      }
    }

    System.err.println("Unknown algebra plugin: " + algebraPlugin);
    System.exit(-3);
    return null;
  }

  private VisualCodeInserterStrategy createVisualizerStrategy() {
    Set<VisualCodeInserterStrategyPlugin> plugins = Plugins.getVisualizerStrategyPlugins();
    for (VisualCodeInserterStrategyPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(visualCodeInserterPlugin)) {
          setSpecificOptionsForPlugin(plugin);
        return plugin.createVisualCodeInserterStrategy();
      }
    }

    System.err.println("Unknown visual code inserter plugin: " + visualCodeInserterPlugin);
    System.exit(-4);
    return null;
  }

  private OptimizationStrategy createOptimizationStrategy() {
    Set<OptimizationStrategyPlugin> plugins = Plugins.getOptimizationStrategyPlugins();
    for (OptimizationStrategyPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(optimizationPlugin)) {
    	  OptimizationStrategy strategy = plugin.createOptimizationStrategy();
          setSpecificOptionsForPlugin(plugin);
    	  // no progressListeners will be added
        return strategy;
      }
    }

    System.err.println("Unknown optimization plugin: " + optimizationPlugin);
    System.exit(-5);
    return null;
  }

  private CodeGenerator createCodeGenerator() {
    Set<CodeGeneratorPlugin> plugins = Plugins.getCodeGeneratorPlugins();
    for (CodeGeneratorPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(codeGeneratorPlugin)) {
          setSpecificOptionsForPlugin(plugin);
        
        return plugin.createCodeGenerator();
      }
    }

    System.err.println("Unknown code generator plugin: " + codeGeneratorPlugin);
    System.exit(-6);
    return null;
  }

  public InputFile getInputFile() throws Exception {
    final Reader reader;
    final String filename;

    if (inputFile.equals("-")) {
      reader = new InputStreamReader(System.in);
      filename = "calculate";
    } else {
      reader = new FileReader(inputFile);
      filename = new File(inputFile).getName();
    }

    try {
      BufferedReader bufReader = new BufferedReader(reader);
      String line;
      StringBuilder result = new StringBuilder();
      while ((line = bufReader.readLine()) != null) {
        result.append(line);
        result.append('\n');
      }

      return new InputFile(filename, result.toString());
    } finally {
      reader.close();
    }
  }
}
