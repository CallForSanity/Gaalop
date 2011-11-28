//============================================================================
// Name        : ProductComputerC.cpp
// Author      : Christian Steinmetz
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================
#include <math.h>
#include <fstream>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>

#include <time.h>

#include "ProductComputer.h"
#include "Algebra5d.h"
#include "Algebra9d.h"
#include "InnerProductCalculator.h"
#include "OuterProductCalculator.h"
#include "GeoProductCalculator.h"
#include "Multivector.h"

void correctness5d(int from, int to);
void outputMultivector(Multivector& m, std::ostream& out);
//void performanceOn9d(int n);

#include "PermutableIterator.h"

#include "Timing.h"



int main(int argc, char** argv) {
	//if (argc != 3) return -1;
    //correctness5d(atoi(argv[1]), atoi(argv[2]));

	Timing::timing.startTime("Global");
	correctness5d(0,10);
	Timing::timing.stopTime("Global");
	Timing::timing.printAllTimes();
    //performanceOn9d(args);
	return 0;
}

void correctness5d(int from, int to) {
        ProductComputer productComputer;
        Algebra9d algebra;
        algebra.create();
        productComputer.initialize(algebra);

        InnerProductCalculator inner;
        OuterProductCalculator outer;
        GeoProductCalculator geo;

        std::ostringstream s;
        s << "products" << from << "-" << (to-1) << ".txt";
        std::ofstream out(s.str().c_str());

        int bladeCount = (int) pow(2,algebra.getBase().size());
        for (int i=from;i<to;i++) {
        	//std::cout << i << std::endl;
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

void outputMultivector(Multivector& m, std::ostream& out) {
	vector<BladeRef> blades = m.getBlades();
	int size = blades.size();
	int cur = 0;
	for (vector<BladeRef>::iterator ci=blades.begin();ci != blades.end(); ++ci) {
		BladeRef bladeRef = *ci;


		switch(bladeRef.getPrefactor()) {
		case -1:
			out << "-E" << bladeRef.getIndex();
			cur++;
			if (cur != size) out << ",";
			break;
		case 0:
			break;
		case 1:
			out << "E" << bladeRef.getIndex();
			cur++;
			if (cur != size) out << ",";
			break;
		default:
			out << "Prefactor is out of range [-1,1]";

		}



	}

}
