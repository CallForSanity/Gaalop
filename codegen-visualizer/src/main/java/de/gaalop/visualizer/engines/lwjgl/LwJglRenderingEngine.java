package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.PointCloud;
import de.gaalop.visualizer.Rendering;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
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
public abstract class LwJglRenderingEngine extends Thread {

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
    
    protected Rendering rendering;
    
    public boolean recording = false;
    
    private int list = -1;

    public LwJglRenderingEngine(String lwJglNativePath, Rendering rendering) {
        this.rendering = rendering;
        System.setProperty("org.lwjgl.librarypath", lwJglNativePath);
        
    }

    public void startEngine() {
        int width = 800;
        int height = 600;
        
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setFullscreen(false);
            Display.setTitle("Gaalop Visualization Window");

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
        
        
    }

    @Override
    public void run() {
        startEngine();
        //long start = System.currentTimeMillis();
        while (!Display.isCloseRequested()) {
            //System.out.println(System.currentTimeMillis()-start);
            //start = System.currentTimeMillis();
            
            if (rendering.isNewDataSetAvailable()) {
                if (list != -1) GL11.glDeleteLists(list, 1);
                list = GL11.glGenLists(1);
                GL11.glNewList(list, GL11.GL_COMPILE);
                draw(rendering.getDataSet(), rendering.getVisibleObjects());
                GL11.glEndList();
            }
            
            
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
            if (list != -1) GL11.glCallList(list);

            pollInput();
            Display.update();
            if (recording) {
                makeScreenshot();
                Display.sync(25); // cap fps to 60fps
            }
            else 
                Display.sync(60); 
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
        
        if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
            //Start recording
            if (!recording) {
                startRecording();
                recording = true;
            }
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
            //Stop recording
            if (recording) {
                recording = false;
                stopRecording();
            }
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);

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
    public abstract void draw(HashMap<String, PointCloud> clouds, HashSet<String> visibleObjects);

    private ByteBuffer screenBuffer;
    
    /**
     * Code from pc
     */
    private void makeScreenshot() {
        if (screenBuffer == null)
            screenBuffer = ByteBuffer.allocateDirect(Display.getDisplayMode().getWidth() * Display.getDisplayMode().getHeight() * 3);
        
        // publish
        try {
                GL11.glReadBuffer(GL11.GL_BACK);
                GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
                GL11.glReadPixels(0, 0, Display.getDisplayMode().getWidth(),
                                Display.getDisplayMode().getHeight(), GL11.GL_RGB,
                                GL11.GL_UNSIGNED_BYTE, screenBuffer);
                final BufferedImage image = transformPixelsRGBBuffer2ARGB_ByHand(screenBuffer);

                encoder.addFrame(image);
        } catch (Exception e) {
                System.out.println("Streaming exception.");
                e.printStackTrace();
        }
    }
    
    /**
     * Code from pc
     * @param pixelsRGB
     * @return 
     */
    private BufferedImage transformPixelsRGBBuffer2ARGB_ByHand(
			ByteBuffer pixelsRGB) {
            int width = Display.getDisplayMode().getWidth();
            int height = Display.getDisplayMode().getHeight();

            // Transform the ByteBuffer and get it as pixeldata.

            int[] pixelInts = new int[width * height];

            // Convert RGB bytes to ARGB ints with no transparency.
            // Flip image vertically by reading the
            // rows of pixels in the byte buffer in reverse
            // - (0,0) is at bottom left in OpenGL.
            //
            // Points to first byte (red) in each row.
            int p = width * height * 3;
            int q; // Index into ByteBuffer
            int i = 0; // Index into target int[]
            int w3 = width * 3; // Number of bytes in each row
            for (int row = 0; row < height; row++) {
                    p -= w3;
                    q = p;
                    for (int col = 0; col < width; col++) {
                            int iR = pixelsRGB.get(q++);
                            int iG = pixelsRGB.get(q++);
                            int iB = pixelsRGB.get(q++);
                            pixelInts[i++] = 0xFF000000 | ((iR & 0x000000FF) << 16)
                                            | ((iG & 0x000000FF) << 8) | (iB & 0x000000FF);
                    }
            }

            // Create a new BufferedImage from the pixeldata.
            BufferedImage bufferedImage = new BufferedImage(width, height,
                            BufferedImage.TYPE_INT_ARGB);
            bufferedImage.setRGB(0, 0, width, height, pixelInts, 0, width);

            return bufferedImage;
    }
    
    private AnimatedGifEncoder encoder;
   
    private void startRecording() {
        try {
            encoder = new AnimatedGifEncoder();
            
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    if (f.getName().toLowerCase().endsWith(".gif")) 
                        return true;
                    if (f.isDirectory()) return true;
                    return false;
                }

                @Override
                public String getDescription() {
                    return "GIF files";
                }
            });
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                encoder.start(new FileOutputStream(chooser.getSelectedFile()));
                encoder.setDelay(40);
                System.out.println("Started recording");
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LwJglRenderingEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void stopRecording() {
        System.out.println("Stopped recording");
        encoder.finish();
        System.out.println("Written recording");
        encoder = null;
    }

}
