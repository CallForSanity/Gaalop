/*
 * CorrectnessTester.cpp
 *
 *  Created on: 01.12.2011
 *      Author: christian
 */

#include "CorrectnessTester.h"

CorrectnessTester::CorrectnessTester() {
}

CorrectnessTester::~CorrectnessTester() {
}

#include "Parser.h"
#include <algorithm>

bool myfunction (const BladeRef ref1,const  BladeRef ref2) { return (ref1.getIndex()<ref2.getIndex()); }

void CorrectnessTester::parseBladeVector( string& str, Multivector& m) {
	Parser parser;
	parser.parseMultivector(str,m);
}

bool CorrectnessTester::test(const char* strc,const  Multivector& m) {
	string str(strc);
	Multivector mblades1;
	parseBladeVector(str, mblades1);


	vector<BladeRef>& f1 = mblades1.blades;
	const vector<BladeRef>& f2 = m.blades;

	vector<BladeRef> blades1;
	vector<BladeRef> blades2;

	for (vector<BladeRef>::const_iterator ci = f1.begin(); ci != f1.end(); ++ci) {
		if (ci->getPrefactor() != 0)
			blades1.push_back(*ci);
	}
	for (vector<BladeRef>::const_iterator ci = f2.begin(); ci != f2.end(); ++ci) {
		if (ci->getPrefactor() != 0)
			blades2.push_back(*ci);
	}


	if (blades1.size() != blades2.size()) return false;
	sort(blades1.begin(), blades1.end(), myfunction);
	sort(blades2.begin(), blades2.end(), myfunction);

	vector<BladeRef>::const_iterator ci1 = blades1.begin();
	for (vector<BladeRef>::const_iterator ci = blades2.begin(); ci != blades2.end(); ++ci) {
		if (ci->getIndex() != ci1->getIndex()) {
			return false;
		}
		if (ci->getPrefactor() != ci1->getPrefactor()) {
			return false;
		}
		++ci1;
	}
	return true;
}




#include <string.h>

void CorrectnessTester::correctness5d() {
	ProductComputer productComputer;
	Algebra5d algebra;
	algebra.create();
	productComputer.initialize(algebra);

	InnerProductCalculator inner;
	OuterProductCalculator outer;
	GeoProductCalculator geo;

	std::ifstream file("/home/christian/algebra/5d/Jproducts.csv");
	int bladeCount = (int) pow(2,algebra.getBase().size());
	string line;
	for (int i=0;i<bladeCount;i++) {
		for (int j=0;j<bladeCount;j++) {
			//test i
			getline ( file, line);
			string part = line.substr(0,line.find(';'));
			line = line.substr(line.find(';')+1);

			std::ostringstream s1;
			s1 << "E" << i;
			if (strcmp(part.c_str(),s1.str().c_str()) != 0)
				std::cerr << "i-Error on E" << i <<  "," << j << " - " << part << std::endl;

			//test j
			part = line.substr(0,line.find(';'));
			line = line.substr(line.find(';')+1);
			std::ostringstream s2;
			s2 << "E" << j;
			if (strcmp(part.c_str(),s2.str().c_str()) != 0)
					std::cerr << "j-Error on E" << i <<  "," << j << std::endl;

			part = line.substr(0,line.find(';'));
			line = line.substr(line.find(';')+1);
			std::ostringstream s3;
			Multivector innerM;
			productComputer.calcProduct(i, j, inner, innerM);
			if (!test(part.c_str(),innerM)) {
				std::cerr << "Error on E" << i << " . " << j << " because " << part << " != ";
				outputMultivector(innerM,std::cerr);
				std::cerr <<std::endl;
			}
			part = line.substr(0,line.find(';'));
			line = line.substr(line.find(';')+1);
			Multivector outerM;
			productComputer.calcProduct(i, j, outer, outerM);
			if (!test(part.c_str(),outerM)) {
				std::cerr << "Error on E" << i << " ^ " << j << " because " << part << " != ";
				outputMultivector(outerM,std::cerr);
				std::cerr <<std::endl;
			}

			Multivector geoM;
			productComputer.calcProduct(i, j, geo, geoM);
			if (!test(line.c_str(),geoM)) {
				std::cerr << "Error on E" << i << " * " << j << " because " << part << " != ";
				outputMultivector(geoM,std::cerr);
				std::cerr <<std::endl;
			}

		}
	}
	std::cout<< "correct (passed 5d)" << std::endl;
	file.close();
}
