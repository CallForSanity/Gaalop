/*
 * BaseVisualizationBackend.h
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick
 */

#ifndef BASEVISUALIZATIONBACKEND_H_
#define BASEVISUALIZATIONBACKEND_H_

class BaseVisualizationBackend {
public:
	BaseVisualizationBackend();
	virtual ~BaseVisualizationBackend();

	virtual void drawPlane(const float x,const float y,const float z,			// some point on the plane
						   const float nx,const float ny,const float nz) = 0;	// normal vector
	virtual void drawSphere(const float x,const float y,const float z,	// point in the center
							const float diameter) = 0;

	virtual void drawCircle(const float x,const float y,const float z,		// point in the center
			   	   	   	    const float nx,const float ny,const float nz,	// normal vector
							const float diameter) = 0;
	virtual void drawLine(const float x,const float y,const float z,			// some point on the line
			   	   	   	  const float dx,const float dy,const float dz) = 0;	// direction vector

	virtual void drawPoint(const float x,const float y,const float z);
	// point pairs should be drawn by calling drawPoint twice
};

#endif /* BASEVISUALIZATIONBACKEND_H_ */
