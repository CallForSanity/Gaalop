/*
 * Algebra.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */
#include "Algebra.h"

Algebra::Algebra() {

};

Algebra::~Algebra() {

};
vector<string> base;
vector<string> base2;
BaseSquareMap baseSquaresStr;
BaseChangeStrMap mapToPlusMinus;
BaseChangeStrMap mapToZeroInf;
void Algebra::print() {
	std::cout << "base: ";
	for (vector<string>::const_iterator b1 = base.begin(); b1 != base.end(); ++b1)
		std::cout << *b1 << ",";
	std::cout << std::endl;
	std::cout << "base2: ";
	for (vector<string>::const_iterator b2 = base2.begin(); b2 != base2.end(); ++b2)
		std::cout << *b2 << ",";
	std::cout << std::endl;
	std::cout << "baseSquares: ";
	for (unordered_map<string, int>::const_iterator bs1 = baseSquaresStr.begin(); bs1 != baseSquaresStr.end(); ++bs1) {
		std::pair<string, int> pair = *bs1;
		std::cout << pair.first << ":" << pair.second << ",";
	}
	std::cout << std::endl;
	std::cout << "mapTo+-: ";
	for (unordered_map<string, ListOfBladeStr>::const_iterator bs2 = mapToPlusMinus.begin(); bs2 != mapToPlusMinus.end(); ++bs2) {
		std::pair<string, ListOfBladeStr> pair1 = *bs2;
		std::cout << pair1.first << ":";
		for (ListOfBladeStr::iterator bs3 = pair1.second.begin(); bs3 != pair1.second.end(); ++bs3) {
			BladeStr bladeStr = *bs3;
			bladeStr.print();
			std::cout << ",";
		}
	}
	std::cout << std::endl;
	std::cout << "mapTo0inf: ";
	for (unordered_map<string, ListOfBladeStr>::const_iterator bs4 = mapToZeroInf.begin(); bs4 != mapToZeroInf.end(); ++bs4) {
		std::pair<string, ListOfBladeStr> pair2 = *bs4;
		std::cout << pair2.first << ":";
		for (ListOfBladeStr::iterator bs5 = pair2.second.begin(); bs5 != pair2.second.end(); ++bs5) {
			BladeStr bladeStr = *bs5;
			bladeStr.print();
			std::cout << ",";
		}
	}
	std::cout << std::endl;
}

