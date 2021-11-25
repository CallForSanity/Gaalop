package de.gaalop.visitors;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.OuterProduct;

/**
 *
 * @author Christian Steinmetz
 */
public class BladePrinter {
    
    public static String print(Expression expression) {
        if (expression instanceof OuterProduct) {
            OuterProduct outerProduct = (OuterProduct) expression;
            return print(outerProduct.getLeft()) + "^" + print(outerProduct.getRight());
        } else {
            return expression.toString();
        }
    }
    
}
