/*
 * Algebra9d.cpp
 *
 *  Created on: 25.11.2011
 *      Author: christian
 */

#include "Algebra9d.h"

Algebra9d::Algebra9d() {

}

Algebra9d::~Algebra9d() {

}

void Algebra9d::create() {
	string e1 = "e1";
	string e2 = "e2";
	string e3 = "e3";
	string e4 = "e4";
	string e5 = "e5";
	string e6 = "e6";
	string e7 = "e7";
	string e8 = "e8";
	string e9 = "e9";
	string einfx = "einfx";
	string einfy = "einfy";
	string einfz = "einfz";
	string e0x = "e0x";
	string e0y = "e0y";
	string e0z = "e0z";

	base.push_back(e1);
	base.push_back(e2);
	base.push_back(e3);
	base.push_back(einfx);
	base.push_back(einfy);
	base.push_back(einfz);
	base.push_back(e0x);
	base.push_back(e0y);
	base.push_back(e0z);

	base2.push_back(e1);
	base2.push_back(e2);
	base2.push_back(e3);
	base2.push_back(e4);
	base2.push_back(e5);
	base2.push_back(e6);
	base2.push_back(e7);
	base2.push_back(e8);
	base2.push_back(e9);

	baseSquaresStr[e1] = 1;
	baseSquaresStr[e2] = 1;
	baseSquaresStr[e3] = 1;
	baseSquaresStr[e4] = 1;
	baseSquaresStr[e5] = 1;
	baseSquaresStr[e6] = 1;
	baseSquaresStr[e7] = -1;
	baseSquaresStr[e8] = -1;
	baseSquaresStr[e9] = -1;

	//e0(em,ep)

	/*
	e0x=0.5*e7-0.5*e4,
	e0y=0.5*e8-0.5*e5,
	e0z=0.5*e9-0.5*e6,
	*/
	ListOfBladeStr listE0x;
	listE0x.push_back(BladeStr(0.5,e7));
	listE0x.push_back(BladeStr(-0.5,e4));
	mapToPlusMinus[e0x] = listE0x;

	ListOfBladeStr listE0y;
	listE0y.push_back(BladeStr(0.5,e8));
	listE0y.push_back(BladeStr(-0.5,e5));
	mapToPlusMinus[e0y] = listE0y;

	ListOfBladeStr listE0z;
	listE0z.push_back(BladeStr(0.5,e9));
	listE0z.push_back(BladeStr(-0.5,e6));
	mapToPlusMinus[e0z] = listE0z;

	/*
	einfx=e4+e7,
	einfy=e5+e8,
	einfz=e6+e9
	*/
	ListOfBladeStr listEinfx;
	listEinfx.push_back(BladeStr(e4));
	listEinfx.push_back(BladeStr(e7));
	mapToPlusMinus[einfx] = listEinfx;

	ListOfBladeStr listEinfy;
	listEinfy.push_back(BladeStr(e5));
	listEinfy.push_back(BladeStr(e8));
	mapToPlusMinus[einfy] = listEinfy;

	ListOfBladeStr listEinfz;
	listEinfz.push_back(BladeStr(e6));
	listEinfz.push_back(BladeStr(e9));
	mapToPlusMinus[einfz] = listEinfz;

/*
	e4=0.5*einfx-e0x,
	e5=0.5*einfy-e0y,
	e6=0.5*einfz-e0z,
*/

	ListOfBladeStr listE4;
	listE4.push_back(BladeStr(0.5f, einfx));
	listE4.push_back(BladeStr(-1, e0x));
	mapToZeroInf[e4] = listE4;

	ListOfBladeStr listE5;
	listE5.push_back(BladeStr(0.5f, einfy));
	listE5.push_back(BladeStr(-1, e0y));
	mapToZeroInf[e5] = listE5;

	ListOfBladeStr listE6;
	listE6.push_back(BladeStr(0.5f, einfz));
	listE6.push_back(BladeStr(-1, e0z));
	mapToZeroInf[e6] = listE6;
/*
  	e7=0.5*einfx+e0x,
	e8=0.5*einfy+e0y,
	e9=0.5*einfz+e0z
*/
	ListOfBladeStr listE7;
	listE7.push_back(BladeStr(0.5f, einfx));
	listE7.push_back(BladeStr(e0x));
	mapToZeroInf[e7] = listE7;

	ListOfBladeStr listE8;
	listE8.push_back(BladeStr(0.5f, einfy));
	listE8.push_back(BladeStr(e0y));
	mapToZeroInf[e8] = listE8;

	ListOfBladeStr listE9;
	listE9.push_back(BladeStr(0.5f, einfz));
	listE9.push_back(BladeStr(e0z));
	mapToZeroInf[e9] = listE9;

}
