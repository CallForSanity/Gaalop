/*
 * GPCUtils.cpp
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick
 */

#include "GPCUtils.h"

GPCUtils::GPCUtils() {
	// TODO Auto-generated constructor stub

}

GPCUtils::~GPCUtils() {
	// TODO Auto-generated destructor stub
}

GPCUtils& GPCUtils::getInstance() {
	static GPCUtils gpcUtils;
	return gpcUtils;
}

BaseVisualizationBackend* GPCUtils::getVisualizationBackend() {
	return visualizationBackend.get();
}

void GPCUtils::setVisualizationBackend(BaseVisualizationBackend* visualizationBackend_) {
	visualizationBackend.reset(visualizationBackend_);
}

std::auto_ptr<BaseVisualizationBackend> GPCUtils::visualizationBackend;
