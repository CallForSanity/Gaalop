/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaLexer;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaParser;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaTransformer;
import de.gaalop.visitors.ReplaceVisitor;
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
        return unfoldSimplePows(transformer.expression());
    }

    /**
     * Unfolds simple pow expressions, i.e. pow(a,3) -> a*a*a,
     * where exponent <= 3 and constant.
     * @param expression The expression to unfold the pow operations
     */
    private static Expression unfoldSimplePows(Expression expression) {
        return new UnfoldingSimplePows().replace(expression);
    }
    
    private static class UnfoldingSimplePows extends ReplaceVisitor {

        @Override
        public void visit(Exponentiation node) {
            
            if (node.getRight() instanceof FloatConstant) {
                double constant = ((FloatConstant) node.getRight()).getValue();
                int integer = (int) Math.round(constant);
                if (integer>0 && integer<=3) 
                    if (Math.abs(constant-integer) < 10E-7) {
                        //Unfold
                        //TODO chs set a new temporary variable, if left is of big height
                        switch (integer) {
                            case 1:
                                result = node.getLeft();
                                return;
                            case 2:
                                result = new Multiplication(node.getLeft(), node.getLeft());
                                return;
                            case 3:
                                result = new Multiplication(new Multiplication(node.getLeft(),node.getLeft()), node.getLeft());
                                return;    
                        }
                    }
            }
            
            super.visit(node);
        }
        
        
    }

    
}
