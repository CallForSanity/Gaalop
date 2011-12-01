/*
 * PermutableIterator.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef PERMUTABLEITERATOR_H_
#define PERMUTABLEITERATOR_H_

#include "Permutable.h"

class PermutableIterator {

private:


    intVector lengths;
public:
	int curNo;
	int count;

	PermutableIterator();
	virtual ~PermutableIterator();

	void initialize(const intVector& lengths, intVector& permutation);
	void getNextPermutation(intVector& permutation);
};

#endif /* PERMUTABLEITERATOR_H_ */
