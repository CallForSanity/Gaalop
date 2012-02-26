#pragma once

#include <vector>

#include "Vec3.h"

class PointCloud
{
public:
	std::vector<Vec3f> points;
	float colR;
	float colG;
	float colB;
	float colA;
	std::string name;

	PointCloud(void) { }
	~PointCloud(void) { }

	void draw() {
		glPointSize(2.0);
		glColor4f(colR, colG, colB, colA);
		glBegin(GL_POINTS);
		for (std::vector<Vec3f>::iterator it = points.begin(); it != points.end(); ++it) {
			Vec3f& p = *it;
			glVertex3f(p.x,p.y,p.z);
		}
		glEnd();
	}
};

