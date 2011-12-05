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
private:
	bool onlyInInterval;
	int from;
	int to;

public:
	PerformanceTester();
	virtual ~PerformanceTester();

	void performance(const string& outDir);

	void setInterval(int from, int to) {
		onlyInInterval = true;
		this->from = from;
		this->to = to;
	}
};

#endif /* PERFORMANCETESTER_H_ */
