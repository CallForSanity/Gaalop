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
	for (listBlade::iterator ci=blades.begin();
			ci != blades.end();++ci) {
		Blade b = *ci;
		intList l = b.getBaseVectors();
		if (!containsDoubleIndex(l))
			newList.push_back(b);
	}
	blades = newList;
}

	    /**
	     * Determines, if an array contains an element at least two times
	     * @param arr The array to use
	     * @return Contains the given array an element at least two times?
	     */
bool SumOfBlades::containsDoubleIndex(intList& arr) {
	unordered_set<int> members;


	for (intList::iterator ci=arr.begin();ci != arr.end(); ++ci) {
		int j = *ci;
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
			intVector vec;
			intList list = b.getBaseVectors();
			VectorMethods::intListToVector(list,vec);
			if (groups.count(vec) == 1)
				groups[vec] += b.getPrefactor();
			else
				groups[vec] = b.getPrefactor();
	}

	blades.clear();

	for (mapIntvectorFloat::iterator ci=groups.begin();
			ci != groups.end();++ci) {
		std::pair<intVector, float> vec = *ci;
		intList list;
		VectorMethods::intVectorToList(vec.first,list);
		Blade b(vec.second,list);
		blades.push_back(b);
	}
}


#include "BladeRef.h"

#include <iostream>
#include <cmath>

void SumOfBlades::toMultivector(ListOfBlades& listOfBlades, Multivector& result) {

	for (listBlade::iterator ci=blades.begin();
					ci != blades.end();++ci) {
		Blade blade = *ci;
		float prefactor = blade.getPrefactor();
		signed char prefactorB = (signed char) prefactor;

		float diff = prefactor-prefactorB;

		if ((std::abs(diff) <= 10E-7) && (std::abs((float) prefactorB) <= 1)) {
			if (prefactorB != 0) {
				intVector vec;
				intList list = blade.getBaseVectors();
				VectorMethods::intListToVector(list,vec);
				BladeRef bladeRef(prefactorB,listOfBlades.getIndex(vec));

				result.addBlade(bladeRef);
			}
		}
		else
			std::cout << "Error: Prefactor of multivector is not -1, 0 or 1:" << prefactor << std::endl;

	}

}
