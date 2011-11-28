/*
 * GeoProductCaluclator.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef GEOPRODUCTCALCULATOR_H_
#define GEOPRODUCTCALCULATOR_H_

#include "ProductCalculator.h"
#include "stdTypes.h"


class GeoProductCalculator: public ProductCalculator {
private:
	int commonElement(intList& list1, intList& list2, int& i1, int& i2);
public:
	GeoProductCalculator();
	virtual ~GeoProductCalculator();

	virtual void calculate(Blade& blade1, Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result);
};

#endif /* GEOPRODUCTCALCULATOR_H_ */
