package de.gaalop.common;


import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides IO methods for control-flow graphs
 * @author christian
 */
public class GraphIOMethods {

     /**
      * Reads a graph from a CLUScript
      * @param filename The filename of the CLUScript, which should be loaded
      * @return The graph which was created from the given CLUScript
      */
     public static ControlFlowGraph readGraphFromClu(String filename) {
        try {
            File fIn = new File(filename);
            InputFile inputFile = new InputFile(fIn.getName(), FileMethods.readFile(fIn));
            de.gaalop.clucalc.input.Plugin pluginInput = new de.gaalop.clucalc.input.Plugin();
            CodeParser parser = pluginInput.createCodeParser();
            try {
                return parser.parseFile(inputFile);
            } catch (CodeParserException ex) {
                Logger.getLogger(GraphIOMethods.class.getName()).log(Level.SEVERE, "CodeParserException", ex);
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(GraphIOMethods.class.getName()).log(Level.SEVERE, "IOException", ex);
            return null;
        }
    }

    /**
     * Writes a graph to a given file in a specified format.
     *
     * If there is more than one file to be outputted,
     * then a number is appended to the filename, starting with 0
     *
     * @param graph The graph, which should be outputted in the file
     * @param filename The filename of the output file
     * @param plugin The used CodeGeneratorPlugin for producing the output of the graph
     */
    public static void writeGraphToFile(ControlFlowGraph graph, String filename, CodeGeneratorPlugin plugin) {
        CodeGenerator generator = plugin.createCodeGenerator();
        try {
            Set<OutputFile> outFiles = generator.generate(graph);

            if (outFiles.size() == 1) {
                 try {
                    FileMethods.writeFile(new File(filename), outFiles.iterator().next().getContent());
                } catch (IOException ex) {
                    Logger.getLogger(GraphIOMethods.class.getName()).log(Level.SEVERE, "IOException", ex);
                }
            } else {
                // numerate output files
                int i=0;
                for (OutputFile curFile: outFiles) {
                    try {
                        FileMethods.writeFile(new File(filename+i), curFile.getContent());
                    } catch (IOException ex) {
                        Logger.getLogger(GraphIOMethods.class.getName()).log(Level.SEVERE, "IOException", ex);
                    }
                    i++;
                }
            }

        } catch (CodeGeneratorException ex) {
            Logger.getLogger(GraphIOMethods.class.getName()).log(Level.SEVERE, "CodeGeneratorException", ex);
        }

    }

}
