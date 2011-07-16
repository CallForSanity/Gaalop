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

    class MvComponent {

        String bladeHandle;
        String bladeName;
        int bladeArrayIndex;
    };
    private Log log = LogFactory.getLog(Main.class);

    @Option(name = "-i", required = true, usage = "The input file.")
    private String inputFilePath;
    @Option(name = "-o", required = false, usage = "The output file.")
    private String outputFilePath;

    @Option(name = "-m", required = false, usage = "Sets the Maple binary path.")
    private String mapleBinaryPath = "";
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
            while (t.getCause() != null) {
                t = t.getCause();
            }

            System.err.println(t.getMessage());
        }
    }

    public static BufferedReader createFileInputStringStream(String fileName) {
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            return br;
        } catch (Exception e) {
            return null;
        }
    }

    public static BufferedWriter createFileOutputStringStream(String fileName) {
        try {
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fstream);

            return bw;
        } catch (Exception e) {
            return null;
        }
    }

    public void runGCD() throws Exception {

        String runPath;
        {
            File directory = new File (".");
            runPath = directory.getCanonicalPath();
        }

        // process input file
        // split into gaalop input files and save in memory
        List<String> gaalopInFileVector = new ArrayList<String>();
        String line;
        {
            BufferedReader inputFile = createFileInputStringStream(inputFilePath);
            while ((line = inputFile.readLine()) != null) {
                // found gaalop line
                if (line.indexOf("#pragma gcd begin") >= 0) {
                    StringBuffer gaalopInFileStream = new StringBuffer();

                    // read until end of optimized file
                    while ((line = inputFile.readLine()) != null) {
                        if (line.indexOf("#pragma gcd end") >= 0) {
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
        List<String> gaalopOutFileVector = new ArrayList<String>();
        StringBuffer variables = new StringBuffer();
        for (int gaalopFileCount = 0; gaalopFileCount < gaalopInFileVector.size(); ++gaalopFileCount) {
            // retrieve multivectors from previous sections
            if (gaalopFileCount > 0) {

                Vector<String> mvNames = new Vector<String>();
                Map<String, List<MvComponent>> mvComponents = new HashMap<String, List<MvComponent>>();
                BufferedReader gaalopOutFile = new BufferedReader(new StringReader(gaalopOutFileVector.get(gaalopFileCount)));

                while ((line = gaalopOutFile.readLine()) != null) {
                    // retrieve multivector declarations
                    {
                        String mvSearchString = "#pragma gcd multivector ";
                        int statementPos = line.indexOf(mvSearchString);
                        if (statementPos >= 0) {
                            mvNames.add(line.substring(statementPos
                                    + mvSearchString.length()));
                        }
                    }

                    // retrieve multivector component declarations
                    {
                        String mvCompSearchString =
                                "#pragma gcd multivector_component ";
                        int statementPos = line.indexOf(mvCompSearchString);
                        if (statementPos >= 0) {
                            Scanner lineStream = new Scanner(line.substring(statementPos
                                    + mvCompSearchString.length()));

                            String mvName = lineStream.next();
                            MvComponent mvComp = new MvComponent();
                            mvComp.bladeHandle = lineStream.next();
                            mvComp.bladeName = lineStream.next();
                            mvComp.bladeArrayIndex = lineStream.nextInt();

                            List<MvComponent> list = mvComponents.get(mvName);
                            if (list == null) {
                                list = new ArrayList<MvComponent>();
                                mvComponents.put(mvName, list);
                            }
                            list.add(mvComp);
                        }
                    }
                }

                // generate import declarations
                for (String mvName : mvNames) {
                    variables.append(mvName).append(" = 0");

                    for (MvComponent mvComp : mvComponents.get(mvName)) {
                        variables.append(" +").append(mvComp.bladeName);
                        variables.append('*').append(mvName).append('_');
                        variables.append(mvComp.bladeHandle);
                    }

                    variables.append(";\n");
                }
            }

            // run Gaalop
            {
                // compose file
                StringBuffer inputFileStream = new StringBuffer();
                inputFileStream.append(variables.toString()).append(LINE_END);
                inputFileStream.append(gaalopInFileVector.get(gaalopFileCount));

                // Configure the compiler
                CompilerFacade compiler = createCompiler();

                // Perform compilation
                InputFile inputFile = new InputFile("inputFile", inputFileStream.toString());
                Set<OutputFile> outputFiles = compiler.compile(inputFile);

                StringBuffer gaalopOutFileStream = new StringBuffer();
                for (OutputFile output : outputFiles)
                    gaalopOutFileStream.append(output.getContent()).append(LINE_END);
                
                gaalopOutFileVector.add(gaalopOutFileStream.toString());
            }
        }

        // compose output file
        {
            // retrieve input file directory
            String inputFileDir;
            {
                int pos = inputFilePath.lastIndexOf('/');
                if (pos <= 0)
                    pos = inputFilePath.lastIndexOf('\\');
                assert (pos > 0);
                inputFileDir = inputFilePath.substring(0, pos + 1);
            }

            BufferedWriter outputFile = createFileOutputStringStream(outputFilePath);
            BufferedReader inputFile = createFileInputStringStream(inputFilePath);
            Integer gaalopFileCount = 0;
            Integer lineCount = 1; // think one line ahead

            while ((line = inputFile.readLine()) != null) {
                ++lineCount;

                if (line.indexOf("#include") >= 0 && line.indexOf('\"') >= 0) {
                    int pos = line.indexOf('\"') + 1;
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
                    outputFile.write(gaalopOutFileVector.get(gaalopFileCount));

                    // skip original code
                    while ((line = inputFile.readLine()) != null) {
                        ++lineCount;
                        if (line.indexOf("#pragma gcd end") >= 0) {
                            break;
                        }
                    }

                    // line pragma for compile errors
                    outputFile.write("#line ");
                    outputFile.write(lineCount.toString());
                    outputFile.write(" \"");
                    outputFile.write(inputFilePath);
                    outputFile.write("\"\n");
                } else {
                    outputFile.write(line);
                    outputFile.write(LINE_END);
                }
            }

            outputFile.close();
        }
    }

    private void writeFile(OutputFile output) throws FileNotFoundException,
            UnsupportedEncodingException {
//        if (outputDirectory.equals("-")) {
//            printFileToConsole(output);
//        } else {
//            File outFile;
//            if (outputDirectory.length() == 0) {
//                outFile = new File(output.getName());
//            } else {
//                // NOTE: output file does not return actual name, but the full path of the file
//                File tempFile = new File(output.getName());
//                outFile = new File(outputDirectory, tempFile.getName());
//            }
//            PrintWriter writer = new PrintWriter(outFile, output.getEncoding().name());
//            writer.print(output.getContent());
//            writer.close();
//        }
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
                if (mapleBinaryPath.length() != 0) {
                    plugin.setMaplePathsByMapleBinaryPath(mapleBinaryPath);
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
