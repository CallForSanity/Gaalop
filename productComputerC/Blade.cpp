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

Blade::Blade(const float prefactor, const int baseVector) {
	this->prefactor = prefactor;
	this->baseVectors.push_back(baseVector);
}

Blade::Blade(const float prefactor, const intVector& baseVectors) {
	this->prefactor = prefactor;
	this->baseVectors = baseVectors;
}

#include "VectorMethods.h"
#include "BubbleSort.h"

void Blade::normalize() {
    if ((BubbleSort::doBubbleSort(baseVectors) % 2) == 1)
        negate();
}
