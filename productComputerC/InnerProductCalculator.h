/*
 * InnerProductCalculator.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef INNERPRODUCTCALCULATOR_H_
#define INNERPRODUCTCALCULATOR_H_

#include "ProductCalculator.h"
#include "stdTypes.h"

class InnerProductCalculator: public ProductCalculator {
private:
	unordered_map<int,int> baseSquares;
	void calculateProductBlades(intList& base1, intList& base2, Blade& result);
	void calculateProductBlades(int base1, intList& base2, Blade& result);
	void calculateProductBlades(intList& base1, int base2, Blade& result);
	void calculateProductBlades(int base1, int base2, Blade& result);
public:
	InnerProductCalculator();
	virtual ~InnerProductCalculator();

	virtual void calculate(Blade& blade1, Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result);
};

#endif /* INNERPRODUCTCALCULATOR_H_ */
