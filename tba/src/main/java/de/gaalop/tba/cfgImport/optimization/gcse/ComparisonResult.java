package de.gaalop.tba.cfgImport.optimization.gcse;

/**
 * Represents the result of an arithmetic comparision of two expressions
 * @author CSteinmetz15
 */
public enum ComparisonResult {
    
    /**
     * The two expressions are different
     */
    DIFFERENT, 
    
    /**
     * The expression1 is the negation of expression2
     */
    NEGATED,
    
    /**
     * The two expressions are equal
     */
    EQUAL;

}
