package de.gaalop.clucalc.algebra;

import de.gaalop.dfg.Expression;

/**
 * This interface describes the required methods for a CluCalc Algebra mode.
 */
public interface AlgebraMode {

    /**
     * Gets the name of the CluCalc method that represents this Algebra mode.
     * <p/>
     * For example: <code>DefVarsN3</code>.
     *
     * @return A string that contains the (case sensitive) name of the method that
     *         defines the constants in this Algebra.
     */
    String getDefinitionMethod();

    /**
     * Gets the signature of the underlying algebra. The signature is the square for each base
     * vector. The dimension of the algebra equals the length of the resulting array.
     *
     * @return An array that contains -1, 0 or 1 for every base vector in the algebra.
     */
    int[] getSignature();

    /**
     * Gets the dimension of this algebra. The value returned by this method must equal the length
     * of the array returned by <code>getSignature</code>.
     *
     * @return The dimension of this algebra.
     */
    int getDimension();

    /**
     * Checks whether a given identifier is a constant in this algebra. This includes all base
     * vectors of this algebra.
     *
     * @param identifier A string containing the name of the identifier. This is case sensitive.
     * @return True if the given identifier is a constant in this algebra.
     */
    boolean isConstant(String identifier);

    /**
     * Creates an Expression node for the given constant identifier.
     *
     * @param identifier The identifier identifying the requested constant value.
     * @return A unique Expression node that is not used elsewhere and that represents the requested constant.
     * @throws IllegalArgumentException If the requested constant does not exist. Please check if the constant exists
     *                                  using the isConstant method of this interface.
     */
    Expression getConstant(String identifier);
}
