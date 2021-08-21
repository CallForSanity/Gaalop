package de.gaalop.productComputer;


/**
 * Calculates the outer product of two blades
 * @author Christian Steinmetz
 */
public class OuterProductCalculator implements ProductCalculator {

    @Override
    public void calcProduct(SignedBlade b1, SignedBlade b2, SumOfBlades result, int bitCount, byte[] squareMask) {
        if (!b1.intersects(b2)) {
            //outer product is not null and geometric product is equal to outer product
            SignedBlade resultOuter = new SignedBlade(bitCount, b1, b1.coefficient*b2.coefficient*GAMethods.canonicalReorderingSign(b1,b2, bitCount));
            resultOuter.xor(b2);
            result.add(resultOuter);
	}
    }

}
