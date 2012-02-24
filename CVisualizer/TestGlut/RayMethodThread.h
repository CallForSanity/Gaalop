#pragma once

#include <vector>
#include "Definitions.h"
#include "FromGaalop.h"

#include "Point3f.h"

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

	std::vector<Point3f>* points;

	float ox;
	float oy;
	float oz;

	void refinement(I& t) {
        bool refine = true;
		float center;
        while (refine) {
            
            center = center(t);

            I outputsf[2];
			f(I(ox),I(oy),I(oz),I(t.lower()),outputsf);
            double lo = outputsf[objectNo].lower();
			f(I(ox),I(oy),I(oz),I(center),outputsf);

            double ce = outputsf[objectNo].lower();
            
            if (abs(ce) <= 0.01) refine = false;
            if (width(t) < 0.001) return;
        
            if (ce*lo < 0) 
				t.set(t.lower(),center);
			else
                t.set(center,t.upper());

        }

        points->push_back(Point3f(ox+center,oy,oz));
    }

	void isolation(I& t) {

		I outputsf[2];
		I outputsdf[2];

		fpdf(I(ox),I(oy),I(oz),t,outputsf,outputsdf);

		if (zero_in(outputsf[objectNo])) {
            if (zero_in(outputsdf[objectNo])) {
				float center = center(t);
                if (width(t) > 0.05) {
                    isolation(I(t.lower(), center));
                    isolation(I(center, t.upper()));
                } else 
					points->push_back(Point3f(ox+center,oy,oz));
                
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

