package de.gaalop.dfg;

/**
 * An enumeration of math functions that this compiler supports without embedding
 * a library. A backend does not need to implement these functions itself but
 * can import a library to implement them.
 * <p/>
 * All mathematical functions in this enumeration take one double argument and return
 * one double result.
 *
 * @see de.gaalop.dfg.MathFunctionCall
 */
public enum MathFunction {
    /**
     * This mathematical function returns the absolute value of its argument.
     */
    ABS,

    /**
     * Evaluates the arc cosine of an angle.
     */
    ACOS,

    /**
     * Evaluates the arc sine of an angle.
     */
    ASIN,

    /**
     * Evaluates the arc tangent of an angle.
     */
    ATAN,

    /**
     * Evaluates the smallest integer value that is larger or equal to the given value.
     */
    CEIL,

    /**
     * Evaluates the cosine of the given angle.
     */
    COS,

    /**
     * Evaluates the exponential of a scalar value.
     */
    EXP,

    /**
     * Evaluates the factorial of an integer number.
     */
    FACT,

    /**
     * Evaluates the largest integer value that is smaller or equal to the given value.
     */
    FLOOR,

    /**
     * Evaluates the natural logarithm of a value.
     */
    LOG,

    /**
     * Evaluates the sine of an angle.
     */
    SIN,

    /**
     * Evaluates the square root of a value.
     */
    SQRT,

    /**
     * Evaluates the tangent of an angle.
     */
    TAN,

    /**
     * Inverts the multivector.
     */
    INVERT;
    
    @Override
    public String toString() { 
    	return super.toString().toLowerCase();
    }
}
