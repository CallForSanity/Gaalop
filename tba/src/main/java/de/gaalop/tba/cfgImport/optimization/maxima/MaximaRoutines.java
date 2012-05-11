/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.dfg.Expression;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaLexer;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaParser;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaTransformer;
import java.util.LinkedList;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 *
 * @author christian
 */
public class MaximaRoutines {
    
    /**
     * Fills a given list of MaximaInOut from an output of maxima
     * @param connected The list of MaximaInOut to be filled
     * @param output The output of maxima
     */
    public static void groupMaximaInAndOutputs(LinkedList<String> connected, MaximaOutput output) {

        StringBuilder sb = new StringBuilder();

        boolean input = false;
        for (String o : output) {
            if (o.startsWith("(%i")) {
                input = false;
                if (sb.length() != 0) {
                    connected.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                if (o.startsWith("(%o")) {
                    sb.append(o.substring(o.indexOf(")") + 2));
                    input = true;
                } else {
                    if (input) {
                        sb.append(o);
                    }
                }
            }
        }

        if (input && sb.length() > 0) {
            connected.add(sb.toString());
        }

    }
    
    /**
     * Returns an expression from a maxima output string.
     * @param maximaOut The maxima output string
     * @return The according expression
     * @throws RecognitionException
     */
    public static Expression getExpressionFromMaximaOutput(String maximaOut) throws RecognitionException {
        ANTLRStringStream inputStream = new ANTLRStringStream(maximaOut);
        MaximaLexer lexer = new MaximaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MaximaParser parser = new MaximaParser(tokenStream);
        MaximaParser.program_return parserResult = parser.program();

        CommonTreeNodeStream treeNodeStream = new CommonTreeNodeStream(parserResult.getTree());
        MaximaTransformer transformer = new MaximaTransformer(treeNodeStream);

        return transformer.expression();
    }
    
}
