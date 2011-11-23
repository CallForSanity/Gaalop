package de.gaalop.algebra;

import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.clucalc.input.CluCalcCodeParser;
import de.gaalop.dfg.Expression;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import de.gaalop.clucalc.input.Plugin;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses a function file
 * @author Christian Steinmetz
 */
public class FunctionParser {

    /**
     * Parses functions from an InputStream
     * @param inputStream The InputStream
     * @return The functions
     * @throws IOException
     */
    public static LinkedList<Function> parseFunctions(InputStream inputStream) throws IOException {
        LinkedList<Function> result = new LinkedList<Function>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line=reader.readLine()) != null) {
            Function f = parseFunction(line);
            if (f!=null)
                result.add(f);
        }
        reader.close();
        return result;
    }

    /**
     * Parses a function from a line
     * @param line The line to parse
     * @return The parsed function; null, if the line does not contain any function
     */
    private static Function parseFunction(String line) {
        try {
            String[] parts = line.split(":=");
            if (parts.length != 2) {
                return null;
            }
            String[] parts2 = parts[0].split("\\(");
            return new Function(parts2[0].trim(), parseArgs(parts2[1].split("\\)")[0].trim()), parseExpression(parts[1].trim()));
        } catch (CodeParserException ex) {
            Logger.getLogger(FunctionParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Parses arguments from a string
     * @param string The string to be parsed
     * @return The parsed arguments
     */
    private static LinkedList<String> parseArgs(String string) {
        String[] parts = string.split(",");
        LinkedList<String> args = new LinkedList<String>();

        for (String part: parts) {
            String partTrimmed = part.trim();
            if (!partTrimmed.isEmpty())
                args.add(partTrimmed);
        }
        
        return args;
    }

    /**
     * Parses an expression from a string
     * @param string The string to be parsed
     * @return The parsed expression
     */
    private static Expression parseExpression(String string) throws CodeParserException {
        InputFile inputFile = new InputFile("parse", "?parse = "+string+";\n");
        ControlFlowGraph graph = new Plugin().createCodeParser().parseFile(inputFile);
        FunctionReplaceVisitor visitor = new FunctionReplaceVisitor(new FunctionWrapper(new LinkedList<Function>()));
        graph.accept(visitor);
        AssignmentNode node = (AssignmentNode) graph.getStartNode().getSuccessor();
        return node.getValue();
    }

}
