/*
 * Permutable.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef PERMUTABLE_H_
#define PERMUTABLE_H_

#include "stdTypes.h"

class Permutable {
public:
	Permutable();
	virtual ~Permutable();

    virtual void initialize(intVector& lengths, intVector& permutation) = 0;
    virtual void getNextPermutation(intVector& permutation) = 0;
    virtual bool hasNextPermutation() = 0;
};

#endif /* PERMUTABLE_H_ */
