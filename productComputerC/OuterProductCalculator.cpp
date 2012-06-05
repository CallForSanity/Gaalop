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


void OuterProductCalculator::calculate(const Blade& blade1, const Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result) {
	intVector list;
	Blade resultB(blade1.getPrefactor()*blade2.getPrefactor(), list);

	const intVector& base1 = blade1.baseVectors;
	const intVector& base2 = blade2.baseVectors;


		if (base1.empty()) {
			if (base2.empty()) {
				result.addBlade(resultB);
				return;
			} else {
				Blade resultF(blade1.getPrefactor()*blade2.getPrefactor(), base2);
				result.addBlade(resultF);
				return;
			}
		} else {
			if (base2.empty()) {
				Blade resultF(blade1.getPrefactor()*blade2.getPrefactor(), base1);
				result.addBlade(resultF);
				return;
			}
		}



		intVector& baseConcat = resultB.baseVectors;
		baseConcat.clear();
		baseConcat.reserve(base1.size()+base2.size());
		for (intVector::const_iterator ci=base1.begin();ci != base1.end(); ++ci)
			baseConcat.push_back(*ci);
		for (intVector::const_iterator ci=base2.begin();ci != base2.end(); ++ci)
			baseConcat.push_back(*ci);

		result.addBlade(resultB);
}
