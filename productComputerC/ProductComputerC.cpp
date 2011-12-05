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
	if (argc != 2) {
            std::cout << "You have to specify an argument: algebraDirPath" << std::endl;
            return -1;
        }
	time_t start;
	time(&start);
	CorrectnessTester c;
	c.correctness5d();

#ifdef TIMING
	Timing::timing.clear();
#endif
	PerformanceTester p;
	p.performance(argv[1]);
#ifdef TIMING
	Timing::timing.printAllTimes();
#endif
	time_t ende;
	time(&ende);
	double dt = difftime(ende, start);
	std::cout << "Run time: " << dt << "s" << std::endl;
	return 0;
}
