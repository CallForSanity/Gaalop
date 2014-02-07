/*
 * OpenGLVisualizationBackend.cpp
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick
 */

#include "OpenGLVisualizationBackend.h"

#define _USE_MATH_DEFINES
#include <math.h>

#ifndef MESH_RESOLUTION
#define MESH_RESOLUTION 32
#endif // MESH_RESOLUTION

#ifndef EPSILON
#define EPSILON 0.000001
#endif // EPSILON

OpenGLVisualizationBackend::OpenGLVisualizationBackend()
{

}

OpenGLVisualizationBackend::~OpenGLVisualizationBackend()
{
	// TODO Auto-generated destructor stub
}

void OpenGLVisualizationBackend::drawPlane( const float x,const float y,const float z, /* some point on the plane */ const float nx,const float ny,const float nz )
{
	// create a basis from the normal vector and use its u and v vector to calculate the plane
	float n[3] = {nx, ny, nz};
	float u[3], v[3], w[3];
	createBasisFromNormal(n, u, v, w);

	glPushMatrix();

	// center of the plane is determined by x, y and z
	glTranslatef(x, y, z);
	// scale of the plane is determined by the length of the normal vector
	float l = vectorLength(n);
	float dl = 0.5f/l;
	glScalef(dl, dl, dl);

	// draw the plane as filled circle
	float angleStep = (float) ((2.0 * M_PI) / (double) MESH_RESOLUTION);
	float theta = 0.0f;
	float p[3];
	float s, c;
	glBegin(GL_TRIANGLE_FAN);
	glNormal3f(nx/l, ny/l, nz/l);
	glVertex3f(0, 0, 0);				// center
	for (unsigned int i = 0; i < MESH_RESOLUTION+1; i++)
	{
		theta += angleStep;
		s = sin(theta);
		c = cos(theta);
		p[0] = u[0]*s + v[0]*c;
		p[1] = u[1]*s + v[1]*c;
		p[2] = u[2]*s + v[2]*c;
		glVertex3f(p[0], p[1], p[2]);
	}
	glEnd();

	glPopMatrix();
}

void OpenGLVisualizationBackend::drawSphere( const float x,const float y,const float z, /* point in the center */ const float diameter )
{
	glPushMatrix();
	glTranslatef(x, y, z);
	//glutWireSphere(diameter/2.0f, MESH_RESOLUTION, MESH_RESOLUTION);
	glutSolidSphere(diameter/2.0f, MESH_RESOLUTION, MESH_RESOLUTION);
	glPopMatrix();
}

void OpenGLVisualizationBackend::drawCircle( const float x,const float y,const float z, /* point in the center */ const float nx,const float ny,const float nz, /* normal vector */ const float diameter, const bool dotted )
{
	// create a basis from the normal vector and use its u and v vector to calculate the circle
	float n[3] = {nx, ny, nz};
	float u[3], v[3], w[3];
	createBasisFromNormal(n, u, v, w);
	// draw the circle
	double angleStep = (2.0 * M_PI) / (double) MESH_RESOLUTION;
	double theta = 0.0;

	glPushMatrix();

	// center of the circle is determined by x, y and z
	glTranslatef(x, y, z);
	// scale by the radius
	float r = diameter/2.0f;
	glScalef(r, r, r);

	float s, c;
	float p[3];

	if (dotted)	// enable line stipple for dotted lines
	{
		glPushAttrib(GL_ENABLE_BIT); 
		glLineStipple(1, 0xf0f0);
		glEnable(GL_LINE_STIPPLE);
	}

	glBegin(GL_LINE_LOOP);
	float l = vectorLength(n);
	glNormal3f(nx/l, ny/l, nz/l);
	for (unsigned int i = 0; i < MESH_RESOLUTION; i++)
	{
		s = (float) sin(theta);
		c = (float) cos(theta);
		p[0] = u[0]*s + v[0]*c;
		p[1] = u[1]*s + v[1]*c;
		p[2] = u[2]*s + v[2]*c;
		glVertex3f(p[0], p[1], p[2]);
		theta += angleStep;
	}
	glEnd();

	if (dotted)
	{
		glPopAttrib();	// disable line stipple by loading previous status
	}

	glPopMatrix();
}

void OpenGLVisualizationBackend::drawLine( const float x,const float y,const float z, /* some point on the line */ const float dx,const float dy,const float dz )
{
	// for an "infinite" line find out where the line intersects the view frustum and draw it between the intersection points
	float ip1[3], ip2[3];
	if(!lineFrustumIntersection(x, y, z, dx, dy, dz, ip1, ip2))
	{
		// no intersection, line is outside the frustum
		return;
	}

	glPushMatrix();
	glBegin(GL_LINES);
	glVertex3f(ip1[0], ip1[1], ip1[2]);
	glVertex3f(ip2[0], ip2[1], ip2[2]);
	glEnd();
	glPopMatrix();
}

void OpenGLVisualizationBackend::drawPoint( const float x,const float y,const float z )
{
	glPushMatrix();
	glBegin(GL_POINTS);
	glVertex3f(x, y, z);
	glEnd();
	glPopMatrix();
}

// like a point, but with a normal for lighting
void OpenGLVisualizationBackend::drawOrientedPoint( const float x,const float y,const float z, const float nx,const float ny,const float nz )
{
	glPushMatrix();
	glBegin(GL_POINTS);
	float n[3] = {nx, ny, nz};
	float l = vectorLength(n);
	glNormal3f(nx/l, ny/l, nz/l);
	glVertex3f(x, y, z);
	glEnd();
	glPopMatrix();
}

void OpenGLVisualizationBackend::createBasisFromNormal( const float n[3], float u[3], float v[3], float w[3] )
{
	// create a normalized normal w
	w[0] = n[0];
	w[1] = n[1];
	w[2] = n[2];
	normalizeVector(w);
	// create two vectors u and v that are perpendicular to the normal and to each other
	// make sure w is not similar to the z unit vector
	if (w[2] < 1-EPSILON)
	{
		// u is the cross product of w and z unit vector: u = w x ez
		u[0] = w[1];	// w[1] * 1 - w[2] * 0;
		u[1] = -w[0];	// w[2] * 0 - w[0] * 1;
		u[2] = 0;		// w[0] * 0 - w[1] * 0;
		normalizeVector(u);
	}
	// if w is too close to the z unit vector use the y unit vector instead
	else
	{
		// u is the cross product of w and y unit vector: u = w x ey
		u[0] = -w[2];	// w[1] * 0 - w[2] * 1;
		u[1] = 0;		// w[2] * 0 - w[0] * 0;
		u[2] = w[0];	// w[0] * 1 - w[1] * 0;
		normalizeVector(u);
	}

	// v is the cross product of w and u: v = w x u
	cross(w, u, v);
	normalizeVector(v);
}

float OpenGLVisualizationBackend::vectorLength( float v[3] )
{
	return sqrt(dot(v,v));
}

void OpenGLVisualizationBackend::normalizeVector( float v[3] )
{
	float l = vectorLength(v);
	v[0] /= l;
	v[1] /= l;
	v[2] /= l;
}

float OpenGLVisualizationBackend::dot( const float u[3], const float v[3] )
{
	return (u[0]*v[0] + u[1]*v[1] + u[2]*v[2]);
}

void OpenGLVisualizationBackend::cross( const float u[3], const float v[3], float w[3] )
{
	w[0] = u[1]*v[2] - u[2]*v[1];
	w[1] = u[2]*v[0] - u[0]*v[2];
	w[2] = u[0]*v[1] - u[1]*v[0];
}

bool OpenGLVisualizationBackend::rayTriangleIntersection( const float a[3], const float b[3], const float c[3], /* corners of the triangle */ const float p[3], const float d[3], /* point on ray and direction of ray */ float &r, float &s, float &t )
{
	float u[3] = {b[0]-a[0], b[1]-a[1], b[2]-a[2]};
	float v[3] = {c[0]-a[0], c[1]-a[1], c[2]-a[2]};
	float w[3] = {p[0]-a[0], p[1]-a[1], p[2]-a[2]};

	//	|r|		   1		|w . (d x v)|
	//	|s| = ----------- *	|u . (d x w)|
	//	|t|   u . (d x v)	|u . (v x w)|

	float dxv[3];
	cross(d, v, dxv);
	float udxv = dot(u, dxv);
	if (abs(udxv) < EPSILON)
	{
		// no solution, ray is parallel to triangle plane
		return false;
	}

	float dxw[3], vxw[3];
	cross(d,w,dxw);
	cross(v,w,vxw);
	float wdxv = dot(w, dxv);
	float udxw = dot(u, dxw);
	float uvxw = dot(u, vxw);
	float invUdxv = 1.0f / udxv;
	r = invUdxv * wdxv;
	s = invUdxv * udxw;
	t = invUdxv * uvxw;

	// check if inside triangle
	if (r+s<=1 && r>=0 && r<=1 && s>=0 && s<=1)
	{
		return true;
	}
	return false;
}

bool OpenGLVisualizationBackend::lineFrustumIntersection( const float x,const float y,const float z, /* some point on the line */ const float dx,const float dy,const float dz, /* direction vector */ float ip1[3], float ip2[3] )
{
	double ang2rad = 0.017453292519943295769236907684886;
	double tang = tan(ang2rad * fov * 0.5);
	// height and width of near and far plane
	float nh = (float) (zNear * tang);
	float nw = (float) (nh * wRatio);
	float fh = (float) (zFar  * tang);
	float fw = (float) (fh * wRatio);

	// camera coordinate system
	float xAxis[3], yAxis[3], zAxis[3];
	zAxis[0] = -camDir[0];
	zAxis[1] = -camDir[1];
	zAxis[2] = -camDir[2];
	normalizeVector(zAxis);
	cross(camUp, zAxis, xAxis);
	normalizeVector(xAxis);
	cross(zAxis, xAxis, yAxis);
	// centers and corners of near and far plane
	float nc[3], ntl[3], ntr[3], nbl[3], nbr[3];
	float fc[3], ftl[3], ftr[3], fbl[3], fbr[3];
	// calculate centers of near and far plane
	nc[0] = camPos[0] - zAxis[0]*(float)zNear;
	nc[1] = camPos[1] - zAxis[1]*(float)zNear;
	nc[2] = camPos[2] - zAxis[2]*(float)zNear;
	fc[0] = camPos[0] - zAxis[0]*(float)zFar;
	fc[1] = camPos[1] - zAxis[1]*(float)zFar;
	fc[2] = camPos[2] - zAxis[2]*(float)zFar;
	// calculate corners of the near plane
	ntl[0] = nc[0] + yAxis[0]*nh - xAxis[0]*nw;
	ntl[1] = nc[1] + yAxis[1]*nh - xAxis[1]*nw;
	ntl[2] = nc[2] + yAxis[2]*nh - xAxis[2]*nw;
	ntr[0] = nc[0] + yAxis[0]*nh + xAxis[0]*nw;
	ntr[1] = nc[1] + yAxis[1]*nh + xAxis[1]*nw;
	ntr[2] = nc[2] + yAxis[2]*nh + xAxis[2]*nw;
	nbl[0] = nc[0] - yAxis[0]*nh - xAxis[0]*nw;
	nbl[1] = nc[1] - yAxis[1]*nh - xAxis[1]*nw;
	nbl[2] = nc[2] - yAxis[2]*nh - xAxis[2]*nw;
	nbr[0] = nc[0] - yAxis[0]*nh + xAxis[0]*nw;
	nbr[1] = nc[1] - yAxis[1]*nh + xAxis[1]*nw;
	nbr[2] = nc[2] - yAxis[2]*nh + xAxis[2]*nw;
	// calculate corners of the far plane
	ftl[0] = fc[0] + yAxis[0]*fh - xAxis[0]*fw;
	ftl[1] = fc[1] + yAxis[1]*fh - xAxis[1]*fw;
	ftl[2] = fc[2] + yAxis[2]*fh - xAxis[2]*fw;
	ftr[0] = fc[0] + yAxis[0]*fh + xAxis[0]*fw;
	ftr[1] = fc[1] + yAxis[1]*fh + xAxis[1]*fw;
	ftr[2] = fc[2] + yAxis[2]*fh + xAxis[2]*fw;
	fbl[0] = fc[0] - yAxis[0]*fh - xAxis[0]*fw;
	fbl[1] = fc[1] - yAxis[1]*fh - xAxis[1]*fw;
	fbl[2] = fc[2] - yAxis[2]*fh - xAxis[2]*fw;
	fbr[0] = fc[0] - yAxis[0]*fh + xAxis[0]*fw;
	fbr[1] = fc[1] - yAxis[1]*fh + xAxis[1]*fw;
	fbr[2] = fc[2] - yAxis[2]*fh + xAxis[2]*fw;

	// triangles of the frustum planes
	float *planes[12][3];
	// triangles of the near plane
	planes[0][0] = ntl;	planes[0][1] = ntr;	planes[0][2] = nbl;
	planes[1][0] = ntr;	planes[1][1] = nbr;	planes[1][2] = nbl;
	// triangles of the far plane
	planes[2][0] = ftl;	planes[2][1] = fbl;	planes[2][2] = ftr;
	planes[3][0] = ftr;	planes[3][1] = fbl;	planes[3][2] = fbr;
	// triangles of the top plane
	planes[4][0] = ntl;	planes[4][1] = ftl;	planes[4][2] = ftr;
	planes[5][0] = ftr;	planes[5][1] = ntr;	planes[5][2] = ntl;
	// triangles of the bottom plane
	planes[6][0] = nbl;	planes[6][1] = fbr;	planes[6][2] = fbl;
	planes[7][0] = fbr;	planes[7][1] = nbl;	planes[7][2] = nbr;
	// triangles of the left plane
	planes[8][0] = ftl;	planes[8][1] = ntl;	planes[8][2] = fbl;
	planes[9][0] = ntl;	planes[9][1] = nbl;	planes[9][2] = fbl;
	// triangles of the right plane
	planes[10][0] = ftr;	planes[10][1] = fbr;	planes[10][2] = ntr;
	planes[11][0] = ntr;	planes[11][1] = fbr;	planes[11][2] = nbr;

	// find the points where the line intersects the frustum
	unsigned int hits = 0;
	float p[3] = {x, y, z};
	float d[3] = {dx, dy, dz};
	float t[2];
	float r, s;
	for (unsigned int i = 0; i < 12; i++)
	{
		if (rayTriangleIntersection(planes[i][0], planes[i][1], planes[i][2], p, d, r, s, t[hits]))
		{
			hits++;
		}
		if (hits >= 2)
		{
			break;
		}
	}
	if (hits < 2)
	{
		return false;
	}

	// intersection-point is p+t*d
	ip1[0] = p[0]+t[0]*d[0];
	ip1[1] = p[1]+t[0]*d[1];
	ip1[2] = p[2]+t[0]*d[2];
	ip2[0] = p[0]+t[1]*d[0];
	ip2[1] = p[1]+t[1]*d[1];
	ip2[2] = p[2]+t[1]*d[2];
	return true;
}

void OpenGLVisualizationBackend::initializeVariables()
{
	// window
	windowWidth = 800;
	windowHeight = 600;
	// intrinsic camera parameters
	zNear = 0.1;
	zFar = 1000.0;
	fov = 50.0;
	wRatio = (GLdouble) windowWidth / (GLdouble) windowHeight;
	// extrinsic camera parameters
	camPos[0] = 0.0f;
	camPos[1] = 0.0f;
	camPos[2] = 10.0f;
	camLookAt[0] = 0.0f;
	camLookAt[1] = 0.0f;
	camLookAt[2] = 0.0f;
	camDir[0] = camLookAt[0] - camPos[0];
	camDir[1] = camLookAt[1] - camPos[1];
	camDir[2] = camLookAt[2] - camPos[2];
	normalizeVector(camDir);
	camUp[0] = 0.0f;
	camUp[1] = 1.0f;
	camUp[2] = 0.0f;
	// mouse
	mouseSensitivy = 1.0f;
	// light
	lightPos[0] = 10.0f;
	lightPos[1] = 10.0f;
	lightPos[2] = 10.0f;
	// misc
	precisionExponent = -6;
	precision = (float) pow(10.0, precisionExponent);
	// rendering
	doRenderWireframe = false;
	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
}

void OpenGLVisualizationBackend::setupOpenGL( int argc, char ** argv )
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH  | GLUT_RGBA | GLUT_MULTISAMPLE);
	glutInitWindowPosition(200,200);
	glutInitWindowSize(windowWidth ,windowHeight);
	windowId = glutCreateWindow("GPC viewer");  
	glutIgnoreKeyRepeat(1);

	glutKeyboardFunc(keyPressed);
	glutMouseFunc(mousePressed);
	glutMotionFunc(mouseMoved);
	glutDisplayFunc(internalRenderingCallback);
	glutReshapeFunc(changeSize);

	// color of cleared screen (white)
	glClearColor(1.0f,1.0f,1.0f,1.0);
	// enable depth buffer
	glEnable(GL_DEPTH_TEST);
	glDepthFunc(GL_LEQUAL);
	// enable transparency
	glEnable(GL_BLEND);
	glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	// width of lines
	glLineWidth(2.0f);
	// size of points
	glPointSize(8.0f);
	// shading model
	glShadeModel(GL_SMOOTH);

	glEnable(GL_LIGHT0);
	// set light position
	GLfloat lp[] = {lightPos[0], lightPos[1], lightPos[2], 0.0f };
	glLightfv(GL_LIGHT0, GL_POSITION, lp);

	GLfloat light_ambient[] = {0.2f, 0.2f, 0.4f, 1.0f};
	GLfloat light_diffuse[] = {1.0f, 1.0f, 0.9f, 1.0f};
	GLfloat light_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};

	glLightfv(GL_LIGHT0, GL_AMBIENT, light_ambient);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, light_diffuse);
	glLightfv(GL_LIGHT0, GL_SPECULAR, light_specular);

	glEnable(GL_COLOR_MATERIAL);

	glEnable( GL_MULTISAMPLE );
	glHint(GL_MULTISAMPLE_FILTER_HINT_NV, GL_NICEST);


	GLenum ret = glewInit();

	changeSize(windowWidth,windowHeight);
}

void OpenGLVisualizationBackend::keyPressed( unsigned char key, int x, int y )
{
	// handle external callback first
	m_gpcKeyboardCallback(key, x, y);

	switch (key)
	{
	case 27:
		exit(0);
		break;
	case 'r':
	case 'R':
		camPos[0] = 0.0f;
		camPos[1] = 0.0f;
		camPos[2] = 10.0f;
		camLookAt[0] = 0.0f;
		camLookAt[1] = 0.0f;
		camLookAt[2] = 0.0f;
		break;
	case '+':
		if (precisionExponent > -128)
		{
			--precisionExponent;
		}
		precision = (float) pow(10.0, precisionExponent);
		break;
	case '-':
		if (precisionExponent < 127)
		{
			++precisionExponent;
		}
		precision = (float) pow(10.0, precisionExponent);
		break;
	case 'w':
	case 'W':
		// toggle wireframe
		doRenderWireframe = !doRenderWireframe;
		glPolygonMode(GL_FRONT_AND_BACK, doRenderWireframe ? GL_LINE : GL_FILL);
		break;
	}
	glutPostRedisplay();
}

void OpenGLVisualizationBackend::mousePressed( int button, int state, int x, int y )
{
	switch(button) {
	case GLUT_LEFT_BUTTON:
		if (state == GLUT_DOWN) {
			mouseButton = 1;
			mouseX = x;
			mouseY = y;
		}
		else mouseButton = 0;
		break;
	case GLUT_RIGHT_BUTTON:
		if (state == GLUT_DOWN) {
			mouseButton = 2;
			mouseX = x;
			mouseY = y;
		}
		else mouseButton = 0;
		break;
	case GLUT_MIDDLE_BUTTON:      
		if (state == GLUT_DOWN) {
			mouseButton = 3;
			mouseX = x;
			mouseY = y;
		}
		else mouseButton = 0;
		break;
	}
	glutPostRedisplay();
}

void OpenGLVisualizationBackend::mouseMoved( int x, int y )
{
	camDir[0] = camLookAt[0]-camPos[0];
	camDir[1] = camLookAt[1]-camPos[1];
	camDir[2] = camLookAt[2]-camPos[2];
	normalizeVector(camDir);
	float camX[3], camY[3];
	cross(camDir, camUp, camX);
	normalizeVector(camX);
	cross(camX, camDir, camY);
	normalizeVector(camY);
	switch(mouseButton)
	{
	// 1 => rotate
	case 1:
		{
			// update angle with relative movement
			float deltaAngleX = (mouseX-x)*mouseSensitivy;
			float deltaAngleY = (mouseY-y)*mouseSensitivy;
			// use OpenGL for matrix calculations
			float tmpMat[16] = {camPos[0],camPos[1],camPos[2],0,0,1,0,0,0,0,1,0,0,0,0,1};
			float curMat[16];
			glPushAttrib(GL_MATRIX_MODE);
			glMatrixMode(GL_MODELVIEW);
			glPushMatrix();
			glLoadIdentity();
			glGetFloatv(GL_MODELVIEW_MATRIX, curMat);
			glRotatef(deltaAngleY, camX[0], camX[1], camX[2]);
			glGetFloatv(GL_MODELVIEW_MATRIX, curMat);
			glRotatef(deltaAngleX, 0, 1, 0);
			glGetFloatv(GL_MODELVIEW_MATRIX, curMat);
			glMultMatrixf(tmpMat);
			glGetFloatv(GL_MODELVIEW_MATRIX, curMat);
			glGetFloatv(GL_MODELVIEW_MATRIX, tmpMat);
			glPopMatrix();
			glPopAttrib();
			camPos[0] = tmpMat[0];
			camPos[1] = tmpMat[1];
			camPos[2] = tmpMat[2];
		}
		break;
	// 2 => zoom
	case 2:
		{
			float step = 0.1f * (float) (y-mouseY) * mouseSensitivy;
			float newCamPos[3], d[3];
			newCamPos[0] = camPos[0] + step * camDir[0];
			newCamPos[1] = camPos[1] + step * camDir[1];
			newCamPos[2] = camPos[2] + step * camDir[2];
			d[0] = camLookAt[0]-newCamPos[0];
			d[1] = camLookAt[1]-newCamPos[1];
			d[2] = camLookAt[2]-newCamPos[2];
			if (vectorLength(d) > zNear+EPSILON && dot(camDir,d) > 0)
			{
				camPos[0] = newCamPos[0];
				camPos[1] = newCamPos[1];
				camPos[2] = newCamPos[2];
			}
		}
		break;
	// 3 => translate 
	case 3:
		{
			// update camPos
			float stepX = -0.1f * (float) (x-mouseX) * mouseSensitivy * 0.5f;
			float stepY =  0.1f * (float) (y-mouseY) * mouseSensitivy * 0.5f;
			camPos[0] += stepX * camX[0] + stepY * camY[0];
			camPos[1] += stepX * camX[1] + stepY * camY[1];
			camPos[2] += stepX * camX[2] + stepY * camY[2];
			camLookAt[0] += stepX * camX[0] + stepY * camY[0];
			camLookAt[1] += stepX * camX[1] + stepY * camY[1];
			camLookAt[2] += stepX * camX[2] + stepY * camY[2];
		}
		break;
	default:
		break;
	}  
	// update mouse for next relative movement
	mouseX = x;
	mouseY = y;
	// redraw if mouse moved
	glutPostRedisplay();
}

void OpenGLVisualizationBackend::changeSize( int w, int h )
{
	// prevent division by 0
	if (h == 0)
	{
		h = 1;
	}
	wRatio = (float) w / (float) h;
	// reset the coordinate system before modifying
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	// set view port to be entire window
	glViewport(0, 0, w, h);
	// set the correct perspective.
	gluPerspective(fov, wRatio, zNear, zFar);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	// camera position and direction
	gluLookAt(	camPos[0],		camPos[1],		camPos[2],		// position
				camLookAt[0],	camLookAt[1],	camLookAt[2],	// look-at
				camUp[0],		camUp[1],		camUp[2]);		// up-vector
	// window size
	windowWidth = w;
	windowHeight = h;
	glutPostRedisplay();
}

void OpenGLVisualizationBackend::init()
{
	initializeVariables();
	char *dummy = "";
	setupOpenGL(1, &dummy);

	glutMainLoop();
}

void OpenGLVisualizationBackend::internalRenderingCallback()
{
	// clear screen
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	// camera rotation
	glLoadIdentity();

	// camera position and direction
	gluLookAt(	camPos[0],		camPos[1],		camPos[2],		// position
				camLookAt[0],	camLookAt[1],	camLookAt[2],	// look-at
				camUp[0],		camUp[1],		camUp[2]);		// up-vector
	camDir[0] = camLookAt[0]-camPos[0];
	camDir[1] = camLookAt[1]-camPos[1];
	camDir[2] = camLookAt[2]-camPos[2];
	normalizeVector(camDir);

	// draw coordinate system
	glBegin(GL_LINES);
	// red X
	glColor3f(1,0,0);
	glVertex3f(0,0,0);
	glVertex3f(5,0,0);
	// green Y
	glColor3f(0,1,0);
	glVertex3f(0,0,0);
	glVertex3f(0,5,0);
	// blue Z
	glColor3f(0,0,1);
	glVertex3f(0,0,0);
	glVertex3f(0,0,5);
	glEnd();

	GLfloat lp[] = {lightPos[0], lightPos[1], lightPos[2], 0.0f };

	glPushAttrib(GL_ENABLE_BIT);

	glEnable(GL_COLOR_MATERIAL);
	glLightfv(GL_LIGHT0, GL_POSITION, lp);
	glEnable(GL_LIGHTING);

	// call gpc rendering callback
	glPushMatrix();
	m_gpcVisualizationCallback();
	glPopMatrix();

	glPopAttrib();

	// swap buffers
	glFlush();
	glutSwapBuffers();
}

void OpenGLVisualizationBackend::setDrawColor( const float r, const float g, const float b, const float a )
{
	glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	glColor4f(r, g, b, a);
	glMaterialf(GL_FRONT, GL_SHININESS, 100);
	glColorMaterial(GL_BACK, GL_AMBIENT_AND_DIFFUSE);
	glColor4f(r-0.2f, g-0.2f, b-0.2f, a);
}

// declare static members again for linker

// window
int OpenGLVisualizationBackend::windowId;
int OpenGLVisualizationBackend::windowWidth;
int OpenGLVisualizationBackend::windowHeight;
// intrinsic camera parameters
GLdouble OpenGLVisualizationBackend::zNear;
GLdouble OpenGLVisualizationBackend::zFar;
GLdouble OpenGLVisualizationBackend::fov;
GLdouble OpenGLVisualizationBackend::wRatio;
// extrinsic camera parameters
GLfloat OpenGLVisualizationBackend::camPos[3];
GLfloat OpenGLVisualizationBackend::camLookAt[3];
GLfloat OpenGLVisualizationBackend::camDir[3];
GLfloat OpenGLVisualizationBackend::camUp[3];
// mouse
int OpenGLVisualizationBackend::mouseX;
int OpenGLVisualizationBackend::mouseY;
int OpenGLVisualizationBackend::mouseButton;
float OpenGLVisualizationBackend::mouseSensitivy;
// light
GLfloat OpenGLVisualizationBackend::lightPos[3];
// rendering
bool OpenGLVisualizationBackend::doRenderWireframe;