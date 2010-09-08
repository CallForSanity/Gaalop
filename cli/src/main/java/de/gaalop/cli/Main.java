package de.gaalop.cli;

import de.gaalop.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.Set;

/**
 * Main class of the Gaalop command line interface. Arguments are parsed using the args4j plugin.
 *
 * @author Christian Schwinn
 *
 */
public class Main {

  private Log log = LogFactory.getLog(Main.class);

  @Option(name = "-i", required = true, usage = "The input file.")
  private String inputFile;

  @Option(name = "-o", required = false, usage = "Sets the directory where the output files are created.")
  private String outputDirectory = "";

  @Option(name = "-m", required = false, usage = "Sets the Maple binary path.")
  private String mapleBinaryPath = "";

  @Option(name = "-parser", required = false, usage = "Sets the class name of the code parser plugin that should be used.")
  private String codeParserPlugin = "de.gaalop.clucalc.input.Plugin";

  @Option(name = "-generator", required = false, usage = "Sets the class name of the code generator plugin that should be used.")
  private String codeGeneratorPlugin = "de.gaalop.cpp.Plugin";

  @Option(name = "-optimizer", required = false, usage = "Sets the class name of the optimization strategy plugin that should be used.")
  private String optimizationStrategyPlugin = "de.gaalop.maple.Plugin";

  /**
   * Starts the command line interface of Gaalop.
   *
   * @param args -i to specify the input file (mandatory), -o to specify the output directory,
   * -parser to set the input parser, -generator to set the code generator plugin, -optimizer to
   * select the optimization strategy.
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
    log.debug("Starting up compilation process.");

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
      File outFile;
      if(outputDirectory.length() == 0)
        outFile = new File(output.getName());
      else
      {
        // NOTE: output file does not return actual name, but the full path of the file
        File tempFile = new File(output.getName());
        outFile = new File(outputDirectory, tempFile.getName());
      }
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
    CodeParser codeParser = createCodeParser();

    OptimizationStrategy optimizationStrategy = createOptimizationStrategy();

    CodeGenerator codeGenerator = createCodeGenerator();

    return new CompilerFacade(codeParser, optimizationStrategy, codeGenerator);
  }

  private CodeParser createCodeParser() {
    Set<CodeParserPlugin> plugins = Plugins.getCodeParserPlugins();
    for (CodeParserPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(codeParserPlugin)) {
        return plugin.createCodeParser();
      }
    }

    System.err.println("Unknown code parser plugin: " + codeParserPlugin);
    System.exit(-2);
    return null;
  }

  private OptimizationStrategy createOptimizationStrategy() {
    Set<OptimizationStrategyPlugin> plugins = Plugins.getOptimizationStrategyPlugins();
    for (OptimizationStrategyPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(optimizationStrategyPlugin)) {
        if(mapleBinaryPath.length() != 0)
          plugin.setMaplePathsByMapleBinaryPath(mapleBinaryPath);
        return plugin.createOptimizationStrategy();
      }
    }

    System.err.println("Unknown optimization strategy plugin: " + optimizationStrategyPlugin);
    System.exit(-3);
    return null;
  }

  private CodeGenerator createCodeGenerator() {
    Set<CodeGeneratorPlugin> plugins = Plugins.getCodeGeneratorPlugins();
    for (CodeGeneratorPlugin plugin : plugins) {
      if (plugin.getClass().getName().equals(codeGeneratorPlugin)) {
        return plugin.createCodeGenerator();
      }
    }

    System.err.println("Unknown code generator plugin: " + codeGeneratorPlugin);
    System.exit(-4);
    return null;
  }

  public InputFile getInputFile() throws Exception {
    final Reader reader;
    final String filename;

    if (inputFile.equals("-")) {
      reader = new InputStreamReader(System.in);
      filename = "stdin";
    } else {
      reader = new FileReader(inputFile);
      filename = inputFile;
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
