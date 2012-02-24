#pragma once

#include "incH.h"

#include <math.h>

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