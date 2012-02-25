// ========================================================================= //
// Author: Matthias Bein                                                     //
// mailto:matthias.bein@gris.informatik.tu-darmstadt.de                      //
// GRIS - Graphisch Interaktive Systeme                                      //
// Technische Universität Darmstadt                                          //
// Fraunhoferstrasse 5                                                       //
// D-64283 Darmstadt, Germany                                                //
//                                                                           //
// Creation Date: 26.051.2010                                                 //
// ========================================================================= //

#include "main.h"
#include <fstream>
#include <iostream>

#include <vector>
#include "PointCloud.h"

// Window size information
float wRatio;
int wSizeH=600, wSizeW=400;
GLdouble tnear = 0.1, tfar = 30;
// Camera information
Vec3f camPos(0.0f, 2.0f, -10.0f);       // camera position
Vec3f camDir(0.0f, 0.0f, 1.0f);         // camera lookat (always Z)
Vec3f camUp(0.0f, 1.0f, 0.0f);          // camera up direction (always Y)
float camAngleX=0.0f, camAngleY=0.0f;   // camera angles
// Light information
Vec3f lightPos(10.0f, 30.0f, -10.0f);
// Mouse information
int mouseX, mouseY, mouseButton;
float mouseSensitivy = 1.0f;
// Button information
bool b_r = false;

std::vector<PointCloud> pointClouds;

int objectCount;

bool drawn = false;
bool list_in_use = false;

void calculatePoints() {
	objectCount = getOutputCount();

	for (int i=0;i<objectCount;++i) {
		pointClouds.push_back(PointCloud());
		PointCloud& cloud = pointClouds[i];
		findZeroLocations(i, cloud.points);
		getOutputAttributes(i, cloud.name, cloud.colR, cloud.colG, cloud.colB, cloud.colA);
	}
	
	drawn = false;
	list_in_use = false;
}

GLuint list;

void drawPoints() {

	if (!drawn) {
		if (list_in_use) 
			glDeleteLists(list,1);
		
		list = glGenLists(1);
		list_in_use = true;
		glNewList(list, GL_COMPILE_AND_EXECUTE);
		
		for (int i=0;i<objectCount;++i) 
			pointClouds[i].draw();


		glEndList();
		drawn = true;

	} else 
		glCallList(list);


}

int main(int argc, char **argv) {
  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
  glutInitWindowPosition(200,200);
  glutInitWindowSize(wSizeH,wSizeW);
  glutCreateWindow("Volume Visualization");
  
  glutIgnoreKeyRepeat(1);
  glutKeyboardFunc(keyPressed);
  glutMouseFunc(mousePressed);
  glutMotionFunc(mouseMoved);
  
  glutDisplayFunc(renderScene);
  glutReshapeFunc(changeSize);
  
  initialize();

  std::cout << "(Simple) Volume Data Visualization\n";
  std::cout << "Usage:\nesc: exit program\n  -: decrease threshold (isovalue)\n  +: increase threshold (isovalue) \n\n";
  std::cout << "mouse left: rotate\nmouse middle: move (pan)\nmouse right: zoom" << std::endl;


  glutMainLoop();
  
  return 0;
}

void initialize() {

  // uncomment if you want to work with shaders ... 
  //if (!GLEW_ARB_vertex_shader || !GLEW_ARB_fragment_shader) 
  //{
  //  std::cout << "Shaders are not supported or not ready!" << std::endl;
  //  exit(1);
  //}
  glClearColor(1.0,1.0,1.0,0.0);
  // enable depth buffer
  glEnable(GL_DEPTH_TEST);
  // shading model
  glShadeModel(GL_SMOOTH);  // GL_SMOOTH, GL_FLAT
  // size window
  changeSize(wSizeH,wSizeW);
  // lighting is used in this exercise
  glDisable(GL_LIGHTING);

  // load a volume data set
  calculatePoints();

}

void changeSize(int w, int h) {
  // Prevent a division by zero, when window is too short
  if(h == 0) h = 1;
  float wRatio = 1.0f* w / h;
  // Reset the coordinate system before modifying
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();	
  // Set the viewport to be the entire window
  glViewport(0, 0, w, h);
  // Set the correct perspective.
  gluPerspective(45,wRatio,tnear,tfar);
  glMatrixMode(GL_MODELVIEW);
  glLoadIdentity();
  gluLookAt(camPos.x,             camPos.y,             camPos.z,               // Position
            camPos.x + camDir.x,  camPos.y + camDir.y,  camPos.z + camDir.z,    // Lookat
	    camUp.x,              camUp.y,              camUp.z);               // Up-direction
}

// Rendering

void renderScene(void) {
  // clear the screen
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
  // apply camPos before rotation
  glLoadIdentity();  
  gluLookAt(camPos.x,             camPos.y,             camPos.z,               // Position
            camPos.x + camDir.x,  camPos.y + camDir.y,  camPos.z + camDir.z,    // Lookat
	    camUp.x,              camUp.y,              camUp.z);               // Up-direction
  // apply rotation
  glRotatef(camAngleX,0,1,0); // window x axis rotates around up vector
  glRotatef(camAngleY,1,0,0); // window y axis rotates around x
  
  glColor3f(1.0,0.0,0.0);

  drawPoints();
  // swap Buffers
  glFlush();
  glutSwapBuffers();
}



// Callbacks

void keyPressed(unsigned char key, int x, int y) {

  float increment = 0.05f;
  switch (key) {
    // esc => exit
    case 27:
      exit(0);
      break;
    // v => reset view
    case 'v':
    case 'V':
      camPos.set(0.0f, 2.0f, -10.0f);
      camAngleX = 180.0f;
      camAngleY = 0.0f;
      break;
  }
  glutPostRedisplay();
}

void mousePressed(int button, int state, int x, int y) {
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
}

void mouseMoved(int x, int y) { 
  switch(mouseButton) {
  // 1 => rotate
  case 1:
    // update angle with relative movement
    camAngleX = fmod(camAngleX + (x-mouseX)*mouseSensitivy,360.0f);
    camAngleY -= (y-mouseY)*mouseSensitivy;
    // limit y angle by 85 degree
    if (camAngleY > 85) camAngleY = 85;
    if (camAngleY < -85) camAngleY = -85;
    break;
  // 2 => zoom
  case 2: 
    camPos -= Vec3f(0.0f,0.0f,0.1f)*(y-mouseY)*mouseSensitivy;
    break;
  // 3 => translate 
  case 3:
    // update camPos
    camPos += Vec3f(0.1f,0.0f,0.0f)*(x-mouseX)*mouseSensitivy;
    camPos += Vec3f(0.0f,0.1f,0.0f)*(y-mouseY)*mouseSensitivy;
    break;
  default: break;
  }  
  // update mouse for next relative movement
  mouseX = x;
  mouseY = y;
  // redraw if mouse moved, since there is no idleFunc defined
  glutPostRedisplay();
}
