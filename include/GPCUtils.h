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
	static void setVisualizationBackend(BaseVisualizationBackend* visualizationBackend_);

	/*
	Draws a multivector represented by an array of 32-floats.
	First it analyzes what kind of multivector was passed
	and which parameters it has,
	and it then uses the draw*() Methods provided by
	visualizationBackend to do the actually drawing.
	*/
	static void drawMultivector(const float* mvArray);

private:
	static std::auto_ptr<BaseVisualizationBackend> visualizationBackend;
};

#endif /* GPCUTILS_H_ */
