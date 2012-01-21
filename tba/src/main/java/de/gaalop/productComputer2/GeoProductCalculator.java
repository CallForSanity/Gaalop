package de.gaalop.productComputer2;


/**
 * Calculates the geometric product of two blades
 * @author christian
 */
public class GeoProductCalculator implements ProductCalculator2 {

    @Override
    public void calcProduct(SignedBlade b1, SignedBlade b2, SumOfBlades result, int bitCount, BitSet squareMask) {
        SignedBlade resultOuter = new SignedBlade(bitCount, b1, b1.coefficient*b2.coefficient*GAMethods.canonicalReorderingSign(b1,b2, bitCount));
        resultOuter.xor(b2);

        if (!b1.intersects(b2)) {
            //outer product is not null and geometric product is equal to outer product
            result.add(resultOuter);
	} else {
            //outer product is null
            //calculate geo product
            Blade aAndBMasked10 = new Blade(bitCount, squareMask);
            aAndBMasked10.and(b1);
            aAndBMasked10.and(b2);
            if (!aAndBMasked10.isEmpty() && ((aAndBMasked10.cardinality() % 2) == 1))
                resultOuter.coefficient *= -1;

            result.add(resultOuter);
	}
    }

}
