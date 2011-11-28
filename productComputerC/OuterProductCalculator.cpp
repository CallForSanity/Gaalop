/*
 * OuterProductCalculator.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "OuterProductCalculator.h"

OuterProductCalculator::OuterProductCalculator() {
}

OuterProductCalculator::~OuterProductCalculator() {
}


void OuterProductCalculator::calculate(Blade& blade1, Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result) {
	intList list;
	Blade resultB(blade1.getPrefactor()*blade2.getPrefactor(), list);

	intList base1 = blade1.getBaseVectors();
	intList base2 = blade2.getBaseVectors();


		if (base1.empty()) {
			if (base2.empty()) {
				result.addBlade(resultB);
				return;
			} else {
				resultB.setBaseVectors(base2);
				result.addBlade(resultB);
				return;
			}
		} else {
			if (base2.empty()) {
				resultB.setBaseVectors(base1);
				result.addBlade(resultB);
				return;
			}
		}

		intList baseConcat(base1);
		for (intList::const_iterator ci=base2.begin();ci != base2.end(); ++ci)
			baseConcat.push_back(*ci);
		resultB.setBaseVectors(baseConcat);

		result.addBlade(resultB);
}
