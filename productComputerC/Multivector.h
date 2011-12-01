/*
 * Multivector.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef MULTIVECTOR_H_
#define MULTIVECTOR_H_

#include "stdTypes.h"
#include "BladeRef.h"

class Multivector {
private:

public:
	vector<BladeRef> blades;
	Multivector();
	virtual ~Multivector();

	inline void addBlade(const BladeRef& blade) {
		blades.push_back(blade);
	}

};

#endif /* MULTIVECTOR_H_ */
