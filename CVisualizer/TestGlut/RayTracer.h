#pragma once

#include <vector>
#include <boost/thread.hpp>

#include <FromGaalop.h>
#include "RayMethodThread.h"
#include "Vec3.h"

#include <iostream>

float cubeEdgeLength = 5.0f;
float density = 0.1f;

void findZeroLocations(int objectNo, std::vector<Vec3f>& points, float* inputs) {
	float a = cubeEdgeLength; // cubeEdgeLength
	float dist = density; //density

	std::cout << "#Rays: " << (4*a*a)/(dist*dist) << std::flush;

    RayMethodThread threads[4];
	std::vector<Vec3f> pointsThreads[4];

	for (int i=0;i<4;i++) {
        threads[i].fromOY_Incl = (i*2*a)/4.0f - a;
        threads[i].toOY_Excl = ((i != 4.0f-1) ? ((i+1)*2*a)/4.0f : 2*a) - a; 
		threads[i].a = a;
		threads[i].dist = dist;
		threads[i].objectNo = objectNo;
		threads[i].points = &pointsThreads[i];
		threads[i].inputs = inputs;
	}

	boost::thread thread0(threads[0]);
	boost::thread thread1(threads[1]);
	boost::thread thread2(threads[2]);
	boost::thread thread3(threads[3]);

	thread0.join();
	thread1.join();
	thread2.join();
	thread3.join();

	//copy all points of the threads to point list points
	for (int i=0;i<4;++i) 
		for (std::vector<Vec3f>::iterator it = pointsThreads[i].begin(); it != pointsThreads[i].end(); ++it) 
			points.push_back(*it);
	
		
	std::cout << ", #Points: " << points.size() << std::endl;
}