/*
 * Blade.cpp
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#include "Blade.h"

Blade::Blade() {

}

Blade::~Blade() {
}

Blade::Blade(float prefactor, int baseVector) {
	this->prefactor = prefactor;
	this->baseVectors.push_back(baseVector);
}

Blade::Blade(float prefactor, intList& baseVectors) {
	this->prefactor = prefactor;
	this->baseVectors = baseVectors;
}

#include "VectorMethods.h"
#include "BubbleSort.h"

void Blade::normalize() {

	intVector arr;
	VectorMethods::intListToVector(baseVectors,arr);

    if ((BubbleSort::doBubbleSort(arr) % 2) == 1)
        negate();

    baseVectors.clear();
    VectorMethods::intVectorToList(arr, baseVectors);
}
