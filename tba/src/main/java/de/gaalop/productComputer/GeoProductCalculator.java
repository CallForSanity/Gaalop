package de.gaalop.productComputer;


/**
 * Calculates the geometric product of two blades
 * @author Christian Steinmetz
 */
public class GeoProductCalculator implements ProductCalculator {

    @Override
    public void calcProduct(SignedBlade b1, SignedBlade b2, SumOfBlades result, int bitCount, byte[] squareMask) {
        SignedBlade resultOuter = new SignedBlade(bitCount, b1, b1.coefficient*b2.coefficient*GAMethods.canonicalReorderingSign(b1,b2, bitCount));
        resultOuter.xor(b2);

        if (!b1.intersects(b2)) {
            //outer product is not null and geometric product is equal to outer product
            result.add(resultOuter);
	} else {
            //outer product is null
            //calculate geo product
            Blade aAndBMasked = new Blade(bitCount, b1);
            aAndBMasked.and(b2);
            for (int index = 0;index < bitCount; index++) 
                if (aAndBMasked.get(index)) 
                    resultOuter.coefficient *= squareMask[index];

            result.add(resultOuter);
	}
    }

}
