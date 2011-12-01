/*
 * PerformanceTester.h
 *
 *  Created on: 01.12.2011
 *      Author: christian
 */

#ifndef PERFORMANCETESTER_H_
#define PERFORMANCETESTER_H_

#include "Functions.h"

class PerformanceTester: public Functions {
public:
	PerformanceTester();
	virtual ~PerformanceTester();

	void performanceOn9d(const int from, const int to);
};

#endif /* PERFORMANCETESTER_H_ */
