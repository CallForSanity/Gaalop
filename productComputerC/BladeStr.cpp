/*
 * BladeStr.cpp
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#include "BladeStr.h"

BladeStr::BladeStr() {
}

BladeStr::~BladeStr() {
}

BladeStr::BladeStr(const string& baseVector)
{
	prefactor = 1;
	this->baseVectors.push_back(baseVector);
}

BladeStr::BladeStr(const vector<string>& baseVectors)
{
	prefactor = 1;
	this->baseVectors = baseVectors;
}

BladeStr::BladeStr(const float prefactor, const string& baseVector)
{
	this->prefactor = prefactor;
	this->baseVectors.push_back(baseVector);
}

BladeStr::BladeStr(const float prefactor, const vector<string>& baseVectors)
{
	this->prefactor = prefactor;
	this->baseVectors = baseVectors;
}

void BladeStr::print() {
	std::cout << prefactor << "[";
	for (vector<string>::const_iterator it = baseVectors.begin(); it != baseVectors.end(); ++it)
		std::cout << *it << ",";

	std::cout << "]";
}
