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
	void calculateProductBlades(const intVector& base1, const intVector& base2, Blade& result);
	void calculateProductBlades(const int base1, const intVector& base2, Blade& result);
	void calculateProductBlades(const intVector& base1, const int base2, Blade& result);
	void calculateProductBlades(const int base1, const int base2, Blade& result);
public:
	InnerProductCalculator();
	virtual ~InnerProductCalculator();

	virtual void calculate(const Blade& blade1, const Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result);
};

#endif /* INNERPRODUCTCALCULATOR_H_ */
