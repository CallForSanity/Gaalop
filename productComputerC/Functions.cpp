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

	vector<BladeRef> blades;
	for (vector<BladeRef>::const_iterator ci1=m.blades.begin();ci1 != m.blades.end(); ++ci1)
		if ((*ci1).getPrefactor() != 0)
			blades.push_back(*ci1);


	int size = blades.size();
	int cur = 0;
	for (vector<BladeRef>::const_iterator ci=blades.begin();ci != blades.end(); ++ci) {
		const BladeRef bladeRef = *ci;

		switch(bladeRef.getPrefactor()) {
		case -1:
			out << "-E" << bladeRef.getIndex();
			cur++;
			if (cur != size) {
				if (blades[cur].getPrefactor() == 1)
					out << "+";
			}
			break;
		case 1:
			out << "E" << bladeRef.getIndex();
			cur++;
			if (cur != size) {
				if (blades[cur].getPrefactor() == 1)
					out << "+";
			}
			break;
		default:
			out << "Prefactor is out of range [-1,1]";

		}



	}

}
