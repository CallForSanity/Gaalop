package de.gaalop.productComputer;


/**
 * Calculates the inner product of two blades
 * @author Christian Steinmetz
 */
public class InnerProductCalculator implements ProductCalculator {

    /**
     * Calculates the inner product of two one grade blades
     * @param b1 The first blade
     * @param b2 The second blade
     * @param result The result of the inner product
     * @param bitCount The maximum number of bits
     * @param squareMask The signature
     * @return true, if the result is not empty; false, otherwise
     */
    private boolean calcInner1Product1(SignedBlade b1, SignedBlade b2, SignedBlade result, int bitCount, byte[] squareMask) {
        result.clear();
        result.coefficient = b1.coefficient * b2.coefficient;
        Blade aAndB = new Blade(bitCount, b1);
        aAndB.and(b2);
        if (aAndB.isEmpty()) {
            return false;
        }
        for (int index=0;index<bitCount;index++)
            if (aAndB.get(index)) {
                result.coefficient *= squareMask[index];
                return (squareMask[index] != 0);
            }
        
        throw new IllegalStateException("InnerProductCalculator.calcInner1Product1: state not allowed");
    }

    /**
     * Calculates the inner product of one one-grade blade and one two-or-more-grade blade
     * @param b1 The first blade (grade one)
     * @param b2 The second blade (grade two-or-more)
     * @param result The result of the inner product
     * @param bitCount The maximum number of bits
     * @param squareMask The signature
     * @return true, if the result is not empty; false, otherwise
     */
    private boolean calcInner1Productn(SignedBlade b1, SignedBlade b2, SignedBlade result, int bitCount, byte[] squareMask) {
        Blade aAndBMasked2 = new Blade(bitCount, b1);
        aAndBMasked2.and(b2);
        if (!aAndBMasked2.isEmpty()) {
            result.coefficient = b1.coefficient * b2.coefficient;
            result.clear();
            result.or(b1);
            result.xor(b2);

            boolean negate = false;
            int i = 0;
            while (!b1.get(i)) {
                if (b2.get(i)) {
                    negate = !negate;
                }
                i++;
            }

            if (negate) {
                result.coefficient *= -1;
            }
            
            for (int index = 0;index<bitCount;index++)
                if (aAndBMasked2.get(index)) {
                    result.coefficient *= squareMask[index];
                    if (squareMask[index] == 0)
                        return false;
                }
            
            return true;
        }
        return false;
    }

    /**
     * Calculates the inner product of one two-or-more-grade blade and one one-grade blade
     * @param b1 The first blade (grade two-or-more)
     * @param b2 The second blade (grade one)
     * @param result The result of the inner product
     * @param bitCount The maximum number of bits
     * @param squareMask The signature
     * @return true, if the result is not empty; false, otherwise
     */
    private boolean calcInnernProduct1(SignedBlade b1, SignedBlade b2, SignedBlade result, int bitCount, byte[] squareMask) {
        Blade aAndBMasked3 = new Blade(bitCount, b1);
        aAndBMasked3.and(b2);
        if (!aAndBMasked3.isEmpty()) {
            result.coefficient = b1.coefficient * b2.coefficient;
            result.clear();
            result.or(b1);
            result.xor(b2);

            boolean negate = false;
            int i = bitCount - 1;
            while (!b2.get(i)) {
                if (b1.get(i)) {
                    negate = !negate;
                }
                i--;
            }

            if (negate) {
                result.coefficient *= -1;
            }
            
            for (int index = 0;index<bitCount;index++)
                if (aAndBMasked3.get(index)) {
                    result.coefficient *= squareMask[index];
                    if (squareMask[index] == 0)
                        return false;
                }
            
            return true;
        }
        return false;
    }

    /**
     * Calculates the inner product of two two-or-more-grade blades
     * @param b1 The first blade
     * @param b2 The second blade
     * @param result The result of the inner product
     * @param bitCount The maximum number of bits
     * @param squareMask The signature
     * @return true, if the result is not empty; false, otherwise
     */
    private boolean calcInnernProductn(SignedBlade b1, SignedBlade b2, SignedBlade result, int bitCount, byte[] squareMask) {
        if (b1.cardinality() > b2.cardinality()) {
            // Bl*ak, k<l

            SignedBlade b1Cp = new SignedBlade(bitCount, b1, b1.coefficient);
            boolean add = false;
            for (int i = 0; i < bitCount; ++i) {
                if (b2.get(i)) {
                    SignedBlade maskedBlade = new SignedBlade(bitCount);
                    maskedBlade.set(i);
                    SignedBlade toB1Cp = new SignedBlade(bitCount);
                    if (!calcInnernProduct1(b1Cp, maskedBlade, toB1Cp, bitCount, squareMask)) {
                        return false;
                    }
                    b1Cp = toB1Cp;
                    add = true;
                }

            }

            result.clear();
            result.or(b1Cp);
            result.coefficient *= b1Cp.coefficient*b2.coefficient;

            return add;
        } else {
            // ak*bl, k<l

            SignedBlade b2Cp = new SignedBlade(bitCount, b2, b2.coefficient);
            boolean add = false;

            for (int i = bitCount - 1; i >= 0; --i) {
                if (b1.get(i)) {
                    SignedBlade maskedBlade = new SignedBlade(bitCount);
                    maskedBlade.set(i);
                    SignedBlade toB2Cp = new SignedBlade(bitCount);
                    if (!calcInner1Productn(maskedBlade, b2Cp, toB2Cp, bitCount, squareMask)) {
                        return false;
                    }

                    b2Cp = toB2Cp;
                    add = true;
                }
            }
            result.clear();
            result.or(b2Cp);
            result.coefficient *= b2Cp.coefficient*b1.coefficient;
            return add;
        }
    }

    @Override
    public void calcProduct(SignedBlade b1, SignedBlade b2, SumOfBlades innerProduct, int bitCount, byte[] squareMask) {
        if (!b1.isEmpty() && !b2.isEmpty()) {
            SignedBlade result = new SignedBlade(bitCount);
            if (b1.cardinality() == 1) {
                if (b2.cardinality() == 1) {
                    //length(b1) = 1 && length(b2) = 1
                    if (calcInner1Product1(b1, b2, result, bitCount, squareMask)) 
                        innerProduct.add(result);
                } else {
                    //length(b1) = 1 && length(b2) > 1
                    if (calcInner1Productn(b1, b2, result, bitCount, squareMask)) 
                        innerProduct.add(result);
                }
            } else {
                if (b2.cardinality() == 1) {
                    //length(b1) > 1 && length(b2) = 1
                    if (calcInnernProduct1(b1, b2, result, bitCount, squareMask)) 
                        innerProduct.add(result);
                } else {
                    //length(b1) > 1 && length(b2) > 1
                    if (calcInnernProductn(b1, b2, result, bitCount, squareMask)) 
                        innerProduct.add(result);
                }
            }
        }
    }
}
