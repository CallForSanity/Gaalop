/*
 * BaseVectors.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef BASEVECTORS_H_
#define BASEVECTORS_H_

#include "stdTypes.h"
#include "DoubledHashMap.h"

class BaseVectors {
private:
	int cur;
	DoubledHashMap<string, int> bases;
public:
	BaseVectors();
	virtual ~BaseVectors();

	int addBase(string& base) {
        bases.setAssociation(base, cur);
        return cur++;
    }

	inline string getBaseString(int& index) {
	   return bases.getKey(index);
	}

	inline int getIndex(string& base) {
	   return bases.getValue(base);
	}

	inline bool containsBase(string& base) {
	   return bases.containsKey(base);
	}

	inline bool containsIndex(int& index) {
	   return bases.containsValue(index);
	}


};

#endif /* BASEVECTORS_H_ */
