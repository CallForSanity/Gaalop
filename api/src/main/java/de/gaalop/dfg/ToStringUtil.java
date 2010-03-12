package de.gaalop.dfg;

/**
 * This class contains internal utilities for the toString helper method.
 */
abstract class ToStringUtil {

    public static String bracketComposite(Expression expression) {
        if (expression.isComposite()) {
            return "(" + expression + ")";
        } else {
            return expression.toString();
        }
    }

}
