/*
 * BaseVectors.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef BASEVECTORS_H_
#define BASEVECTORS_H_

#include "stdTypes.h"

class BaseVectors {
private:
	int cur;
	unordered_map<string, int> bases;
public:
	BaseVectors();
	virtual ~BaseVectors();

	inline int addBase(const string& base) {
        bases[base] = cur;
        return cur++;
    }

	inline int getIndex(const string& base) {
	   return bases[base];
	}

	inline bool containsBase(const string& base) {
		return bases.count(base) != 0;
	}

};

#endif /* BASEVECTORS_H_ */
