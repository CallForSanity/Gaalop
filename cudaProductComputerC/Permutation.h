#pragma once

#include "SignedBlade.h"
#include "Definitions.h"

class Permutation {
private:
	bool permutationToBlade(SumOfBlades* lengths, SignedBlade& result, unsigned int i);
public:
	int curNo;
	int count;
	int permutation[MAXBITCOUNT];

	Permutation() {
	}

	virtual ~Permutation() {
	}

	inline bool hasNextPermutation() {
		return curNo+1<count;
	}

	
	bool initialize(SumOfBlades* lengths, SignedBlade& result);
	bool getNextPermutation(SumOfBlades* lengths, SignedBlade& result);
};