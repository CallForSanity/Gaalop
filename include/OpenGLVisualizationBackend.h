/*
 * OpenGLVisualizationBackend.h
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick
 */

#ifndef OPENGLVISUALIZATIONBACKEND_H_
#define OPENGLVISUALIZATIONBACKEND_H_

#include <GL/glew.h>
#include <GL/freeglut.h>

#include "BaseVisualizationBackend.h"

class OpenGLVisualizationBackend: public BaseVisualizationBackend {

public:
	OpenGLVisualizationBackend();
	virtual ~OpenGLVisualizationBackend();

	void init();
	void setDrawColor(const float r, const float g, const float b, const float a);

	void drawPlane(const float x,const float y,const float z,		// some point on the plane
				   const float nx,const float ny,const float nz);	// normal vector
	void drawSphere(const float x,const float y,const float z,		// point in the center
					const float diameter);

	void drawCircle(const float x,const float y,const float z,		// point in the center
	   	   	   	    const float nx,const float ny,const float nz,	// normal vector
					const float diameter, const bool dotted = false);
	void drawLine(const float x,const float y,const float z,		// some point on the line
	   	   	   	  const float dx,const float dy,const float dz);	// direction vector

	void drawPoint(const float x,const float y,const float z);		// point pairs should be drawn by calling drawPoint twice

	void drawOrientedPoint(const float x,const float y,const float z, // like a point, but with a normal for lighting
		const float nx,const float ny,const float nz);

protected:
	static void createBasisFromNormal(const float n[3],				// normal vector
							   float u[3], float v[3], float w[3]);	// a basis containing the normalized normal in w
	static inline float vectorLength(float v[3]);
	static inline void normalizeVector(float v[3]);
	static inline float dot(const float u[3], const float v[3]);
	static inline void cross(const float u[3], const float v[3], float w[3]);
	static bool rayTriangleIntersection(const float a[3], const float b[3], const float c[3], // corners of the triangle
										const float p[3], const float d[3],	// point on ray and direction of ray
										float &r, float &s, float &t);		// barycentric coordinates r and s of the triangle (edges a-b and a-c) and ray parameter t
	static bool lineFrustumIntersection(const float x,const float y,const float z,		// some point on the line
										const float dx,const float dy,const float dz,	// direction vector
										float ip1[3], float ip2[3]);					// intersection points

	static void initializeVariables();
	static void setupOpenGL( int argc, char ** argv );

	// callback functions
	static void keyPressed(unsigned char key, int x, int y);
	static void mousePressed(int button, int state, int x, int y);
	static void mouseMoved(int x, int y);
	static void changeSize(int w, int h);

	static void internalRenderingCallback();

protected:
	// window
	static int windowId;
	static int windowWidth;
	static int windowHeight;
	// intrinsic camera parameters
	static GLdouble zNear;
	static GLdouble zFar;
	static GLdouble fov;
	static GLdouble wRatio;
	// extrinsic camera parameters
	static GLfloat camPos[3];
	static GLfloat camLookAt[3];
	static GLfloat camDir[3];
	static GLfloat camUp[3];
	// mouse
	static int mouseX;
	static int mouseY;
	static int mouseButton;
	static float mouseSensitivy;
	// light
	static GLfloat lightPos[3];
	// rendering
	static bool doRenderWireframe;
};

#endif /* OPENGLVISUALIZATIONBACKEND_H_ */
