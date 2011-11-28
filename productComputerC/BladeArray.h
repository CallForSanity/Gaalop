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
	vector<Blade> blades;
public:
	BladeArray();
	virtual ~BladeArray();

	inline vector<Blade> getBlades() const
	{
		return blades;
	}

	inline void setBlades(vector<Blade>& blades)
	{
		this->blades = blades;
	}

	inline void getBlade(int index, Blade& blade) {
		blade = blades[index];
	}
};

#endif /* BLADEARRAY_H_ */
