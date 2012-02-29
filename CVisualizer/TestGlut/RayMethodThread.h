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

	void refinementRegulaFalsi(float a, float b) {
        bool refine = true;
		float c;

		float* outputsf = new float[OUTPUTCOUNT];

		f(ox,oy,oz,a,inputs,outputsf);
		float fa = outputsf[objectNo];
		f(ox,oy,oz,b,inputs,outputsf);
		float fb = outputsf[objectNo];
		c = (float) ((a*fb-b*fa)/(fb-fa));
		f(ox,oy,oz,c,inputs,outputsf);
		float fc = outputsf[objectNo];

        while (refine) {
			//if (width(t) < 0.001) { return; delete[] outputsf; } // TODO why?
			if ((b-a) < 0.001) refine = false;  // TODO maybe this change to direct return if (width(t) < 0.001) return
			else {
				if (fa*fc > 0) {
					a = c;
					fa = fc;
				} else {
					b = c;
					fb = fc;
				}

				c = (float) ((a*fb-b*fa)/(fb-fa));
				f(ox,oy,oz,c,inputs,outputsf);
				fc = outputsf[objectNo];

            
				if (abs(fc) <= 0.01) refine = false;
			}

        }
		delete[] outputsf;

        points->push_back(Vec3f(ox+c,oy,oz));
    }

	void refinementBisection(float a, float b) {
        bool refine = true;
		float center;

		float* outputsf = new float[OUTPUTCOUNT];
		f(ox,oy,oz,a,inputs,outputsf);
        double lo = outputsf[objectNo];
		f(ox,oy,oz,center,inputs,outputsf);
        double ce = outputsf[objectNo];

        while (refine) {
            if ((b-a) < 0.001) {
				delete[] outputsf;
				return;
			}

            center = (a+b)/2.0f;

            if (abs(ce) <= 0.01) refine = false;
        
            if (ce*lo < 0) {
				b = center;
			}
			else {
				a = center;
				lo = ce;
			}
			f(ox,oy,oz,center,inputs,outputsf);
			ce = outputsf[objectNo];
        }
		delete[] outputsf;

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
                refinementRegulaFalsi(t.lower(),t.upper());
				//refinementBisection(t.lower(),t.upper());
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

