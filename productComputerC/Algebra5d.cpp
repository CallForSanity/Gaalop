/*
 * Algebra5d.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "Algebra5d.h"

Algebra5d::Algebra5d() {
}

Algebra5d::~Algebra5d() {
}

void Algebra5d::create() {
	base.push_back("e1");
	base.push_back("e2");
	base.push_back("e3");
	base.push_back("einf");
	base.push_back("e0");

	base2.push_back("e1");
	base2.push_back("e2");
	base2.push_back("e3");
	base2.push_back("ep");
	base2.push_back("em");

	baseSquaresStr["e1"] = 1;
	baseSquaresStr["e2"] = 1;
	baseSquaresStr["e3"] = 1;
	baseSquaresStr["ep"] = 1;
	baseSquaresStr["em"] = -1;

	//e0(em,ep)
	ListOfBladeStr listE0;
	string em = "em";
	string ep = "ep";
	string e0 = "e0";
	string einf = "einf";
	listE0.push_back(BladeStr(0.5,em));
	listE0.push_back(BladeStr(-0.5,ep));
	mapToPlusMinus[e0] = listE0;

	//einf(em,ep)
	ListOfBladeStr listEinf;
	listEinf.push_back(BladeStr(em));
	listEinf.push_back(BladeStr(ep));
	mapToPlusMinus[einf] = listEinf;


	//em(einf,e0)
	ListOfBladeStr listEm;
	listEm.push_back(BladeStr(0.5f, einf));
	listEm.push_back(BladeStr(e0));
	mapToZeroInf[em] = listEm;

	//ep(einf,e0)
	ListOfBladeStr listEp;
	listEp.push_back(BladeStr(0.5f, einf));
	listEp.push_back(BladeStr(-1, e0));
	mapToZeroInf[ep] = listEp;

}
