/*
 * BladeArray.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef BLADEARRAY_H_
#define BLADEARRAY_H_

#include "stdTypes.h"
#include "Blade.h"

class BladeArray {
private:

public:
	vector<Blade> blades;
	BladeArray();
	virtual ~BladeArray();

	inline void getBlade(const int index, Blade& blade) {
		blade = blades[index];
	}
};

#endif /* BLADEARRAY_H_ */
