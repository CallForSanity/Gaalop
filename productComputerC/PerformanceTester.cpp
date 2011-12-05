/*
 * PerformanceTester.cpp
 *
 *  Created on: 01.12.2011
 *      Author: christian
 */

#include "PerformanceTester.h"

PerformanceTester::PerformanceTester() {
	onlyInInterval = false;

}

PerformanceTester::~PerformanceTester() {

}

#include "AlgebraFromFile.h"
#include "Timing.h"

void PerformanceTester::performance(const string& outDir) {
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
        if (onlyInInterval)
        	sOut << outDir << "products"<< from << ".txt";
        else
        	sOut << outDir << "products.txt";
        std::ofstream out(sOut.str().c_str());

        int bladeCount = (int) pow(2,algebra.getBase().size());
#ifdef TIMING
        Timing::timing.startTime("Global");
#endif

        if (!onlyInInterval) {
        	from = 0;
        	to = bladeCount;
        }

        for (int i=from;i<to;i++) {
        	std::cout << i << std::endl;
            for (int j=0;j<bladeCount;j++) {
            	Multivector innerM;
#ifdef TIMING
            	Timing::timing.startTime("Inner");
#endif
            	productComputer.calcProduct(i, j, inner, innerM);
#ifdef TIMING
            	Timing::timing.stopTime("Inner");
            	Timing::timing.startTime("Outer");
#endif
            	Multivector outerM;
            	productComputer.calcProduct(i, j, outer, outerM);
#ifdef TIMING
            	Timing::timing.stopTime("Outer");
            	Timing::timing.startTime("Geo");
#endif
            	Multivector geoM;
            	productComputer.calcProduct(i, j, geo, geoM);
#ifdef TIMING
            	Timing::timing.stopTime("Geo");
#endif
            	out << "E" << i << ";E" << j << ";";
#ifdef TIMING
            	Timing::timing.startTime("Printout");
#endif
            	outputMultivector(innerM, out);
            	out << ";";
            	outputMultivector(outerM, out);
            	out << ";";
            	outputMultivector(geoM, out);
#ifdef TIMING
            	Timing::timing.stopTime("Printout");
#endif
            	out << std::endl;
            }
        }
#ifdef TIMING
        Timing::timing.stopTime("Global");
#endif

    }
