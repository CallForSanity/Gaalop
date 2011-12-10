//============================================================================
// Name        : ProductComputerC.cpp
// Author      : Christian Steinmetz
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include "CorrectnessTester.h"
#include "PerformanceTester.h"
#include <boost/thread.hpp>

int main(int argc, char** argv) {
	if (argc != 3) {
		std::cout << "You have to specify an 2 arguments: algebraDirPath nthreads" << std::endl;
		return -1;
	}
	time_t start;
	time(&start);
	//CorrectnessTester c;
	//c.correctness5d();
	PerformanceTester p;
	p.performance(argv[1],atoi(argv[2]));
	time_t ende;
	time(&ende);
	double dt = difftime(ende, start);
	std::cout << "Run time: " << dt << "s" << std::endl;
	return 0;
}
