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

#include <string>

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

bool drawn = false;
bool list_in_use = false;

//menue
bool autoDraw = true;
int selectedInput = 0;
float curStepWidth = 1;

GLuint list;

void drawPoints() {

	if (!drawn) {
		if (list_in_use) 
			glDeleteLists(list,1);
		
		list = glGenLists(1);
		list_in_use = true;
		glNewList(list, GL_COMPILE_AND_EXECUTE);

		// draw coordinate space
		glColor3f(0,0,0); //black
		glBegin(GL_LINES);
		glVertex3f(1,0,0);glVertex3f(0,0,0);
		glVertex3f(0,0,0);glVertex3f(0,1,0);
		glVertex3f(0,0,0);glVertex3f(0,0,1);
		glEnd();
		glRasterPos3f(1.1f,0,0); glutBitmapString(GLUT_BITMAP_TIMES_ROMAN_10,(const unsigned char*) "x");
		glRasterPos3f(0,1.1f,0); glutBitmapString(GLUT_BITMAP_TIMES_ROMAN_10,(const unsigned char*) "y");
		glRasterPos3f(0,0,1.1f); glutBitmapString(GLUT_BITMAP_TIMES_ROMAN_10,(const unsigned char*) "z");
		
		int objectCount = pointClouds.size();

		for (std::vector<PointCloud>::iterator it = pointClouds.begin(); it != pointClouds.end(); ++it)
			it->draw();

		glEndList();
		drawn = true;

	} else 
		glCallList(list);


}

int main(int argc, char **argv) {
  int inputCount = getInputCount();
  inputs = new float[inputCount];
  for (int i=0;i<inputCount;++i)
	  inputs[i] = 1;


  glutInit(&argc, argv);
  glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
  glutInitWindowPosition(200,200);
  glutInitWindowSize(wSizeH,wSizeW);
  glutCreateWindow("CVisualizer of Gaalop");
  
  glutIgnoreKeyRepeat(1);
  glutKeyboardFunc(keyPressed);
  glutSpecialFunc(specialPressed);
  glutMouseFunc(mousePressed);
  glutMotionFunc(mouseMoved);
  
  glutDisplayFunc(renderScene);
  glutReshapeFunc(changeSize);

  std::cout << "CVisualizer from Gaalop\n";
  std::cout << "Usage:\nesc: exit program\nC/c: adjust cubeEdgeLength\nD/d: adjust density\nS/s: adjust step size\na: toggle autodraw\nr: repaint\n\n";
  std::cout << "Arrows:\nup/down: switch current input\nleft/right: increase/decrease current input value with step size\n";
  std::cout << "mouse left: rotate\nmouse middle: move (pan)\nmouse right: zoom\n" << std::endl;

  initialize();

  glutMainLoop();

  delete[] inputs;
  inputs = 0;
  return 0;
}

void calcAndDraw() {
	calculatePoints();
	drawn = false;
	drawPoints();
	glutPostRedisplay();
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
  drawn = false;
  list_in_use = false;

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
bool textMode = false;

void setToTextMode() {
    // backup current modelview matrix
    glPushMatrix();
    // reset modelview matrix
    glLoadIdentity();

    // set to 2D orthogonal projection =======
    // switch to projection matrix
    glMatrixMode(GL_PROJECTION);
    // backup projection matrix
    glPushMatrix();
    // reset projection matrix
    glLoadIdentity();
    // set to orthogonal projection
	glOrtho(0, wSizeW, 0, wSizeH, -1.0, 1.0);

	textMode = true;
}

void restoreFromTextMode() {
	textMode = false;
    // restore previous projection matrix
    glPopMatrix();

    // restore previous modelview matrix
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
}



void printText(const char* text, float x, float y) {
	if (!textMode) {
		setToTextMode();
		glRasterPos2f(x, y);
		glutBitmapString(GLUT_BITMAP_HELVETICA_12, (const unsigned char*) text);
		restoreFromTextMode();
	} else {
		glRasterPos2f(x, y);
		glutBitmapString(GLUT_BITMAP_HELVETICA_12, (const unsigned char*) text);
	}
}

inline void printRow(const char* text, int y) {
	printText(text,0,wSizeH-(y+1)*17);
}

inline void printPropertyRow(const char* name, float value, int y) {
	 std::stringstream s;
	 s << name << " = " << value;
	 printRow(s.str().c_str(),y);
}

void printMenu() {
	int inputCount = getInputCount();
	setToTextMode();
	glColor3f(0,0,0);
	printRow("Properties:",0);
	printPropertyRow("cubeEdgeLength",cubeEdgeLength,1);
	printPropertyRow("density",density,2);
	printPropertyRow("step size",curStepWidth,3);
	if (autoDraw)
		printRow("autodraw",4);
	else
		printRow("No autodraw",4);

	printRow("Inputs:",6);

	for (int i=0;i<inputCount;++i) {
		if (selectedInput == i) 
			glColor3f(1,0,0);
		else
			glColor3f(0,0,0);

		std::string name;
		getInputName(i,name);
		printPropertyRow(name.c_str(),inputs[i],i+7);
	}
	restoreFromTextMode();
}

void renderScene(void) {
  // clear the screen
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 

  printMenu();

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
	case 'C':
		cubeEdgeLength += curStepWidth;
		calcAndDraw();
		break;
	case 'c':
		cubeEdgeLength -= curStepWidth;
		calcAndDraw();
		break;
	case 'D':
		density *= 2;
		calcAndDraw();
		break;
	case 'd':
		density /= 2;
		calcAndDraw();
		break;
	case 'S':
		curStepWidth *= 10;
		break;
	case 's':
		curStepWidth /= 10;
		break;
	case 'a':
		autoDraw = !autoDraw;
		break;
	case 'r':
		calcAndDraw();
		return;
  }
  glutPostRedisplay();
}

void specialPressed(int key, int x, int y) {
	switch (key) {
    // esc => exit
	case GLUT_KEY_LEFT:
		if (selectedInput < getInputCount())
			inputs[selectedInput] -= curStepWidth;
		if (autoDraw) calcAndDraw(); else glutPostRedisplay();
		break;
	case GLUT_KEY_RIGHT:
		if (selectedInput < getInputCount())
			inputs[selectedInput] += curStepWidth;
		if (autoDraw) calcAndDraw(); else glutPostRedisplay();
		break;
	case GLUT_KEY_UP:
		selectedInput--;
		if (selectedInput < 0) selectedInput = 0;
		glutPostRedisplay();
		break;
	case GLUT_KEY_DOWN:
		selectedInput++;
		if (selectedInput >= getInputCount()) selectedInput = getInputCount()-1;
		glutPostRedisplay();
		break;
	}
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
