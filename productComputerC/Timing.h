/*
 * Timing.h
 *
 *  Created on: 26.11.2011
 *      Author: christian
 */

#ifndef TIMING_H_
#define TIMING_H_

#include <iostream>
#include "stdTypes.h"


class Timing {
private:
	unordered_map<string, double> mapSumSections;
	unordered_map<string, time_t> mapStartCurSection;
public:
	Timing();
	virtual ~Timing();

	void startTime(const string& clock);

	void stopTime(const string& clock);

	void printTime(const string& clock);

	void clear() {
		mapStartCurSection.clear();
		mapSumSections.clear();
	}

	static Timing timing;

	void printAllTimes();
};



#endif /* TIMING_H_ */
