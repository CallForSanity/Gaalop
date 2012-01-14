/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.productComputer2;


/**
 *
 * @author christian
 */
public class InnerProductCalculator implements ProductCalculator2 {

    public boolean calcInner1Product1(SignedBlade b1, SignedBlade b2, SignedBlade resultInner1, int bitCount, BitSet squareMask) {
        resultInner1.clear();
        resultInner1.coefficient = b1.coefficient * b2.coefficient;
        Blade aAndB = new Blade(bitCount, b1);
        aAndB.and(b2);
        if (aAndB.isEmpty()) {
            return false;
        }
        Blade aAndBMasked1 = new Blade(bitCount, aAndB);
        aAndBMasked1.and(squareMask);
        if (!aAndBMasked1.isEmpty()) // if ((aAndBMasked1.count() % 2) == 1)  always true!
        {
            resultInner1.coefficient *= -1;
        }
        return true;
    }

    public boolean calcInner1Productn(SignedBlade b1, SignedBlade b2, SignedBlade resultInner2, int bitCount, BitSet squareMask) {
        Blade aAndBMasked2 = new Blade(bitCount, b1);
        aAndBMasked2.and(b2);
        if (!aAndBMasked2.isEmpty()) {
            resultInner2.coefficient = b1.coefficient * b2.coefficient;
            resultInner2.clear();
            resultInner2.or(b1);
            resultInner2.xor(b2);

            boolean negate = false;
            int i = 0;
            while (!b1.get(i)) {
                if (b2.get(i)) {
                    negate = !negate;
                }
                i++;
            }

            Blade temp = new Blade(bitCount, aAndBMasked2);
            temp.and(squareMask);

            if (!temp.isEmpty()) {
                negate = !negate;
            }

            if (negate) {
                resultInner2.coefficient *= -1;
            }
            return true;
        }
        return false;
    }

    public boolean calcInnernProduct1(SignedBlade b1, SignedBlade b2, SignedBlade resultInner3, int bitCount, BitSet squareMask) {
        Blade aAndBMasked3 = new Blade(bitCount, b1);
        aAndBMasked3.and(b2);
        if (!aAndBMasked3.isEmpty()) {
            resultInner3.coefficient = b1.coefficient * b2.coefficient;
            resultInner3.clear();
            resultInner3.or(b1);
            resultInner3.xor(b2);

            boolean negate = false;
            int i = bitCount - 1;
            while (!b2.get(i)) {
                if (b1.get(i)) {
                    negate = !negate;
                }
                i--;
            }

            Blade temp = new Blade(bitCount, aAndBMasked3);
            temp.and(squareMask);
            if (!temp.isEmpty()) {
                negate = !negate;
            }

            if (negate) {
                resultInner3.coefficient *= -1;
            }
            return true;
        }
        return false;
    }

    public boolean calcInnernProductn(SignedBlade b1, SignedBlade b2, SignedBlade resultInner4, int bitCount, BitSet squareMask) {
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

            resultInner4.clear();
            resultInner4.or(b1Cp);
            resultInner4.coefficient *= b1Cp.coefficient*b2.coefficient;

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
            resultInner4.clear();
            resultInner4.or(b2Cp);
            resultInner4.coefficient *= b2Cp.coefficient*b1.coefficient;
            return add;
        }
    }

    @Override
    public void calcProduct(SignedBlade b1, SignedBlade b2, SumOfBlades innerProduct, int bitCount, BitSet squareMask) {
        if (!b1.isEmpty() && !b2.isEmpty()) {
            SignedBlade result = new SignedBlade(bitCount);
            if (b1.cardinality() == 1) {
                if (b2.cardinality() == 1) {
                    //length(b1) = 1 && length(b2) = 1
                    if (calcInner1Product1(b1, b2, result, bitCount, squareMask)) {
                        innerProduct.add(result);
                    }
                } else {
                    //length(b1) = 1 && length(b2) > 1
                    if (calcInner1Productn(b1, b2, result, bitCount, squareMask)) {
                        innerProduct.add(result);
                    }
                }
            } else {
                if (b2.cardinality() == 1) {
                    //length(b1) > 1 && length(b2) = 1
                    if (calcInnernProduct1(b1, b2, result, bitCount, squareMask)) {
                        innerProduct.add(result);
                    }
                } else {
                    //length(b1) > 1 && length(b2) > 1
                    if (calcInnernProductn(b1, b2, result, bitCount, squareMask)) {
                        innerProduct.add(result);
                    }
                }
            }
        }
    }
}
