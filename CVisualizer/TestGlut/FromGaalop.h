#pragma once

#include "Definitions.h"

void getOutputAttributes(int outputNo, std::string& name, float& colorR,float& colorG,float& colorB,float& colorA) {
	switch (outputNo) {
		case 0: 
		colorR = 0.0f; 	colorG = 1.0f; 	colorB = 0.0f; 	colorA = 1.0f;
             name = "b";
		break;
		case 1: 
		colorR = 1.0f; 	colorG = 0.0f; 	colorB = 0.0f; 	colorA = 1.0f;
             name = "a";
		break;
   }
}

int getInputCount() {
	return 0;
}
void getInputName(int inputNo, std::string& name) {
}

int getOutputCount() {
	return 2;
}

void fpdf(I& ox, I& oy, I& oz, I& t, float inputs[], I outputsF[], I outputsDF[]) {
outputsF[1] = ((oz * oz / 2.0f + oy * oy / 2.0f + (ox + t) * (ox + t) / 2.0f) - 2.0f) * ((oz * oz / 2.0f + oy * oy / 2.0f + (ox + t) * (ox + t) / 2.0f) - 2.0f); // 1.0
outputsDF[1] = (1.0f * pow(t,3.0f) + 3.0f * ox * t * t + ((1.0f * oz * oz + 1.0f * oy * oy + 3.0f * ox * ox) - 4.0f) * t + 1.0f * ox * oz * oz + 1.0f * ox * oy * oy + 1.0f * pow(ox,3.0f)) - 4.0f * ox; // 1.0
outputsF[0] = (((oz * oz / 2.0f - oz + oy * oy / 2.0f) - oy + (ox + t) * (ox + t) / 2.0f) - (ox + t) + 1.0f) * (((oz * oz / 2.0f - oz + oy * oy / 2.0f) - oy + (ox + t) * (ox + t) / 2.0f) - (ox + t) + 1.0f); // 1.0
outputsDF[0] = ((1.0f * pow(t,3.0f) + (3.0f * ox - 3.0f) * t * t + (((1.0f * oz * oz - 2.0f * oz + 1.0f * oy * oy) - 2.0f * oy + 3.0f * ox * ox) - 6.0f * ox + 4.0f) * t + (1.0f * ox - 1.0f) * oz * oz + (2.0f - 2.0f * ox) * oz + (1.0f * ox - 1.0f) * oy * oy + (2.0f - 2.0f * ox) * oy + 1.0f * pow(ox,3.0f)) - 3.0f * ox * ox + 4.0f * ox) - 2.0f; // 1.0
}
