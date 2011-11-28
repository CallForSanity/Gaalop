/*
 * GeoProductCaluclator.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "GeoProductCalculator.h"

GeoProductCalculator::GeoProductCalculator() {
}

GeoProductCalculator::~GeoProductCalculator() {
}

void GeoProductCalculator::calculate(Blade& blade1, Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result) {
	//approach method from the dissertation: Daniel Fontijne, "Efficient Implementation of Geometric Algebra" (2007)

	intList list1 = blade1.getBaseVectors();
	intList list2 = blade2.getBaseVectors();


	bool negate = false;

	list1.remove(0);
	list2.remove(0);


	int element;
	int i1;
	int i2;
	while ((element = commonElement(list1, list2, i1, i2)) != -1) {
		int disp1 = list1.size()-i1-1;
		int disp2 = i2;
		if (((disp1+disp2) % 2) == 1)
			negate = !negate;
		list1.remove(element);
		list2.remove(element);
		if (baseSquares[element] == -1) negate = !negate;
	}

	intList merged(list1);
	VectorMethods::mergeLists(merged,list2);

	float prefactor = blade1.getPrefactor()*blade2.getPrefactor();
	if (negate) prefactor *= -1;

	Blade b(prefactor, merged);
	result.addBlade(b);
}


/**
 * Returns a common element of two lists
 * @param list1 The first list
 * @param list2 The second list
 * @return A common element
 */
int GeoProductCalculator::commonElement(intList& list1, intList& list2, int& i1, int& i2) {
	i1 = 0;
	i2 = 0;
	for (intList::const_iterator ci1 = list1.begin(); ci1 != list1.end(); ++ci1) {
		i2=0;
		for (intList::const_iterator ci2 = list2.begin(); ci2 != list2.end(); ++ci2) {
			if (*ci1 == *ci2)
				return *ci1;
			i2++;
		}
		i1++;
	}
	return -1;
}
