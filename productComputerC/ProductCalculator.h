/*
 * ProductCalculator.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef PRODUCTCALCULATOR_H_
#define PRODUCTCALCULATOR_H_

#include "stdTypes.h"
#include "Blade.h"
#include "SumOfBlades.h"

class ProductCalculator {
public:
	ProductCalculator();
	virtual ~ProductCalculator();

    /**
     * Calculates the product of two blades in PlusMinus base
     * @param blade1 The first blade
     * @param blade2 The second blade
     * @return The product as sum of blades
     */
	virtual void calculate(const Blade& blade1, const Blade& blade2, unordered_map<int,int>& baseSquares, SumOfBlades& result) = 0;

};

#endif /* PRODUCTCALCULATOR_H_ */
