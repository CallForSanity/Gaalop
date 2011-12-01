/*
 * CalcThread.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef CALCTHREAD_H_
#define CALCTHREAD_H_

#include "Algebra.h"
#include "ProductComputer.h"
#include "InnerProductCalculator.h"
#include "OuterProductCalculator.h"
#include "GeoProductCalculator.h"

class CalcThread {
private:
	ProductComputer productComputer;
    InnerProductCalculator inner;
    OuterProductCalculator outer;
    GeoProductCalculator geo;
    int from;
    int to;
    int bladeCount;
public:
	CalcThread(const int from, const  int to,const  int bladeCount,const Algebra& algebra) {
		this->from = from;
		this->to = to;
		this->bladeCount = bladeCount;
		productComputer.initialize(algebra);
	}

	virtual ~CalcThread();

	void run();
};

#endif /* CALCTHREAD_H_ */
