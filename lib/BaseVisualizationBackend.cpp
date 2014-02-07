/*
 * BaseVisualizationBackend.cpp
 *
 *  Created on: Jun 4, 2013
 *      Author: patrick, marcel
 */

#include "BaseVisualizationBackend.h"

BaseVisualizationBackend::BaseVisualizationBackend()
{
	// TODO Auto-generated constructor stub
}

BaseVisualizationBackend::~BaseVisualizationBackend()
{
	// TODO Auto-generated destructor stub
}

void BaseVisualizationBackend::setGpcVisualizationCallback( void (*gpcVisualizationCallback) (void) )
{
	m_gpcVisualizationCallback = gpcVisualizationCallback;
}

void BaseVisualizationBackend::setGpcKeyboardCallback( void (*gpcKeyboardCallback)( unsigned char, int, int ) )
{
	m_gpcKeyboardCallback = gpcKeyboardCallback;
}


void (*BaseVisualizationBackend::m_gpcVisualizationCallback) (void);
void (*BaseVisualizationBackend::m_gpcKeyboardCallback) (unsigned char, int, int);
char BaseVisualizationBackend::precisionExponent;
float BaseVisualizationBackend::precision;