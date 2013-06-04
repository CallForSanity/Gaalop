/*
 * GPCUtils.h
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick
 */

#ifndef GPCUTILS_H_
#define GPCUTILS_H_

#include <memory>
#include "BaseVisualizationBackend.h"

class GPCUtils {
public:
	GPCUtils();
	~GPCUtils();

	static GPCUtils& getInstance();
	static BaseVisualizationBackend* getVisualizationBackend();

private:
	static std::auto_ptr<BaseVisualizationBackend> visualizationBackend;
};

#endif /* GPCUTILS_H_ */
