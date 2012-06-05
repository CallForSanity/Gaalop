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
	intVector baseVectors;
	Blade();
	Blade(const float prefactor, const int baseVector);
    Blade(const float prefactor, const intVector& baseVectors);
	virtual ~Blade();

	inline float getPrefactor() const
    {
        return prefactor;
    }

	inline void setPrefactor(const float prefactor)
    {
        this->prefactor = prefactor;
    }

	void normalize();

	inline void negate() {
		prefactor *= -1;
	}

	void print() {
		std::cout << prefactor << "*";
		VectorMethods::printIntVector(baseVectors);
	}

};

#endif /* BLADE_H_ */
