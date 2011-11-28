/*
 * CalcThread.cpp
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#include "CalcThread.h"


CalcThread::~CalcThread() {
}

void CalcThread::run() {
	for (int i = from; i < to; i++) {
		for (int j = 0; j < bladeCount; j++) {
			Multivector m;
			productComputer.calcProduct(i, j, inner, m);
			productComputer.calcProduct(i, j, outer, m);
			productComputer.calcProduct(i, j, geo, m);
		}
	}
}
