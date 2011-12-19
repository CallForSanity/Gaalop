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
import java.util.StringTokenizer;
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
    public Log log = LogFactory.getLog(Main.class);
    
    @Option(name = "-i", required = true, usage = "The input file.")
    public static String inputFilePath;
    @Option(name = "-o", required = true, usage = "The output file.")
    public static String outputFilePath;
    @Option(name = "-m", required = false, usage = "Sets an external optimzer path. This can be either Maple Binary Path or Maxima Path")
    public static String externalOptimizerPath = "";
    @Option(name = "-parser", required = false, usage = "Sets the class name of the code parser plugin that should be used.")
    public static String codeParserPlugin = "de.gaalop.clucalc.input.Plugin";
    @Option(name = "-generator", required = false, usage = "Sets the class name of the code generator plugin that should be used.")
    public static String codeGeneratorPlugin = "de.gaalop.compressed.Plugin";
    @Option(name = "-optimizer", required = false, usage = "Sets the class name of the optimization strategy plugin that should be used.")
    public static String optimizationStrategyPlugin = "de.gaalop.maple.Plugin";
    @Option(name = "-algebra", required = false, usage = "Sets the class name of the algebra strategy plugin that should be used.")
    public static String algebraStrategyPlugin = "de.gaalop.algebra.Plugin";

    public static String PATH_SEP = "/";
    public static char LINE_END = '\n';
    
    public static final String gpcBegin = "#pragma gpc begin";
    public static final String gpcEnd = "#pragma gpc end";

    public static final String gpcMvFromArray = "mv_from_array";
    public static final String gpcMvFromStridedArray = "mv_from_stridedarray";
    public static final String gpcMvFromVector = "mv_from_vector";
    
    public static final String gpcMvGetBladeCoeff = "mv_get_bladecoeff";
    public static final String gpcMvToArray = "mv_to_array";
    public static final String gpcMvToStridedArray = "mv_to_stridedarray";
    public static final String gpcMvToVector = "mv_to_vecarray";
    public static final String gpcZero = "0.0f";
    
    public static final String mvSearchString = "//#pragma gpc multivector ";
    public static final String mvCompSearchString = "//#pragma gpc multivector_component ";
    
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
        List<String> gaalopInFileVector = InputFilesComposer.readInputFile();

        // process optimization blocks
        Vector<String> gaalopOutFileVector = BlockTransformer.processOptimizationBlocks(gaalopInFileVector);
        
        // compose output file
        OutputFileComposer.composeOutputFile(gaalopOutFileVector);
    }
}
