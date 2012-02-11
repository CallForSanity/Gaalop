package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.PointCloud;
import de.gaalop.visualizer.engines.RenderingEngine;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * Implements a rendering engine based on LwJgl
 * @author Christian Steinmetz
 */
public abstract class LwJglRenderingEngine extends RenderingEngine {

    private double near = 0.1, far = 30;
    // Camera information
    private Vec3f camPos = new Vec3f(0.0f, 2.0f, -10.0f);       // camera position
    private Vec3f camDir = new Vec3f(0.0f, 0.0f, 1.0f);         // camera lookat (always Z)
    private Vec3f camUp = new Vec3f(0.0f, 1.0f, 0.0f);          // camera up direction (always Y)
    private float camAngleX = 0.0f, camAngleY = 0.0f;   // camera angles
    // Mouse information
    private int mouseX, mouseY, mouseButton;
    private float mouseSensitivy = 1.0f;
    private boolean[] buttonDown = new boolean[]{false, false, false};
    private static final int STATE_DOWN = 1;
    private static final int STATE_UP = 2;
    
    @Override
    public void render(HashMap<String, PointCloud> clouds) {

        int width = 800;
        int height = 600;

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setFullscreen(false);

            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        changeSize(width, height);
        GL11.glDisable(GL11.GL_LIGHTING);


        // init OpenGL
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective((float) 65.0, (float) width / (float) height, (float) 0.1, 100);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the screen
            GL11.glLoadIdentity(); // apply camPos before rotation

            GL11.glTranslatef(0.0f, 0.0f, -5.0f);
            // draw
            GLU.gluLookAt(camPos.x, camPos.y, camPos.z, // Position
                    camPos.x + camDir.x, camPos.y + camDir.y, camPos.z + camDir.z, // Lookat
                    camUp.x, camUp.y, camUp.z);               // Up-direction
            // apply rotation
            GL11.glRotatef(camAngleX, 0, 1, 0); // window x axis rotates around up vector
            GL11.glRotatef(camAngleY, 1, 0, 0); // window y axis rotates around x

            //Render the scene
            draw(clouds);

            pollInput();
            Display.update();
            Display.sync(1); // cap fps to 60fps
        }

        Display.destroy();
    }

    void mouseMoved(int x, int y) {
        switch (mouseButton) {
            // 1 => rotate
            case 1:
                // update angle with relative movement
                camAngleX = fmod(camAngleX + (x - mouseX) * mouseSensitivy, 360.0f);


                camAngleY -= (y - mouseY) * mouseSensitivy;
                // limit y angle by 85 degree
                if (camAngleY > 85) {
                    camAngleY = 85;
                }
                if (camAngleY < -85) {
                    camAngleY = -85;
                }
                break;
            // 2 => zoom
            case 2:
                camPos.z -= 0.1f * (y - mouseY) * mouseSensitivy;
                break;
            // 3 => translate
            case 3:
                // update camPos
                camPos.x += 0.1f * (x - mouseX) * mouseSensitivy;
                camPos.y -= 0.1f * (y - mouseY) * mouseSensitivy;
                break;
            default:
                break;
        }
        // update mouse for next relative movement
        mouseX = x;
        mouseY = y;
    }

    void mouseAction(int button, int state, int x, int y) {
        switch (button) {
            case 0:
                if (state == STATE_DOWN) {
                    mouseButton = 1;
                    mouseX = x;
                    mouseY = y;
                } else {
                    mouseButton = 0;
                }
                break;
            case 1:
                if (state == STATE_DOWN) {
                    mouseButton = 3;
                    mouseX = x;
                    mouseY = y;
                } else {
                    mouseButton = 0;
                }
                break;
            case 2:
                if (state == STATE_DOWN) {
                    mouseButton = 2;
                    mouseX = x;
                    mouseY = y;
                } else {
                    mouseButton = 0;
                }
                break;
        }
    }

    private void pollInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
            camPos.set(0.0f, 2.0f, -10.0f);
            camAngleX = 180.0f;
            camAngleY = 0.0f;
        }

        int x = Mouse.getX();
        int y = Mouse.getY();

        for (int button = 0; button <= 2; button++) {
            if (Mouse.isButtonDown(button)) {
                if (!buttonDown[button]) {
                    mouseAction(button, STATE_DOWN, x, y);
                } else {
                    mouseMoved(x, y);
                }
                buttonDown[button] = true;
            } else {
                if (buttonDown[button]) {
                    mouseAction(button, STATE_UP, x, y);
                }
                buttonDown[button] = false;
            }
        }
    }

    private void changeSize(float w, float h) {
        // Prevent a division by zero, when window is too short
        if (h == 0) {
            h = 1;
        }
        float wRatio = 1.0f * w / h;
        // Reset the coordinate system before modifying
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        // Set the viewport to be the entire window
        GL11.glViewport(0, 0, (int) w, (int) h);
        // Set the correct perspective.
        GLU.gluPerspective(45.0f, wRatio, (float) near, (float) far);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GLU.gluLookAt(camPos.x, camPos.y, camPos.z, // Position
                camPos.x + camDir.x, camPos.y + camDir.y, camPos.z + camDir.z, // Lookat
                camUp.x, camUp.y, camUp.z);               // Up-direction}
    }

    private float fmod(float value, float modulo) {
        while (value < 0) {
            value += modulo;
        }
        while (value > modulo) {
            value -= modulo;
        }
        return value;
    }

    /**
     * Draws the concrete scene
     * @param clouds The point clouds
     */
    public abstract void draw(HashMap<String, PointCloud> clouds);

}
