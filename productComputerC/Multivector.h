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
	vector<BladeRef> blades;
public:
	Multivector();
	virtual ~Multivector();

	inline void addBlade(BladeRef& blade) {
		blades.push_back(blade);
	}

	inline vector<BladeRef> getBlades() const {
		return blades;
	}
};

#endif /* MULTIVECTOR_H_ */
