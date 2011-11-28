/*
 * SumOfBlades.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef SUMOFBLADES_H_
#define SUMOFBLADES_H_

#include "stdTypes.h"
#include "Blade.h"
#include "Multivector.h"
#include "ListOfBlades.h"

typedef list<Blade> listBlade;

class SumOfBlades {
private:
	listBlade blades;
public:
	SumOfBlades();
	virtual ~SumOfBlades();

	SumOfBlades(Blade& blade) {
		blades.push_back(blade);
	}

	SumOfBlades(listBlade& blades) {
		this->blades = blades;
	}

	/**
	 * Checks, if some blades are zero and removes these ones
	 */
	void checkIfSomeBladesAreZero();

	/**
	 * Determines, if an array contains an element at least two times
	 * @param arr The array to use
	 * @return Contains the given array an element at least two times?
	 */
	bool containsDoubleIndex(list<int>& arr);

	void normalize() {
		for (listBlade::iterator ci=blades.begin();
				ci != blades.end();++ci)
			ci->normalize();
	}

	void group();

	void toMultivector(ListOfBlades& listOfBlades, Multivector& result);

	inline listBlade getBlades() const
	{
		return blades;
	}

	void print() {
		std::cout << "|";
		for (listBlade::iterator ci=blades.begin(); ci != blades.end();++ci) {
			Blade blade = *ci;
			blade.print();
			std::cout << ",";
		}
		std::cout << "|";
	}

	inline void addBlade(Blade& blade) {
		blades.push_back(blade);
	}

	inline void setBlades(listBlade& blades) {
		this->blades = blades;
	}

	inline void clear() {
		blades.clear();
	}

};

#endif /* SUMOFBLADES_H_ */
