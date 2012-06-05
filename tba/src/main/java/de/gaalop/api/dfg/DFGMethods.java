package de.gaalop.api.dfg;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Expression;

/**
 * Provides methods on expressions
 * @author Christian Steinmetz
 */
public class DFGMethods {

    /**
     * Creates a single expression of expression array, which represents the summands
     * @param arr The expression array
     * @return The created expression
     */
    public static Expression exprArrToAddition(Expression[] arr) {
        if (arr.length == 1) {
            return arr[0];
        } else {
            Addition r = new Addition(arr[0], null);
            Addition cur = r;
            for (int i=1;i<arr.length-1;i++) {
                Addition add = new Addition(arr[i], null);
                cur.setRight(add);
                cur = add;
            }
            cur.setRight(arr[arr.length-1]);
            return r;
        }
    }

}
