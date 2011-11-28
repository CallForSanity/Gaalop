/*
 * PermutableIterator.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef PERMUTABLEITERATOR_H_
#define PERMUTABLEITERATOR_H_

#include "Permutable.h"

class PermutableIterator: public Permutable {

private:
    int curNo;
    int count;

    intVector lengths;
public:
	PermutableIterator();
	virtual ~PermutableIterator();

	virtual void initialize(intVector& lengths, intVector& permutation);
	virtual void getNextPermutation(intVector& permutation);
	virtual bool hasNextPermutation();
};

#endif /* PERMUTABLEITERATOR_H_ */
