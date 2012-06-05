/*
 * Functions.h
 *
 *  Created on: 01.12.2011
 *      Author: christian
 */

#ifndef FUNCTIONS_H_
#define FUNCTIONS_H_

#include "Multivector.h"
#include <iostream>
#include <math.h>
#include "ProductComputer.h"
#include "Algebra5d.h"
#include "InnerProductCalculator.h"
#include "OuterProductCalculator.h"
#include "GeoProductCalculator.h"
#include <sstream>
#include <fstream>

class Functions {
public:
	Functions();
	virtual ~Functions();

	void outputMultivector(const Multivector& m, std::ostream& out);
};

#endif /* FUNCTIONS_H_ */
