#pragma once

#include "Definitions.h"

void getOutputAttributes(int outputNo, std::string& name, float& colorR,float& colorG,float& colorB,float& colorA) {
	switch (outputNo) {
		case 0: 
		colorR = 0.0f; 	colorG = 1.0f; 	colorB = 0.0f; 	colorA = 1.0f;
             name = "b";
		break;
   }
}

int getInputCount() {
	return 1;
}
void getInputName(int inputNo, std::string& name) {
	switch (inputNo) {
		case 0: 
		name = "r";
		break;
   }
}

int getOutputCount() {
	return 1;
}

void fpdf(I& ox, I& oy, I& oz, I& t, float inputs[], I outputsF[], I outputsDF[]) {
outputsF[0] = (((oz * oz / 2.0f - oz + oy * oy / 2.0f) - oy + (ox + t) * (ox + t) / 2.0f) - (ox + t) - inputs[0] * inputs[0] / 2.0f + 1.5f) * (((oz * oz / 2.0f - oz + oy * oy / 2.0f) - oy + (ox + t) * (ox + t) / 2.0f) - (ox + t) - inputs[0] * inputs[0] / 2.0f + 1.5f); // 1.0
outputsDF[0] = ((1.0f * pow(t,3.0f) + (3.0f * ox - 3.0f) * t * t + (((1.0f * oz * oz - 2.0f * oz + 1.0f * oy * oy) - 2.0f * oy + 3.0f * ox * ox) - 6.0f * ox - 1.0f * inputs[0] * inputs[0] + 5.0f) * t + (1.0f * ox - 1.0f) * oz * oz + (2.0f - 2.0f * ox) * oz + (1.0f * ox - 1.0f) * oy * oy + (2.0f - 2.0f * ox) * oy + 1.0f * pow(ox,3.0f)) - 3.0f * ox * ox + (5.0f - 1.0f * inputs[0] * inputs[0]) * ox + 1.0f * inputs[0] * inputs[0]) - 3.0f; // 1.0
}
