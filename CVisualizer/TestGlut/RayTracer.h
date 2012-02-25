#pragma once

#include <vector>
#include <boost/thread.hpp>

#include "FromGaalop.h"
#include "RayMethodThread.h"
#include "Point3f.h"

#include <iostream>

void findZeroLocations(int objectNo, std::vector<Point3f>& points, float* inputs) {
	float a = 5.0f; // cubeEdgeLength
	float dist = 0.1f; //density

	std::cout << "#Rays: " << (4*a*a)/(dist*dist) << std::endl;

    RayMethodThread threads[4];
	std::vector<Point3f> pointsThreads[4];

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
		for (std::vector<Point3f>::iterator it = pointsThreads[i].begin(); it != pointsThreads[i].end(); ++it) 
			points.push_back(*it);
	
		
	std::cout << points.size() << std::endl;
}