/*
 * PerformanceTester.cpp
 *
 *  Created on: 01.12.2011
 *      Author: christian
 */

#include "PerformanceTester.h"

PerformanceTester::PerformanceTester() {

}

PerformanceTester::~PerformanceTester() {

}

#include "AlgebraFromFile.h"

#include <boost/thread.hpp>
#include "CalcThread.h"

void PerformanceTester::performance(const string& outDir, int threadCount) {
        ProductComputer productComputer;
        AlgebraFromFile algebra;
        algebra.create();
        std::ostringstream sIn;
        sIn << outDir << "definition.csv";
        algebra.loadFromFile(sIn.str().c_str());

        productComputer.initialize(algebra);

        InnerProductCalculator inner;
        OuterProductCalculator outer;
        GeoProductCalculator geo;

        std::ostringstream sOut;
        sOut << outDir << "products.txt";
        std::ofstream out(sOut.str().c_str());

        int bladeCount = (int) pow(2,algebra.getBase().size());

        int nofintervals = bladeCount / threadCount;
		int remainder = bladeCount % threadCount;
		int interval = 0;
		boost::thread threads[threadCount];

		// fork & join & collect results ...
		int x = 0;
		for (int i = 0; i < threadCount; ++i) {
			std::ostringstream sOut;
			sOut << outDir << "products" << i << ".txt";
			string str = sOut.str();
			int intervals = nofintervals;
			if (i < remainder)
				++intervals;
			interval += intervals;
			int xleft = x;
			x = interval;
			threads[i] = boost::thread(CalcThread(xleft,x,bladeCount,algebra,str));
			out.close();
		}

        for (int i = 0; i < threadCount; ++i) {
			threads[i].join();
		}
        return;


        for (int i=0;i<bladeCount;i++) {
        	std::cout << i << std::endl;
            for (int j=0;j<bladeCount;j++) {
            	Multivector innerM;
            	productComputer.calcProduct(i, j, inner, innerM);
            	Multivector outerM;
            	productComputer.calcProduct(i, j, outer, outerM);
            	Multivector geoM;
            	productComputer.calcProduct(i, j, geo, geoM);
            	out << "E" << i << ";E" << j << ";";
            	outputMultivector(innerM, out);
            	out << ";";
            	outputMultivector(outerM, out);
            	out << ";";
            	outputMultivector(geoM, out);
            	out << std::endl;
            }
        }

    }
