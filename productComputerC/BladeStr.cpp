/*
 * BladeStr.cpp
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#include "BladeStr.h"

BladeStr::~BladeStr() {
}

BladeStr::BladeStr(string& baseVector)
{
	prefactor = 1;
	this->baseVectors.push_back(baseVector);
}

BladeStr::BladeStr(vector<string>& baseVectors)
{
	prefactor = 1;
	this->baseVectors = baseVectors;
}

BladeStr::BladeStr(float prefactor, string& baseVector)
{
	this->prefactor = prefactor;
	this->baseVectors.push_back(baseVector);
}

BladeStr::BladeStr(float prefactor, vector<string>& baseVectors)
{
	this->prefactor = prefactor;
	this->baseVectors = baseVectors;
}
