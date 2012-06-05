/*
 * PermutableIterator.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "PermutableIterator.h"

PermutableIterator::PermutableIterator() {
}

PermutableIterator::~PermutableIterator() {
}

void PermutableIterator::initialize(const intVector& lengths, intVector& permutation) {
	this->lengths = lengths;
	permutation.reserve(lengths.size());
	curNo = 0;
	count = 1;
	for (unsigned int i=0;i<lengths.size();++i) {
		count *= lengths[i];
		permutation.push_back(0);
	}
}

void PermutableIterator::getNextPermutation(intVector& permutation) {
	curNo++;
	if (!(curNo<count)) return;

	int pos = permutation.size();
	bool ueberlauf = true;
	while (ueberlauf) {
		pos--;
		ueberlauf = (permutation[pos]+1 == lengths[pos]);
		if (ueberlauf)
			permutation[pos] = 0;
		else
			permutation[pos]++;
	}
}
