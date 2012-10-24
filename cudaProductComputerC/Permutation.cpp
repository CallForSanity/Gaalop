#include "Permutation.h"

inline SignedBlade* at(SumOfBlades& s, int index) {
	SumOfBlades::iterator it = s.begin();

	for (int i=0;i<index;++i)
		it++;

	return &(*it);
}

#include <iostream>

bool Permutation::permutationToBlade(SumOfBlades* lengths, SignedBlade& result, unsigned int i) {
	SumOfBlades& s = lengths[i];
	SignedBlade* b = at(s,permutation[i]);

	result.coefficient *= b->coefficient; 
	for (unsigned int j=0;j<MAXBITCOUNT;++j) {
		bool v = b->isBit(j);
		if (v && result.isBit(j)) return false; // = 0 , because of double indices in a wedge operation 
		if (v) {
			result.setBit(j);
			for (int k=j+1;k<MAXBITCOUNT;++k)
				if (result.isBit(k))
					result.coefficient *= -1;
		}
	}
	return true;
}

bool Permutation::initialize(SumOfBlades* lengths, SignedBlade& result) {
	result.bits = 0;
	curNo = 0;
	count = 1;
	for (unsigned int i=0;i<MAXBITCOUNT;++i) {
		if (lengths[i].size() > 0) {
			count *= lengths[i].size();
			permutation[i] = 0;
		} else 
			permutation[i] = -1;
	}

	for (unsigned int i=0;i<MAXBITCOUNT;++i) {

		if (lengths[i].size() > 0) 
			if (!permutationToBlade(lengths, result,i)) return false;
	}
	

	return true;
}



bool Permutation::getNextPermutation(SumOfBlades* lengths, SignedBlade& result) {
	curNo++;
	if (!(curNo<count)) return false;
	


	int pos = 0;
	bool ueberlauf = true;
	while (ueberlauf) {
		if (permutation[pos] != -1) {
			ueberlauf = (permutation[pos]+1 == lengths[pos].size());
			if (ueberlauf)
				permutation[pos] = 0;
			else
				permutation[pos]++;
		}
		pos++;
	}

	result.bits = 0;
	for (unsigned int i=0;i<MAXBITCOUNT;++i) 
		if (lengths[i].size() > 0) 
			if (!permutationToBlade(lengths,result,i)) return false;

	return true;
}