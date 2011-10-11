package de.gaalop.tba;

/**
 * Defines an interface for a multiplication table
 * @author Christian Steinmetz
 */
public interface IMultTable {

    /**
     * Creates a new table with a given dimension.
     * The dimension equals to the number of blades in the algebra
     *
     * @param dimension The dimension of the table
     */
    public void createTable(int dimension);

    /**
     * Returns the product of two blades with specified indices in the algebra
     * @param factor1 The index of the blade of the first factor
     * @param factor2 The index of the blade of the second factor
     * @return The product
     */
    public Multivector getProduct(Integer factor1, Integer factor2);

    /**
     * Sets the product of two blades with specified indices in the algebra
     * @param factor1 The index of the blade of the first factor
     * @param factor2 The index of the blade of the second factor
     * @param product The product
     */
    public void setProduct(Integer factor1, Integer factor2, Multivector product);
}
