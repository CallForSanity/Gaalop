/*
 * GPCUtils.cpp
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick
 */

#include <cstdlib>
#include <cmath>
#include <math.h>
#include "GPCUtils.h"
#include "MultiVectorEntries.h"

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
	return m_visualizationBackend.get();
}

void GPCUtils::setVisualizationBackend( std::auto_ptr<BaseVisualizationBackend> visualizationBackend )
{
	m_visualizationBackend = visualizationBackend;
}

void GPCUtils::drawMultivector( const float mvArray[32] )
{
	float precision = BaseVisualizationBackend::precision;
	// depending on the components of the multivector draw the corresponding geometric shape
	
	// check which components are set
	unsigned int nonZeroComponents = 0;
	for ( unsigned int i = 0; i < 32; i++ )
	{
		nonZeroComponents |= (std::abs(mvArray[i]) >= precision) ? (1 << i) : 0;
	}
	
	// points, spheres and planes are vectors, circles and lines are bivectors, point pairs are trivectors
	bool isVector		= !(nonZeroComponents & (~0x0000003e));	// only components 1 to 5 are set
	bool isBivector		= !(nonZeroComponents & (~0x0000ffc0));	// only components 6 to 15 are set
	bool isTrivector	= !(nonZeroComponents & (~0x03ff0000));	// only components 16 to 25 are set
	
	// if multivector is a point, sphere or plane
	if (isVector)
	{
		// For a (normalized) sphere the squared multivector is equal to the squared radius, for a plane it is infinity.
		// The factor in front of e0 needs to be 1 after normalization, divide by it.
		float facE0 = mvArray[E0];
		// If the factor is 0, multivector seems to be a plane.
		if (std::abs(facE0) < precision)
		{
			// The normal components are stored in e1, e2, e3
			float nx = mvArray[E1];
			float ny = mvArray[E2];
			float nz = mvArray[E3];
			float l = nx*nx + ny*ny + nz*nz;
			nx /= l;
			ny /= l;
			nz /= l;
			// The distance to the origin is stored in e_inf
			float d = mvArray[EINF];
			float px = nx * d;
			float py = ny * d;
			float pz = nz * d;
			// draw plane
			m_visualizationBackend->drawPlane(px, py, pz, nx, ny, nz);
		}
		else
		{
			float x = mvArray[E1] / facE0;
			float y = mvArray[E2] / facE0;
			float z = mvArray[E3] / facE0;
			float facEinf = mvArray[EINF] / facE0;
			float midpointDist = x*x + y*y + z*z;
			float r = std::sqrt(midpointDist - 2.0f * facEinf);
			// If the radius is (close to) 0, it's a point
			if ( r < precision )
			{
				m_visualizationBackend->drawPoint(x, y, z);
			}
			else
			{
				// draw a sphere
				m_visualizationBackend->drawSphere(x, y, z, 2 * r);
			}
		}
	}
	else if (isBivector)
	{
		float facE1E0 = mvArray[E1E0];
		float facE2E0 = mvArray[E2E0];
		float facE3E0 = mvArray[E3E0];
		float facEINFE0 = mvArray[EINFE0];
		// check if there are any E0 components
		if (std::abs(facE1E0) < precision && std::abs(facE2E0) < precision && std::abs(facE3E0) < precision && std::abs(facEINFE0) < precision)
		{
			// draw line

			// get the moment
			float mx = mvArray[E1EINF];
			float my = mvArray[E2EINF];
			float mz = mvArray[E3EINF];

			// get the direction
			float ux =   mvArray[E2E3];
			float uy = - mvArray[E1E3];
			float uz =   mvArray[E1E2];

			// get a point on the line

			// the point on the line closest to the origin is given by (m^u:u.u)
			// where '^' is the cross product, '.' is the dot product and (x,w) is the homogeneous point of x
			// see http://tog.acm.org/resources/RTNews/html/rtnv11n1.html#art3

			// calculate 1.0f/u.u
			float uu_inv = 1.0f / (ux*ux + uy*uy + uz*uz);
			// calculate m^u / u.u
			float x = uu_inv * (uy * mz - uz * my);
			float y = uu_inv * (uz * mx - ux * mz);
			float z = uu_inv * (ux * my - uy * mx);

			m_visualizationBackend->drawLine(x, y, z, ux, uy, uz);
		}
		else
		{
			// draw circle

			// get the normal of the circle
			float nx = mvArray[E1E0];
			float ny = mvArray[E2E0];
			float nz = mvArray[E3E0];
			float l2_inv = 1.0f/(nx*nx + ny*ny + nz*nz);

			const float* C = mvArray;
			// get the center of the circle (division by l2 for normalization)
			float cx = l2_inv * (float) ((-((-(-0.5 * C[12])) * C[6])) + (-((-(-0.5 * C[14])) * C[7])) + (-(-0.5 * C[15])) * C[9] + -0.5 * C[6] * C[12] + -0.5 * C[7] * C[14] + (-(-0.5 * C[9])) * C[15]  ); // e1
			float cy = l2_inv * (float) ((-(-0.5 * C[9])) * C[6] + (-((-(-0.5 * C[14])) * C[10])) + (-(-0.5 * C[15])) * C[12] + (-(-0.5 * C[6] * C[9])) + -0.5 * C[10] * C[14] + (-(-0.5 * C[12])) * C[15]); // e2
			float cz = l2_inv * (float) ((-(-0.5 * C[9])) * C[7] + (-(-0.5 * C[12])) * C[10] + (-(-0.5 * C[15])) * C[14] + (-(-0.5 * C[7] * C[9])) + (-(-0.5 * C[10] * C[12])) + (-(-0.5 * C[14])) * C[15]); // e3

			// get the radius
			float r2 = ((-((-C[6]) * C[6])) + (-((-C[7]) * C[7])) + (-C[8]) * C[9] + (-C[9]) * C[8] + (-((-C[10]) * C[10])) + (-C[11]) * C[12] + (-C[12]) * C[11] + (-C[13]) * C[14] + (-C[14]) * C[13] + (-C[15]) * C[15]); // 1.0
			float r = std::sqrtf(std::abs(r2*l2_inv));

			// if the radius is (close to) 0 it is an oriented point
			if ( r < precision)
			{
				m_visualizationBackend->drawOrientedPoint(cx, cy, cz, nx, ny, nz);
			}
			else
			{
				bool dotted = (r2 < 0);	// circle with pure imaginary radius will be drawn dotted
				m_visualizationBackend->drawCircle(cx, cy, cz, nx, ny, nz, 2.0f*r, dotted);
			}
		}
	}
	else if (isTrivector)
	{
		// draw a point pair

		const float* PP = mvArray;
		// Pp will contain a bivector, so the components 16 and above are not needed
		float Pp[16] = {0};
		float offset;
		// the points will be vectors, so the components 6 and above are not needed
		float p1[6] = {0};
		float p2[6] = {0};
		
		Pp[6] = (-PP[25]); // e1 ^ e2
		Pp[7] = PP[24]; // e1 ^ e3
		Pp[8] = (-PP[22]); // e1 ^ einf
		Pp[9] = PP[23]; // e1 ^ e0
		Pp[10] = (-PP[21]); // e2 ^ e3
		Pp[11] = PP[19]; // e2 ^ einf
		Pp[12] = (-PP[20]); // e2 ^ e0
		Pp[13] = (-PP[17]); // e3 ^ einf
		Pp[14] = PP[18]; // e3 ^ e0
		Pp[15] = PP[16]; // einf ^ e0
		offset = sqrtf((-(Pp[6] * Pp[6])) + (-(Pp[7] * Pp[7])) + Pp[8] * Pp[9] + Pp[9] * Pp[8] + (-(Pp[10] * Pp[10])) + Pp[11] * Pp[12] + Pp[12] * Pp[11] + Pp[13] * Pp[14] + Pp[14] * Pp[13] + Pp[15] * Pp[15]); // 1.0
		p1[1] = offset * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + Pp[6] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + Pp[7] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[9] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e1
		p1[2] = offset * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[6] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + Pp[10] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[12] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e2
		p1[3] = offset * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[7] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[10] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[14] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e3
		p1[4] = offset * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[8] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[11] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[13] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[15] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // einf
		p1[5] = (-(Pp[9] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[12] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[14] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e0
		p2[1] = (-offset) * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + Pp[6] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + Pp[7] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[9] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e1
		p2[2] = (-offset) * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[6] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + Pp[10] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[12] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e2
		p2[3] = (-offset) * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[7] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[10] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[14] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e3
		p2[4] = (-offset) * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]) + (-(Pp[8] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[11] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[13] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[15] * Pp[15] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // einf
		p2[5] = (-(Pp[9] * Pp[9] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[12] * Pp[12] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))) + (-(Pp[14] * Pp[14] / (Pp[9] * Pp[9] + Pp[12] * Pp[12] + Pp[14] * Pp[14]))); // e0

		float p1e0_inv = 1.0f / p1[E0];
		float p1x = p1[E1] * p1e0_inv;
		float p1y = p1[E2] * p1e0_inv;
		float p1z = p1[E3] * p1e0_inv;

		float p2e0_inv = 1.0f / p2[E0];
		float p2x = p2[E1] * p2e0_inv;
		float p2y = p2[E2] * p2e0_inv;
		float p2z = p2[E3] * p2e0_inv;

		m_visualizationBackend->drawPoint(p1x, p1y, p1z);
		m_visualizationBackend->drawPoint(p2x, p2y, p2z);
	}
}

void GPCUtils::setVisualizationCallback( void (*renderingCallback) (void) )
{
	m_visualizationBackend->setGpcVisualizationCallback(renderingCallback);
}

void GPCUtils::setKeyboardCallback( void (*keyboardCallback)( unsigned char, int, int ) )
{
	m_visualizationBackend->setGpcKeyboardCallback(keyboardCallback);
}

void GPCUtils::setDrawColor( const float r, const float g, const float b )
{
	m_visualizationBackend->setDrawColor(r,g,b,1.0f);
}

void GPCUtils::setDrawColor( const float r, const float g, const float b, const float a )
{
	m_visualizationBackend->setDrawColor(r,g,b,a);
}




std::auto_ptr<BaseVisualizationBackend> GPCUtils::m_visualizationBackend;

