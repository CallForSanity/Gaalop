/*
 * Blade.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef BLADE_H_
#define BLADE_H_

#include "stdTypes.h"

#include "VectorMethods.h"

class Blade {
private:
	float prefactor;

public:
	intList baseVectors;
	Blade();
	Blade(float prefactor, int baseVector);
    Blade(float prefactor, intList& baseVectors);
	virtual ~Blade();

	inline intList getBaseVectors() const
    {
        return baseVectors;
    }

	inline float getPrefactor() const
    {
        return prefactor;
    }

	inline void setBaseVectors(intList& baseVectors)
    {
        this->baseVectors = baseVectors;
    }

	inline void setPrefactor(float prefactor)
    {
        this->prefactor = prefactor;
    }

	void normalize();

	inline void negate() {
		prefactor *= -1;
	}

	void print() {
		std::cout << prefactor << "*";
		VectorMethods::printIntList(baseVectors);
	}

};

#endif /* BLADE_H_ */
