#pragma once

#include <FromGaalop.h>
#include "RayTracer.h"

#include "PointCloud.h"

std::vector<PointCloud> pointClouds;

float* inputs;

void calculatePoints() {
	int outputCount = getOutputCount();
	pointClouds.clear();

	for (int i=0;i<outputCount;++i) {
		pointClouds.push_back(PointCloud());
		PointCloud& cloud = pointClouds[i];
		findZeroLocations(i, cloud.points, inputs);
		getOutputAttributes(i, cloud.name, cloud.colR, cloud.colG, cloud.colB, cloud.colA);
	}
}