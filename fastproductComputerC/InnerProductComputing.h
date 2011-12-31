#pragma once

#include "SignedBlade.h"

inline bool calcInner1Product1(SignedBlade& b1, SignedBlade& b2, SignedBlade& resultInner1) {
	resultInner1.bits = 0;
	resultInner1.coefficient = b1.coefficient*b2.coefficient;
	Blade aAndB(b1.bits & b2.bits);
	if (!aAndB.any()) return false;
	Blade aAndBMasked1 = squareMask.bits & aAndB.bits;
	if (aAndBMasked1.any()) 
		// if ((aAndBMasked1.count() % 2) == 1)  always true!
			resultInner1.coefficient *= -1;
	return true;
}

inline bool calcInner1Productn(SignedBlade& b1, SignedBlade& b2, SignedBlade& resultInner2) {
	Blade aAndBMasked2 = (b1.bits & b2.bits);
	if (aAndBMasked2.any()) {
		resultInner2.coefficient = b1.coefficient*b2.coefficient;
		resultInner2.bits = b1.bits ^ b2.bits;

		bool negate = false;
		int i=0;
		while (!b1.isBit(i)) {
			if (b2.isBit(i))
				negate = !negate;
			i++;
		}

		if (Blade(aAndBMasked2.bits & squareMask.bits).any())
		negate = !negate;

		if (negate)
			resultInner2.coefficient *= -1;
		return true;
	}
	return false;
}

inline bool calcInnernProduct1(SignedBlade& b1, SignedBlade& b2, SignedBlade& resultInner3) {
	Blade aAndBMasked3 = (b1.bits & b2.bits);
	if (aAndBMasked3.any()) {
		resultInner3.coefficient = b1.coefficient*b2.coefficient;
		resultInner3.bits = b1.bits ^ b2.bits;

		bool negate = false;
		int i=MAXBITCOUNT-1;
		while (!b2.isBit(i)) {
			if (b1.isBit(i))
				negate = !negate;
			i--;
		}

		if (Blade(aAndBMasked3.bits & squareMask.bits).any())
			negate = !negate;

		if (negate)
			resultInner3.coefficient *= -1;
		return true;
	}
	return false;
}

inline bool calcInnernProductn(SignedBlade& b1, SignedBlade& b2, SignedBlade& resultInner4) {
	if (b1.count() > b2.count()) {
            // Bl*ak, k<l

		Blade mask = 1;
		SignedBlade b1Cp(b1);
		bool add = false;
		for (int i=0;i<MAXBITCOUNT;++i) {
			Blade maskAndB2 = mask.bits & b2.bits;
			if (maskAndB2.any()) {
				SignedBlade maskedBlade(maskAndB2.bits);
				SignedBlade toB1Cp;
				if (!calcInnernProduct1(b1Cp,maskedBlade,toB1Cp)) return false;
				b1Cp = toB1Cp;
				add = true;
			} 

			mask.bits <<= 1;
		}

		resultInner4 = b1Cp;
		resultInner4.coefficient *= b2.coefficient;

		return add;
	} else {
            // ak*bl, k<l
        	Blade mask = 1;
			SignedBlade b2Cp(b2);
			bool add = false;
			mask.bits <<= MAXBITCOUNT-1;

			for (int i=MAXBITCOUNT-1;i>=0;--i) {
				Blade maskAndB1 = mask.bits & b1.bits;
				if (maskAndB1.any()) {
					SignedBlade maskedBlade(maskAndB1.bits);
					SignedBlade toB2Cp;
					if (!calcInner1Productn(maskedBlade,b2Cp,toB2Cp)) return false;

					b2Cp = toB2Cp;
					add = true;
				}

				mask.bits >>= 1;
			}
		resultInner4 = b2Cp;
		resultInner4.coefficient *= b1.coefficient;
		return add;
    }
}

void computeInnerOfTwoSignedBlades(SignedBlade& b1, SignedBlade& b2, SumOfBlades& innerProduct) {
	if (b1.any() && b2.any()) {
		SignedBlade result;
		if (b1.count() == 1) {
			if (b2.count() == 1) {
				//length(b1) = 1 && length(b2) = 1
				if (calcInner1Product1(b1, b2, result))
					innerProduct.push_back(result);
			} else {
				//length(b1) = 1 && length(b2) > 1
				if (calcInner1Productn(b1, b2, result)) 
					innerProduct.push_back(result);
			}
		} else {
			if (b2.count() == 1) {
				//length(b1) > 1 && length(b2) = 1
				if (calcInnernProduct1(b1, b2, result))
				innerProduct.push_back(result);
			}
			else {
				//length(b1) > 1 && length(b2) > 1
				if (calcInnernProductn(b1, b2, result))
				innerProduct.push_back(result);
			}
		}
	}
}