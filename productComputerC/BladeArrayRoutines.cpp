/*
 * BladeArrayRoutines.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "BladeArrayRoutines.h"

BladeArrayRoutines::BladeArrayRoutines() {
}

BladeArrayRoutines::~BladeArrayRoutines() {
}

    /**
     * Creates an array of blades from a base
     * @param base The base
     * @return The array of blades
     */
    void BladeArrayRoutines::createBlades(intVector& base, vector<Blade>& result) {
    	intList list;
    	result.push_back(Blade(1,list));
    	for (unsigned int k=1;k<=base.size();++k) {
    		intList list;
    		createBladesHelp(list,0,k, base, result);
    	}
    }

    /**
     * Helper function for createBlades.
     * @param arrTrailing The trailing array to be inserted before each combination
     * @param startPos The start position in the base array
     * @param k The number of base elements to be inserted
     */
    void BladeArrayRoutines::createBladesHelp(intList& arrTrailing, int startPos, int k, intVector& base, vector<Blade>& blades) {
    	if (k == 1) {
			for (unsigned int s=startPos;s<base.size();++s) {
				intList nbase(arrTrailing);
				nbase.push_back(base[s]);
				blades.push_back(Blade(1,nbase));
			}
		} else {
			for (unsigned int s=startPos;s<base.size()-1;++s) {
				intList nbase(arrTrailing);
				nbase.push_back(base[s]);
				createBladesHelp(nbase, s+1, k-1, base, blades);
			}
		}
    }
