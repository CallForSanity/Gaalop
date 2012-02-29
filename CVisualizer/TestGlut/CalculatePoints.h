#pragma once

#include <FromGaalop.h>
#include "RayTracer.h"

#include "PointCloud.h"

std::vector<PointCloud> pointClouds;

float* inputs;

void calculatePoints() {
	pointClouds.clear();

	for (int i=0;i<OUTPUTCOUNT;++i) {
		pointClouds.push_back(PointCloud());
		PointCloud& cloud = pointClouds[i];
		getOutputAttributes(i, cloud.name, cloud.colR, cloud.colG, cloud.colB, cloud.colA);
		std::cout << cloud.name << ": ";
		findZeroLocations(i, cloud.points, inputs);
		
	}
}