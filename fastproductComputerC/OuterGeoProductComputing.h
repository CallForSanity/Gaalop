#pragma once

#include "SignedBlade.h"

void computeOuterGeoOfTwoSignedBlades(SignedBlade& b1, SignedBlade& b2, SumOfBlades& outerProduct, SumOfBlades& geoProduct) {
	if (!b1.anyDoubleTrueBit(b2)) {
		//outer product is not null and geometric product is equal to outer product
		SignedBlade resultOuter(b1.bits ^ b2.bits); // also "or"-operation is possible
		resultOuter.coefficient = b1.coefficient*b2.coefficient*canonicalReorderingSign(b1,b2);
		outerProduct.push_back(resultOuter);
		geoProduct.push_back(resultOuter);
	} else {
		//outer product is null
		//calculate geo product
		SignedBlade resultOuter;
		resultOuter.bits = b1.bits ^ b2.bits; // also "or"-operation is possible
		resultOuter.coefficient = b1.coefficient*b2.coefficient*canonicalReorderingSign(b1,b2);

		Blade aAndBMasked10 = squareMask.bits & (b1.bits & b2.bits);
		if (aAndBMasked10.any() && ((aAndBMasked10.count() % 2) == 1)) 
			resultOuter.coefficient *= -1;

		geoProduct.push_back(resultOuter);
	}
				
}