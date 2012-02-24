#pragma once

#include <math.h>
#include <string>

#include "Definitions.h"

void getOutputAttributes(int outputNo, std::string& name, float& colorR,float& colorG,float& colorB,float& colorA) {
	switch (outputNo) {
	case 0: 
		colorR = 1; colorG = 0; colorB = 0; colorA = 1;
		break;
	}
}

int getOutputCount() {
	return 1;
}

void f(I& ox, I& oy, I& oz, I& t, I outputs[]) {
	outputs[0] = pow(ox+t,2) + pow(oy,2) + pow(oz,2) - 1.0f;
}

void df(I& ox, I& oy, I& oz, I& t, I outputs[]) {
	outputs[0] = 2.0f*(ox+t);
}

void fpdf(I& ox, I& oy, I& oz, I& t, I outputsf[], I outputsdf[]) {
	outputsf[0] = pow(ox+t,2) + pow(oy,2) + pow(oz,2) - 1.0f;
	outputsdf[0] = 2.0f*(ox+t);
}