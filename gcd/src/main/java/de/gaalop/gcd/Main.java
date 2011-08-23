package de.gaalop.gcd;

import de.gaalop.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

/**
 * Main class of the Gaalop command line interface. Arguments are parsed using the args4j plugin.
 *
 * @author Patrick Charrier
 *
 */
public class Main {

    private void writeLinePragma(BufferedWriter outputFile, Integer lineCount) throws IOException {
        // line pragma for compile errors
        outputFile.write("#line ");
        outputFile.write(lineCount.toString());
        outputFile.write(" \"");
        outputFile.write(inputFilePath);
        outputFile.write("\"\n");
    }

    class MvComponent {

        String bladeHandle;
        String bladeName;
        int bladeArrayIndex;
    };
    
    private Log log = LogFactory.getLog(Main.class);
    
    @Option(name = "-i", required = true, usage = "The input file.")
    private String inputFilePath;
    @Option(name = "-o", required = true, usage = "The output file.")
    private String outputFilePath;
    @Option(name = "-m", required = false, usage = "Sets an external optimzer path. This can be either Maple Binary Path or Maxima Path")
    private String externalOptimizerPath = "";
    @Option(name = "-parser", required = false, usage = "Sets the class name of the code parser plugin that should be used.")
    private String codeParserPlugin = "de.gaalop.clucalc.input.Plugin";
    @Option(name = "-generator", required = false, usage = "Sets the class name of the code generator plugin that should be used.")
    private String codeGeneratorPlugin = "de.gaalop.compressed.Plugin";
    @Option(name = "-optimizer", required = false, usage = "Sets the class name of the optimization strategy plugin that should be used.")
    private String optimizationStrategyPlugin = "de.gaalop.maple.Plugin";

    private static String PATH_SEP = "/";
    private static char LINE_END = '\n';

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
            main.runGCD();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        } catch (Throwable t) {
//            while (t.getCause() != null) {
//                t = t.getCause();
//            }

//            System.err.println(t.getMessage());
            t.printStackTrace();
        }
    }

    public static final BufferedReader createFileInputStringStream(String fileName) {
        try {
            final FileInputStream fstream = new FileInputStream(fileName);
            final DataInputStream in = new DataInputStream(fstream);
            return new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            return null;
        }
    }

    public static BufferedWriter createFileOutputStringStream(String fileName) {
        try {
            final FileWriter fstream = new FileWriter(fileName);
            return new BufferedWriter(fstream);
        } catch (Exception e) {
            return null;
        }
    }

    public void runGCD() throws Exception {

        String runPath;
        {
            final File directory = new File(".");
            runPath = directory.getCanonicalPath();
        }

        // process input file
        // split into gaalop input files and save in memory
        List<String> gaalopInFileVector = new ArrayList<String>();
        String line;
        {
            final BufferedReader inputFile = createFileInputStringStream(inputFilePath);
            while ((line = inputFile.readLine()) != null) {
                // found gaalop line
                if (line.contains("#pragma gcd begin")) {
                    StringBuffer gaalopInFileStream = new StringBuffer();

                    // read until end of optimized file
                    while ((line = inputFile.readLine()) != null) {
                        if (line.contains("#pragma gcd end")) {
                            break;
                        } else {
                            gaalopInFileStream.append(line);
                            gaalopInFileStream.append(LINE_END);
                        }
                    }

                    // add to vector
                    gaalopInFileVector.add(gaalopInFileStream.toString());
                }
            }
        }

        // process gaalop files - call gaalop
        Vector<String> gaalopOutFileVector = new Vector<String>();
        for (int gaalopFileCount = 0; gaalopFileCount < gaalopInFileVector.size(); ++gaalopFileCount) {

            // import declarations
            StringBuffer variableDeclarations = new StringBuffer();
            // imported multivector components
            Map<String, List<MvComponent>> mvComponents = new HashMap<String, List<MvComponent>>();

            // retrieve multivectors from previous sections
            if (gaalopFileCount > 0) {
                BufferedReader gaalopOutFile = new BufferedReader(new StringReader(gaalopOutFileVector.get(gaalopFileCount - 1)));

                while ((line = gaalopOutFile.readLine()) != null) {
                    // retrieve multivector declarations
                    {
                        final String mvSearchString = "#pragma gcd multivector ";
                        final int statementPos = line.indexOf(mvSearchString);
                        if (statementPos >= 0) {
                            final String mvName = line.substring(statementPos
                                    + mvSearchString.length());
                            mvComponents.put(mvName, new ArrayList<MvComponent>());
                        }
                    }

                    // retrieve multivector component declarations
                    {
                        final String mvCompSearchString =
                                "#pragma gcd multivector_component ";
                        final int statementPos = line.indexOf(mvCompSearchString);

                        if (statementPos >= 0) {
                            final Scanner lineStream = new Scanner(line.substring(statementPos
                                    + mvCompSearchString.length()));

                            final String mvName = lineStream.next();
                            MvComponent mvComp = new MvComponent();
                            mvComp.bladeHandle = lineStream.next();
                            mvComp.bladeName = lineStream.next();
                            mvComp.bladeArrayIndex = lineStream.nextInt();

                            mvComponents.get(mvName).add(mvComp);
                        }
                    }
                }

                // generate import declarations
                for (final String mvName : mvComponents.keySet()) {
                    variableDeclarations.append(mvName).append(" = 0");

                    for (final MvComponent mvComp : mvComponents.get(mvName)) {
                        variableDeclarations.append(" +").append(mvComp.bladeName);
                        variableDeclarations.append('*').append(mvName).append('_');
                        variableDeclarations.append(mvComp.bladeHandle);
                    }

                    variableDeclarations.append(";\n");
                }
            }

            // run Gaalop
            {
                // compose file
                StringBuffer inputFileStream = new StringBuffer();
                inputFileStream.append(variableDeclarations.toString()).append(LINE_END);
                inputFileStream.append(gaalopInFileVector.get(gaalopFileCount));

                System.out.println("compiling");

                // Configure the compiler
                CompilerFacade compiler = createCompiler();

                // Perform compilation
                final InputFile inputFile = new InputFile("inputFile", inputFileStream.toString());
                Set<OutputFile> outputFiles = compiler.compile(inputFile);

                StringBuffer gaalopOutFileStream = new StringBuffer();
                for (OutputFile output : outputFiles) {
                    gaalopOutFileStream.append(filterImportedMultivectorsFromExport(output.getContent(), mvComponents.keySet())).append(LINE_END);
                }

                gaalopOutFileVector.add(gaalopOutFileStream.toString());
            }
        }

        // compose output file
        {
            // log
            System.out.println("writing");

            // retrieve input file directory
            String inputFileDir;
            {
                int pos = inputFilePath.lastIndexOf('/');
                if (pos <= 0) {
                    pos = inputFilePath.lastIndexOf('\\');
                }
                assert (pos > 0);
                inputFileDir = inputFilePath.substring(0, pos + 1);
            }

            BufferedWriter outputFile = createFileOutputStringStream(outputFilePath);
            final BufferedReader inputFile = createFileInputStringStream(inputFilePath);
            Integer gaalopFileCount = 0;
            Integer lineCount = 1; // think one line ahead

            writeLinePragma(outputFile, lineCount++);
            while ((line = inputFile.readLine()) != null) {
                ++lineCount;

                if (line.indexOf("#include") >= 0 && line.indexOf('\"') >= 0) {
                    final int pos = line.indexOf('\"') + 1;
                    outputFile.write(line.substring(0, pos));
                    outputFile.write(inputFileDir);
                    outputFile.write(line.substring(pos));
                    outputFile.write(LINE_END);
                } // found gaalop line - insert intermediate gaalop file
                else if (line.indexOf("#pragma gcd begin") >= 0) {
                    // line pragma for compile errors
                    outputFile.write("#line ");
                    outputFile.write(lineCount.toString());
                    outputFile.write(" \"");
                    outputFile.write(inputFilePath);
                    outputFile.write("\"\n");

                    // merge optimized code
                    outputFile.write(gaalopOutFileVector.get(gaalopFileCount++));

                    // skip original code
                    while ((line = inputFile.readLine()) != null) {
                        ++lineCount;
                        if (line.contains("#pragma gcd end")) {
                            break;
                        }
                    }
                    writeLinePragma(outputFile, lineCount);
                } else {
                    outputFile.write(line);
                    outputFile.write(LINE_END);
                }
            }

            // close output file
            outputFile.close();
        }
    }

    private String filterImportedMultivectorsFromExport(final String content, final Set<String> importedMVs) throws Exception {
        final BufferedReader inStream = new BufferedReader(new StringReader(content));
        StringBuffer outStream = new StringBuffer();

        boolean skip = false;
        String line;
        while ((line = inStream.readLine()) != null) {

            // find multivector meta-statement
            int index = line.indexOf("#pragma gcd multivector");
            if (index >= 0) {
                // extract exported multivector
                final String exportedMV = line.substring(index).split(" ")[3];

                // determine if this MV was previously imported
                skip = importedMVs.contains(exportedMV);
            }
            
            // skip until next multivector meta-statement
            if (!skip) {
                outStream.append(line);
                outStream.append(LINE_END);
            }
            else
                System.out.println("Fehler");
        }

        return outStream.toString();
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
                if (externalOptimizerPath.length() != 0) {
                    if(plugin instanceof de.gaalop.maple.Plugin) {
                        ((de.gaalop.maple.Plugin) plugin).setMaplePathsByMapleBinaryPath(externalOptimizerPath);
                    }
                    else if(plugin instanceof de.gaalop.tba.Plugin) {
                        ((de.gaalop.tba.Plugin) plugin).optMaxima = true;
                        ((de.gaalop.tba.Plugin) plugin).maximaCommand = externalOptimizerPath;
                    }
                }

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
}
