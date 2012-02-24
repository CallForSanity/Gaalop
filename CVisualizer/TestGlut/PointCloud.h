#pragma once

#include <vector>

#include "Point3f.h"

class PointCloud
{
public:
	std::vector<Point3f> points;
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
		for (std::vector<Point3f>::iterator it = points.begin(); it != points.end(); ++it) {
			Point3f& p = *it;
			glVertex3f(p.x,p.y,p.z);
		}
		glEnd();
	}
};

