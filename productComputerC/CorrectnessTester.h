/*
 * CorrectnessTester.h
 *
 *  Created on: 01.12.2011
 *      Author: christian
 */

#ifndef CORRECTNESSTESTER_H_
#define CORRECTNESSTESTER_H_

#include "Functions.h"
#include "BladeRef.h"
#include "Multivector.h"

class CorrectnessTester: public Functions {
public:
	CorrectnessTester();
	virtual ~CorrectnessTester();

	void parseBladeVector(string& str, Multivector& m);
	bool test(const char* strc,const  Multivector& m);

	void correctness5d();
};

#endif /* CORRECTNESSTESTER_H_ */
