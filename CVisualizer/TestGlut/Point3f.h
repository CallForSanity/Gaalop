#pragma once
class Point3f
{
public:
	float x,y,z;

	Point3f(void) { }
	Point3f(float x, float y, float z) : x(x),y(y),z(z) { }
	~Point3f(void) { }
};

