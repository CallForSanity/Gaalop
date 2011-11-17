package de.gaalop.gpc;

import de.gaalop.*;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MacroCall;
import org.antlr.runtime.RecognitionException;
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
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * Main class of the Gaalop command line interface. Arguments are parsed using the args4j plugin.
 *
 * @author Patrick Charrier
 *
 */
public class Main {
    protected static final String gpcZero = "0.0f";

    protected void composeOutputFile(Vector<String> gaalopOutFileVector) throws IOException, RecognitionException {
        String line;
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
        
        // process line by line
        Map<String, Map<String,String>> mvComponents = new HashMap<String, Map<String,String>>();
        BufferedWriter outputFile = createFileOutputStringStream(outputFilePath);
        final BufferedReader inputFile = createFileInputStringStream(inputFilePath);
        Integer gaalopFileCount = 0;
        Integer lineCount = 1;
        writeLinePragma(outputFile, lineCount++);
        while ((line = inputFile.readLine()) != null) {
            if (line.indexOf("#include") >= 0 && line.indexOf('\"') >= 0) {
                final int pos = line.indexOf('\"') + 1;
                outputFile.write(line.substring(0, pos));
                outputFile.write(inputFileDir);
                outputFile.write(line.substring(pos));
                outputFile.write(LINE_END);
            } // found gaalop line - insert intermediate gaalop file
            else if (line.indexOf(gpcBegin) >= 0) {
                // line pragma for compile errors
                writeLinePragma(outputFile, lineCount++); // we skipped gcd begin line

                // merge optimized code
                outputFile.write(gaalopOutFileVector.get(gaalopFileCount));

                // skip original code
                while ((line = inputFile.readLine()) != null) {
                    if (line.contains(gpcEnd))
                        break;
                    ++lineCount;
                }

                // line pragma for compile errors
                writeLinePragma(outputFile, lineCount++); // we skipped gcd end line

                // scan block
                scanBlock(gaalopOutFileVector, gaalopFileCount, mvComponents);
                
                ++gaalopFileCount;
            } else if(line.indexOf(gpcMvGetBladeCoeff) >= 0) {
                CommandReplacer cp = new CommandReplacer(line,inputFile,gpcMvGetBladeCoeff);
                lineCount += cp.getLineCount();
                
                // get blade coeff array entry
                String bladeCoeffArrayEntry = getMvBladeCoeffArrayEntry(mvComponents,
                                                                    cp.getCommandParams()[0],
                                                                    cp.getCommandParams()[1]);
                
                outputFile.write(cp.replace(bladeCoeffArrayEntry));
                outputFile.write(LINE_END);
            } else if(line.indexOf(gpcMvToArray) >= 0)
                lineCount = processMvToArray(line, inputFile, outputFile, mvComponents, lineCount); 
            else if(line.indexOf(gpcMvToStridedArray) >= 0)
                lineCount = processMvToStridedArray(line, inputFile, outputFile, mvComponents, lineCount); 
            else if(line.indexOf(gpcMvToVec) >= 0)
                lineCount = processMvToVec(line, inputFile, outputFile, mvComponents, lineCount); 
            else if(line.indexOf(gpcMvToVecArray) >= 0)
                lineCount = processMvToVecArray(line, inputFile, outputFile, mvComponents, lineCount); 
            else {
                outputFile.write(line);
                outputFile.write(LINE_END);
                ++lineCount; // we read one line
            }
        }
        // close output file
        outputFile.close();
    }

    protected String getMvBladeCoeffArrayEntry(Map<String, Map<String, String>> mvComponents, final String mv, final String blade) {
        // get blade coeff array entry
        String bladeCoeffArrayEntry = mvComponents.get(mv).get(blade);
        // handle the case that this blade coeff is zero
        if(bladeCoeffArrayEntry == null)
            bladeCoeffArrayEntry = gpcZero;
        return bladeCoeffArrayEntry;
    }

    protected Integer processMvToArray(String line, final BufferedReader inputFile, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents, Integer lineCount) throws RecognitionException, IOException {
        // read until end of statement
        String statement = readUntilEndOfStatement(line, inputFile);
        // parse assignment
        AssignmentNode assignment = parseAssignment(statement);
        // get array name
        String array = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        Integer count = 0;
        while(it.hasNext()) {
            outputFile.write(array);
            outputFile.write('[');
            outputFile.write((count++).toString());
            outputFile.write(']');
            outputFile.write(" = ");                    
            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,
                                                       it.next().toString()));                                        
            outputFile.write(";\n");
        }
        outputFile.write(LINE_END);
        ++lineCount; // we read one line
        return lineCount;
    }

    protected Integer processMvToStridedArray(String line, final BufferedReader inputFile, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents, Integer lineCount) throws RecognitionException, IOException {
        // read until end of statement
        String statement = readUntilEndOfStatement(line, inputFile);
        // parse assignment
        AssignmentNode assignment = parseAssignment(statement);
        // get array name
        String array = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        final int stride = Integer.parseInt(it.next().toString());
        Integer index = 0;
        while(it.hasNext()) {
            outputFile.write(array);
            outputFile.write('[');
            outputFile.write(index.toString());
            outputFile.write(']');
            outputFile.write(" = ");                    
            
            // update index
            index+=stride;

            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,
                                                       it.next().toString()));                                        
            outputFile.write(";\n");
        }
        outputFile.write(LINE_END);
        ++lineCount; // we read one line
        return lineCount;
    }
    
    protected String getOpenCLIndex(Integer index) {
        if(index < 10)
            return index.toString();
        else switch(index) {
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
        }
        
        assert(false);
        return "fail";
    }

    protected Integer processMvToVec(String line, final BufferedReader inputFile, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents, Integer lineCount) throws RecognitionException, IOException {
        // read until end of statement
        String statement = readUntilEndOfStatement(line, inputFile);
        // parse assignment
        AssignmentNode assignment = parseAssignment(statement);
        // get array name
        final String vec = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        outputFile.write(vec);
        outputFile.write(" = make_float4(");
        outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                   mv,
                                                   "e1"));                                        
        outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                   mv,
                                                   "e2"));                                        
        outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                   mv,
                                                   "e3"));                                        
        outputFile.write(",0);\n");
        ++lineCount; // we read one line
        return lineCount;
    }

    protected Integer processMvToVecArray(String line, final BufferedReader inputFile, BufferedWriter outputFile, Map<String, Map<String, String>> mvComponents, Integer lineCount) throws RecognitionException, IOException {
        // read until end of statement
        String statement = readUntilEndOfStatement(line, inputFile);
        // parse assignment
        AssignmentNode assignment = parseAssignment(statement);
        // get array name
        String vec = assignment.getVariable().getName();
        // print array assignments
        Iterator<Expression> it = ((MacroCall)assignment.getValue()).getArguments().iterator();
        final String mv = it.next().toString();
        int count = 0;
        while(it.hasNext()) {
            outputFile.write(vec);
            outputFile.write(".s"); // TODO recognize vector size and assign with make_float
            outputFile.write(getOpenCLIndex(count++));
            outputFile.write(" = ");
            outputFile.write(getMvBladeCoeffArrayEntry(mvComponents,
                                                       mv,
                                                       it.next().toString()));                                        
            outputFile.write(";\n");
        }
        outputFile.write(LINE_END);
        ++lineCount; // we read one line
        return lineCount;
    }

    protected String readUntilEndOfStatement(String line, final BufferedReader inputFile) throws IOException {
        // read until end of statement
        StringBuffer statementBuffer = new StringBuffer();
        statementBuffer.append(line).append(LINE_END);
        while((line = inputFile.readLine()) != null && !line.contains(";"))
            statementBuffer.append(line).append(LINE_END);
        
        return statementBuffer.toString();
    }

    protected AssignmentNode parseAssignment(String line) throws RecognitionException {
        ANTLRStringStream inputStream = new ANTLRStringStream(line);
        GPCLexer lexer = new GPCLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        GPCParser parser = new GPCParser(tokenStream);
        GPCParser.program_return parserResult = parser.program();
        CommonTreeNodeStream treeNodeStream = new CommonTreeNodeStream(parserResult.getTree());
        GPCTransformer transformer = new GPCTransformer(treeNodeStream);
        AssignmentNode assignment = transformer.assignment();
        return assignment;
    }

    protected Vector<String> processOptimizationBlocks(List<String> gaalopInFileVector) throws Exception {
        String line;
        
        // process gaalop files - call gaalop
        // imported multivector components
        Map<String, Map<String,String>> mvComponents = new HashMap<String, Map<String,String>>();
        Vector<String> gaalopOutFileVector = new Vector<String>();
        for (int gaalopFileCount = 0; gaalopFileCount < gaalopInFileVector.size(); ++gaalopFileCount) {

            // import declarations
            StringBuffer variableDeclarations = new StringBuffer();

            // retrieve multivectors from previous block
            if (gaalopFileCount > 0) {
                // scan previous block
                scanBlock(gaalopOutFileVector, gaalopFileCount-1, mvComponents);
                
                // generate import declarations
                generateImportDeclarations(mvComponents, variableDeclarations);
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
                System.out.println(inputFile.getContent());
                Set<OutputFile> outputFiles = compiler.compile(inputFile);

                StringBuffer gaalopOutFileStream = new StringBuffer();
                for (OutputFile output : outputFiles) {
                    String content = output.getContent();
                    content = filterHashedBladeCoefficients(content);
                    content = filterImportedMultivectorsFromExport(content, mvComponents.keySet());
                    gaalopOutFileStream.append(content).append(LINE_END);
                }

                gaalopOutFileVector.add(gaalopOutFileStream.toString());
            }
        }
        return gaalopOutFileVector;
    }

    protected void generateImportDeclarations(Map<String, Map<String, String>> mvComponents, StringBuffer variableDeclarations) {
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

    protected void scanBlock(Vector<String> gaalopOutFileVector, int gaalopFileNumber, Map<String, Map<String,String>> mvComponents) throws IOException {
        String line;
        BufferedReader gaalopOutFile = new BufferedReader(new StringReader(gaalopOutFileVector.get(gaalopFileNumber)));
        while ((line = gaalopOutFile.readLine()) != null) {
            // retrieve multivector declarations
            {
                final int statementPos = line.indexOf(mvSearchString);
                if (statementPos >= 0) {
                    final String mvName = line.substring(statementPos
                            + mvSearchString.length());
                    mvComponents.put(mvName, new HashMap<String,String>());
                }
            }

            // retrieve multivector component declarations
            {
                final int statementPos = line.indexOf(mvCompSearchString);

                if (statementPos >= 0) {
                    final Scanner lineStream = new Scanner(line.substring(statementPos
                            + mvCompSearchString.length()));

                    // read and save mvName mvBladeCoeffName bladeName
                    mvComponents.get(lineStream.next()).put(lineStream.next(), lineStream.next());
                }
            }
        }
    }

    protected List<String> readInputFile() throws IOException {
        // process input file
        // split into gaalop input files and save in memory
        List<String> gaalopInFileVector = new ArrayList<String>();
        String line;
        {
            final BufferedReader inputFile = createFileInputStringStream(inputFilePath);
            while ((line = inputFile.readLine()) != null) {
                // found gaalop line
                if (line.contains(gpcBegin)) {
                    StringBuffer gaalopInFileStream = new StringBuffer();

                    // read until end of optimized file
                    while ((line = inputFile.readLine()) != null) {
                        if (line.contains(gpcEnd)) {
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
        return gaalopInFileVector;
    }

    private void writeLinePragma(BufferedWriter outputFile, Integer lineCount) throws IOException {
        // line pragma for compile errors
        outputFile.write("#line ");
        outputFile.write(lineCount.toString());
        outputFile.write(" \"");
        outputFile.write(inputFilePath);
        outputFile.write("\"\n");
    }
    
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
    private static final String gpcBegin = "#pragma gcd begin";
    private static final String gpcEnd = "#pragma gcd end";

    private static final String gpcMvFromArray = "mv_from_array";
    
    private static final String gpcMvGetBladeCoeff = "mv_get_bladecoeff";
    private static final String gpcMvToArray = "mv_to_array";
    private static final String gpcMvToStridedArray = "mv_to_stridedarray";
    private static final String gpcMvToVec = "mv_to_vec";
    private static final String gpcMvToVecArray = "mv_to_vecarray";
    
    private static final String mvSearchString = "//#pragma gcd multivector ";
    private static final String mvCompSearchString = "//#pragma gcd multivector_component ";

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
            main.runGPC();
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

    public void runGPC() throws Exception {

        // read input file and split into optimization blocks
        List<String> gaalopInFileVector = readInputFile();
        
        // process optimization blocks
        Vector<String> gaalopOutFileVector = processOptimizationBlocks(gaalopInFileVector);

        // compose output file
        composeOutputFile(gaalopOutFileVector);
    }

    private String filterImportedMultivectorsFromExport(final String content, final Set<String> importedMVs) throws Exception {
        final BufferedReader inStream = new BufferedReader(new StringReader(content));
        StringBuffer outStream = new StringBuffer();

        boolean skip = false;
        String line;
        while ((line = inStream.readLine()) != null) {

            // find multivector meta-statement
            int index = line.indexOf(mvSearchString); // will also find components
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
            } else if(importedMVs.isEmpty())
                System.err.println("Internal Error: GCD Should not remove multivectors if there are none imported!");
        }

        return outStream.toString();
    }
    
    private String filterHashedBladeCoefficients(final String content) throws Exception {
        final BufferedReader inStream = new BufferedReader(new StringReader(content));
        StringBuffer outStream = new StringBuffer();

        String line;
        while ((line = inStream.readLine()) != null) {
            for(Entry<String,String> entry : NameTable.getInstance().getTable().entrySet())
                line = line.replace(entry.getKey(),entry.getValue());
            
            outStream.append(line).append(LINE_END);
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
                } else if(plugin instanceof de.gaalop.tba.Plugin)
                    ((de.gaalop.tba.Plugin) plugin).optMaxima = false;

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
