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
	static void setVisualizationBackend( std::auto_ptr<BaseVisualizationBackend> visualizationBackend);

	static void setVisualizationCallback( void (*renderingCallback) (void) );
	static void setKeyboardCallback( void (*keyboardCallback)( unsigned char, int, int ));
	static void setDrawColor(const float r, const float g, const float b);
	static void setDrawColor(const float r, const float g, const float b, const float a);

	static void drawMultivector(const float mvArray[32]);

private:
	static std::auto_ptr<BaseVisualizationBackend> m_visualizationBackend;
};

#endif /* GPCUTILS_H_ */
