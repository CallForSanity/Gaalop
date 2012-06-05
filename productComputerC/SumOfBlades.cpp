/*
 * SumOfBlades.cpp
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#include "SumOfBlades.h"

SumOfBlades::SumOfBlades() {

}

SumOfBlades::~SumOfBlades() {
}

/**
 * Checks, if some blades are zero and removes these ones
 */
void SumOfBlades::checkIfSomeBladesAreZero() {
	listBlade newList;
	const listBlade::const_iterator endItBlades = blades.end();
	for (listBlade::const_iterator ci=blades.begin();
			ci != endItBlades;++ci) {
		const intVector& l = ci->baseVectors;
		if (!containsDoubleIndex(l))
			newList.push_back(*ci);
	}
	blades = newList;
}

	    /**
	     * Determines, if an array contains an element at least two times
	     * @param arr The array to use
	     * @return Contains the given array an element at least two times?
	     */
bool SumOfBlades::containsDoubleIndex(const intVector& arr) {
	unordered_set<int> members;

	const intVector::const_iterator& endItArr = arr.end();
	for (intVector::const_iterator ci=arr.begin();ci != endItArr; ++ci) {
		const int j = *ci;
		if (members.count(j) == 1)
			return true;
		else
			members.insert(j);
	}

	return false;
}

typedef unordered_map<intVector, float, hashIntVector> mapIntvectorFloat;

#include "VectorMethods.h"


void SumOfBlades::group() {
	mapIntvectorFloat groups;

	for (listBlade::iterator ci=blades.begin();
				ci != blades.end();++ci) {
			Blade b = *ci;
			intVector& list = b.baseVectors;
			if (groups.count(list) == 1)
				groups[list] += b.getPrefactor();
			else
				groups[list] = b.getPrefactor();
	}

	blades.clear();

	for (mapIntvectorFloat::iterator ci=groups.begin();
			ci != groups.end();++ci) {
		std::pair<intVector, float> vec = *ci;
		Blade b(vec.second,vec.first);
		blades.push_back(b);
	}
}


#include "BladeRef.h"

#include <iostream>
#include <cmath>

void SumOfBlades::toMultivector(ListOfBlades& listOfBlades, Multivector& result) {

	for (listBlade::const_iterator ci=blades.begin();
					ci != blades.end();++ci) {
		const Blade blade = *ci;
		float prefactor = blade.getPrefactor();
		signed char prefactorB = (signed char) prefactor;

		float diff = prefactor-prefactorB;

		if ((std::abs(diff) <= 10E-7) && (std::abs((float) prefactorB) <= 1)) {
			if (prefactorB != 0) {
				const intVector& vec = blade.baseVectors;
				BladeRef bladeRef(prefactorB,listOfBlades.getIndex(vec));

				result.addBlade(bladeRef);
			}
		}
		else
			std::cout << "Error: Prefactor of multivector is not -1, 0 or 1:" << prefactor << std::endl;

	}

}
