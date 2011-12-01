/*
 * Timing.cpp
 *
 *  Created on: 26.11.2011
 *      Author: christian
 */

#include "Timing.h"

Timing::Timing() {
}

Timing::~Timing() {
}

void Timing::startTime(const string& clock) {
	time(&mapStartCurSection[clock]);
}

void Timing::stopTime(const string& clock) {
	time_t endCurSection;
	time(&endCurSection);
	if (mapSumSections.count(clock) == 0)
		mapSumSections[clock] = 0;
	mapSumSections[clock] += difftime(endCurSection, mapStartCurSection[clock]);
}

void Timing::printAllTimes() {
	for (unordered_map<string, double>::const_iterator it=mapSumSections.begin();it != mapSumSections.end(); ++it) {
		std::pair<string, double> pair = *it;
		printTime(pair.first);
	}
}

void Timing::printTime(const string& clock) {
	std::cout << "Accumulated time(" << clock << "): " << mapSumSections[clock] << "s" << std::endl;
}
