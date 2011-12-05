#define TIMING
//============================================================================
// Name        : ProductComputerC.cpp
// Author      : Christian Steinmetz
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================
#ifdef TIMING
#include "Timing.h"
Timing Timing::timing;
#endif

#include "CorrectnessTester.h"
#include "PerformanceTester.h"

int main(int argc, char** argv) {
	if (argc != 3) return -1;
	time_t start;
	time(&start);
	CorrectnessTester c;
	c.correctness5d();

#ifdef TIMING
	Timing::timing.clear();
#endif
	PerformanceTester p;
	//p.performanceOn9d(500,505);
	p.performance("/home/christian/algebra/5d/");
#ifdef TIMING
	Timing::timing.printAllTimes();
#endif
    //performanceOn9d(args);

	time_t ende;
	time(&ende);
	double dt = difftime(ende, start);
	std::cout << "Run time: " << dt << "s" << std::endl;
	return 0;
}
