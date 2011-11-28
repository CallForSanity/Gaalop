/*
 * BladeArrayRoutines.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef BLADEARRAYROUTINES_H_
#define BLADEARRAYROUTINES_H_

#include "stdTypes.h"
#include "Blade.h"

class BladeArrayRoutines {
public:
	BladeArrayRoutines();
	virtual ~BladeArrayRoutines();

	void createBlades(intVector& base, vector<Blade>& result);
	void createBladesHelp(intList& arrTrailing, int startPos, int k, intVector& base, vector<Blade>& blades);
};

#endif /* BLADEARRAYROUTINES_H_ */
