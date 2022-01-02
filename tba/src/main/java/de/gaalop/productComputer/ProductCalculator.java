package de.gaalop.productComputer;

/**
 * Defines an interface for calculating a product of two blades
 * @author Christian Steinmetz
 */
public interface ProductCalculator {

    /**
     * Calculates the product of two blades
     * @param b1 The first blade
     * @param b2 The second blade
     * @param result The result of the product
     * @param bitCount The maximum number of bits
     * @param squareMask The signature
     */
    public void calcProduct(SignedBlade b1, SignedBlade b2, SumOfBlades result, int bitCount, byte[] squareMask);

}
