package de.gaalop.gpc;

import de.gaalop.algebra.AlStrategy;
import de.gaalop.cfg.AlgebraDefinitionFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Vector;

/**
 * Main class of the Gaalop command line interface. Arguments are parsed using the args4j plugin.
 *
 * @author Patrick Charrier
 *
 */
public class Main {
    public Log log = LogFactory.getLog(Main.class);
    
    public static String inputFilePath;
    public static String outputFilePath;
    public static String externalOptimizerPath;
    public static String codeParserPlugin;
    public static String codeGeneratorPlugin;
    public static String optimizationStrategyPlugin;
    public static String globalSettingsStrategyPlugin;
    public static String algebraStrategyPlugin;
    public static String visualizerStrategyPlugin;
    public static boolean writeLinePragmas;
    public static boolean useDoubles;
    public static String algebraName;    
    public static String algebraBaseDirectory;

    public static void runGPC() throws Exception {
        InputFilesComposer.algebraDefinitionFile = new AlgebraDefinitionFile();
        if (!AlStrategy.loadAlgebra(InputFilesComposer.algebraDefinitionFile, false, !"".equals(algebraBaseDirectory), algebraBaseDirectory, algebraName, null))
            throw new Exception("Can't load algebra!");

        // read input file and split into optimization blocks
        List<String> gaalopInFileVector = InputFilesComposer.readInputFile();

        // process optimization blocks
        Vector<String> gaalopOutFileVector = BlockTransformer.processOptimizationBlocks(gaalopInFileVector);
        
        // compose output file
        OutputFileComposer.composeOutputFile(gaalopOutFileVector);
    }
}
