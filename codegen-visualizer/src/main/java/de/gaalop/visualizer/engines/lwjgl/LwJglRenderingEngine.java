package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.PointCloud;
import de.gaalop.visualizer.engines.RenderingEngine;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

/**
 *
 * @author Christian Steinmetz
 */
public class LwJglRenderingEngine extends RenderingEngine {

int angleX=0, angleY=0; // rotation angle
int startX=0, startY=0; // mouse position (window coordinates)

    @Override
    public void render(HashMap<String, PointCloud> clouds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void startEngine() {
        int width = 800;
        int height = 600;

        try {
                Display.setDisplayMode(new DisplayMode(width, height));
                Display.create();
        } catch (LWJGLException e) {
                e.printStackTrace();
                System.exit(0);
        }

         // init OpenGL
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective((float) 65.0, (float)width / (float)height, (float) 0.1, 100);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);


        while (!Display.isCloseRequested()) {

		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                GL11.glLoadIdentity();

                GL11.glTranslatef(0,0,-5.0f);         // translate 5 in z direction
                GL11.glRotatef((float) angleX,(float) 0.0,(float) 1.0,(float) 0.0);  // rotate around y axis (facing up) (use x-value of mouse movement)
                GL11.glRotatef((float) angleY,(float) 1.0,(float) 0.0,(float) 0.0);  // rotate around x axis (use y-value of mouse movement)


		// R,G,B,A Set The Color To Blue One Time Only
		GL11.glColor3f(0.5f, 0.5f, 1.0f);

		// draw
                GL11.glPushMatrix();
                Sphere s = new Sphere();
                s.draw(0.1f, 10, 10);
                
                GL11.glTranslatef(0.1f,0,0);

                s = new Sphere();
                s.draw(0.1f, 10, 10);

                GL11.glPopMatrix();
                Display.update();
                Display.sync(60); // cap fps to 60fps
        }

        Display.destroy();
    }


	public static void main(String[] argv) {
		LwJglRenderingEngine engine = new LwJglRenderingEngine();
		engine.startEngine();
	}

}
