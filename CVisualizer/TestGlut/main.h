// ========================================================================= //
// Author: Matthias Bein                                                     //
// mailto:matthias.bein@gris.informatik.tu-darmstadt.de                      //
// GRIS - Graphisch Interaktive Systeme                                      //
// Technische Universität Darmstadt                                          //
// Fraunhoferstrasse 5                                                       //
// D-64283 Darmstadt, Germany                                                //
//                                                                           //
// Creation Date: 26.05.2010                                                 //
// ========================================================================= //

#include <stdlib.h>
#include <stdio.h>
#include <vector>
#include <openglut.h>
#include <fstream>
#include <iostream>


#include "PointCloud.h"
#include "CalculatePoints.h"
#include "Vec3.h"

// Basics

int main(int argc, char **argv);

void initialize();

void changeSize(int w, int h);

// Rendering

void renderScene(void);

// Callbacks

void keyPressed(unsigned char key, int x, int y);

void specialPressed(int key, int x, int y);

void mousePressed(int button, int state, int x, int y);

void mouseMoved(int x, int y);

