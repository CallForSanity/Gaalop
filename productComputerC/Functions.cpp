/*
 * Functions.cpp
 *
 *  Created on: 01.12.2011
 *      Author: christian
 */

#include "Functions.h"

Functions::Functions() {


}

Functions::~Functions() {

}

void Functions::outputMultivector(const Multivector& m,  std::ostream& out) {
	const vector<BladeRef>& blades = m.blades;
	int size = blades.size();
	int cur = 0;
	for (vector<BladeRef>::const_iterator ci=blades.begin();ci != blades.end(); ++ci) {
		const BladeRef bladeRef = *ci;

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
	if (cur == 0)
		out << "0";
}
