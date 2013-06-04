/*
 * OpenGLVisualizationBackend.h
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick
 */

#ifndef OPENGLVISUALIZATIONBACKEND_H_
#define OPENGLVISUALIZATIONBACKEND_H_

#include "BaseVisualizationBackend.h"

class OpenGLVisualizationBackend: public BaseVisualizationBackend {
public:
	OpenGLVisualizationBackend();
	virtual ~OpenGLVisualizationBackend();

	void drawPlane(const float x,const float y,const float z,			// some point on the plane
				   const float nx,const float ny,const float nz);	// normal vector
	void drawSphere(const float x,const float y,const float z,	// point in the center
					const float diameter);

	void drawCircle(const float x,const float y,const float z,		// point in the center
	   	   	   	    const float nx,const float ny,const float nz,	// normal vector
					const float diameter);
	void drawLine(const float x,const float y,const float z,			// some point on the line
	   	   	   	  const float dx,const float dy,const float dz);	// direction vector

	void drawPoint(const float x,const float y,const float z);
	// point pairs should be drawn by calling drawPoint twice
};

#endif /* OPENGLVISUALIZATIONBACKEND_H_ */
