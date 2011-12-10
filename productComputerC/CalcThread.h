/*
 * CalcThread.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef CALCTHREAD_H_
#define CALCTHREAD_H_

#include "Algebra.h"
#include "ProductComputer.h"
#include "InnerProductCalculator.h"
#include "OuterProductCalculator.h"
#include "GeoProductCalculator.h"

#include <fstream>

#include "Functions.h"

class CalcThread: public Functions {
private:
	ProductComputer productComputer;
    InnerProductCalculator inner;
    OuterProductCalculator outer;
    GeoProductCalculator geo;
    int from;
    int to;
    int bladeCount;
    string filename;

public:
	CalcThread(const int from, const int to,const int bladeCount,const Algebra& algebra, string& filename) {
		this->filename = filename;
		this->from = from;
		this->to = to;
		this->bladeCount = bladeCount;
		productComputer.initialize(algebra);
	}

	void operator()() {
		std::ofstream out(filename.c_str());
		for (int i = from; i < to; i++) {
			for (int j = 0; j < bladeCount; j++) {
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

	virtual ~CalcThread();

};

#endif /* CALCTHREAD_H_ */
