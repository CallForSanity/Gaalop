#pragma once

#include <vector>
#include "Definitions.h"
#include <FromGaalop.h>

#include "Vec3.h"

class RayMethodThread
{
public:
	RayMethodThread(void) { }
	~RayMethodThread(void) { }

	float fromOY_Incl;
	float toOY_Excl;
	float a;
	float dist;
	int objectNo;
	float* inputs;

	std::vector<Vec3f>* points;

	float ox;
	float oy;
	float oz;

	void refinement(I& t) {
        bool refine = true;
		float center;
        while (refine) {
            
            center = center(t);

			I* outputsf = new I[OUTPUTCOUNT];
			I* outputsdf = new I[OUTPUTCOUNT];

			fpdf(I(ox),I(oy),I(oz),I(t.lower()),inputs,outputsf,outputsdf);
            double lo = outputsf[objectNo].lower();
			fpdf(I(ox),I(oy),I(oz),I(center),inputs,outputsf,outputsdf);
            double ce = outputsf[objectNo].lower();
			delete[] outputsf;
			delete[] outputsdf;
            
            if (abs(ce) <= 0.01) refine = false;
            if (width(t) < 0.001) return;
        
            if (ce*lo < 0) 
				t.set(t.lower(),center);
			else
                t.set(center,t.upper());

        }

        points->push_back(Vec3f(ox+center,oy,oz));
    }

	void isolation(I& t) {

		I* outputsf = new I[OUTPUTCOUNT];
		I* outputsdf = new I[OUTPUTCOUNT];

		fpdf(I(ox),I(oy),I(oz),t,inputs,outputsf,outputsdf);

		bool zeroInF = zero_in(outputsf[objectNo]);
		bool zeroInDF = zero_in(outputsdf[objectNo]);
		delete[] outputsf;
		delete[] outputsdf;

		if (zeroInF) {
            if (zeroInDF) {
				float center = center(t);
                if (width(t) > 0.05) {
                    isolation(I(t.lower(), center));
                    isolation(I(center, t.upper()));
                } else 
					points->push_back(Vec3f(ox+center,oy,oz));
                
            } else 
                refinement(t);
        }

    }

	void operator()() {
		ox = -a;
        for (oy = fromOY_Incl; oy <= toOY_Excl; oy += dist) 
            for (oz = -a; oz <= a; oz += dist) {
				I t(0.0f,2*a);
				isolation(t);
            }
	}
};

