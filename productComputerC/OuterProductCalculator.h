/*
 * OuterProductCalculator.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef OUTERPRODUCTCALCULATOR_H_
#define OUTERPRODUCTCALCULATOR_H_

#include "ProductCalculator.h"
#include "stdTypes.h"

class OuterProductCalculator: public ProductCalculator {
public:
	OuterProductCalculator();
	virtual ~OuterProductCalculator();

	virtual void calculate(const Blade& blade1, const Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result);
};

#endif /* OUTERPRODUCTCALCULATOR_H_ */
