#pragma once

#include "Definitions.h"

/**
    * Helper function for createBlades.
    * @param arrTrailing The trailing array to be inserted before each combination
    * @param startPos The start position in the base array
    * @param k The number of base elements to be inserted
    */
void createBladesHelp(const Blade& arrTrailing, const int startPos, const  int k, Bladelist& bladelist) {
    if (k == 1) {
		for (unsigned int s=startPos;s<MAXBITCOUNT;++s) {
			Blade nbase(arrTrailing);
			nbase.setBit(s);
			SumOfBlades su;
			su.push_back(SignedBlade(nbase));
			bladelist.push_back(su);
		}
	} else {
		for (unsigned int s=startPos;s<MAXBITCOUNT-1;++s) {
			Blade nbase(arrTrailing);
			nbase.setBit(s);
			createBladesHelp(nbase, s+1, k-1, bladelist);
		}
	}
}

void initializeBladelist(Bladelist& bladelist) {
	SumOfBlades s;
	s.push_back(SignedBlade());
	bladelist.push_back(s);
	for (unsigned int k=1;k<=MAXBITCOUNT;++k) {
    	Blade list1;
		createBladesHelp(list1,0,k, bladelist);
    }
}


