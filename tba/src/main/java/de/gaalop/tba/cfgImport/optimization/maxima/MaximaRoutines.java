package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaLexer;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaParser;
import de.gaalop.visitors.ReplaceVisitor;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 *
 * @author Christian Steinmetz
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
     */
    public static Expression getExpressionFromMaximaOutput(String maximaOut) {
        
        try {
            ANTLRInputStream inputStream = new ANTLRInputStream(new StringReader(maximaOut));
            MaximaLexer lexer = new MaximaLexer(inputStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            MaximaParser parser = new MaximaParser(tokenStream);
            
            MaximaVisitor visitor = new MaximaVisitor();
            Expression expression = visitor.visit(parser.expression());
            
            
            return unfoldSimplePows(expression);
            
        } catch (IOException ex) {
            Logger.getLogger(MaximaRoutines.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
