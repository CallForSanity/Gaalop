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

	virtual void init() = 0;

	static void setGpcVisualizationCallback( void (*gpcVisualizationCallback) (void) );
	static void setGpcKeyboardCallback( void (*gpcKeyboardCallback)( unsigned char, int, int ) );
	
	virtual void setDrawColor(const float r, const float g, const float b, const float a) = 0;

	virtual void drawPlane(const float x,const float y,const float z,			// some point on the plane
						   const float nx,const float ny,const float nz) = 0;	// normal vector
	virtual void drawSphere(const float x,const float y,const float z,	// point in the center
							const float diameter) = 0;

	virtual void drawCircle(const float x,const float y,const float z,		// point in the center
			   	   	   	    const float nx,const float ny,const float nz,	// normal vector
							const float diameter, const bool dotted = false) = 0;
	virtual void drawLine(const float x,const float y,const float z,			// some point on the line
			   	   	   	  const float dx,const float dy,const float dz) = 0;	// direction vector

	virtual void drawPoint(const float x,const float y,const float z) = 0;
	// point pairs should be drawn by calling drawPoint twice

	// like a point, but with a normal for lighting
	virtual void drawOrientedPoint(const float x,const float y,const float z,
							const float nx,const float ny,const float nz) = 0;

protected:
	static void (*m_gpcVisualizationCallback) (void);
	static void (*m_gpcKeyboardCallback)( unsigned char, int, int );

	static char precisionExponent;
public:
	static float precision;
};

#endif /* BASEVISUALIZATIONBACKEND_H_ */
