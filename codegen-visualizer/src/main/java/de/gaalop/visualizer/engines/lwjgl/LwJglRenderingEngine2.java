package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.Point3d;
import de.gaalop.visualizer.PointCloud;
import de.gaalop.visualizer.Rendering;
import de.gaalop.visualizer.engines.lwjgl.recording.GIFRecorder;
import de.gaalop.visualizer.engines.lwjgl.recording.Recorder;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


/**
 * Implements a rendering engine based on LwJgl
 * @author Christian Steinmetz
 */
public class LwJglRenderingEngine2 extends RenderingEngine {

    // Camera information
    private Vec3f camPos = new Vec3f(0.0f, 2.0f, 2.25f);       // camera position
    //private float camAngleX = 0.0f, camAngleY = 0.0f;   // camera angles
    // Mouse information
    private int mouseX, mouseY, mouseButton;
    private float mouseSensitivy = 1.0f;
    private boolean[] buttonDown = new boolean[]{false, false, false};
    private static final int STATE_DOWN = 1;
    private static final int STATE_UP = 2;
    
    protected Rendering rendering;
    
    
    
    public Recorder recorder;
    
    private boolean changed = false;
    private boolean firstFrame = true;
    
    private int list = -1;

    public LwJglRenderingEngine2(String lwJglNativePath, Rendering rendering) {
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
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        GL11.glEnable(GL11.GL_TEXTURE_2D);


        // init OpenGL

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glViewport(0,0,width,height);
            GL11.glOrtho(0, width, 0, height, 0, 128);
        
    }

    @Override
    public void run() {
        startEngine();
        //long start = System.currentTimeMillis();
        while (!Display.isCloseRequested()) {
            //System.out.println(System.currentTimeMillis()-start);
            //start = System.currentTimeMillis();
            

            
            
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);



            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();

            GL11.glTranslatef(320, 240, 0);
            
            GL11.glTranslatef(camPos.x, camPos.y, 0);
            
            GL11.glScalef(camPos.z, camPos.z, 1);

            GL11.glDisable(GL11.GL_BLEND);
        
            if (rendering.isNewDataSetAvailable()) {
                if (list != -1) GL11.glDeleteLists(list, 1);
                list = GL11.glGenLists(1);
                GL11.glNewList(list, GL11.GL_COMPILE);
                drawKOS();
                draw(rendering.getDataSet(), rendering.getVisibleObjects());
                GL11.glEndList();
                changed = true;
            }
            
            //Render the scene
            if (list != -1) GL11.glCallList(list);

            pollInput();
            Display.update();
            if (recorder != null) {
                if (changed || firstFrame) {
                    recorder.makeScreenshot();
                    changed = false;
                }
                firstFrame = false;
                Display.sync(25); // cap fps to 60fps
            }
            else 
                Display.sync(60); 
        }

        Display.destroy();
    }
    
    void mouseMoved(int x, int y) {
        switch (mouseButton) {
            // 2 => zoom
            case 2:
                float old = camPos.z;
                camPos.z -= 0.1f * (y - mouseY) * mouseSensitivy;
                if (camPos.z <= 0) camPos.z = old;
                changed = true;
                break;
            // 3 => translate
            case 3:
                // update camPos
                camPos.x += 5 * (x - mouseX) * mouseSensitivy;
                camPos.y += 5 * (y - mouseY) * mouseSensitivy;
                changed = true;
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
            if (recorder == null) {
                recorder = new GIFRecorder();
                recorder.startRecording();
            }
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
            //Stop recording
            if (recorder != null) {
                recorder.stopRecording();
                recorder = null;
            }
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            if (recorder != null) 
                recorder.stopRecording();
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
    public void draw(HashMap<String, PointCloud> clouds, HashSet<String> visibleObjects) {
        for (String obj: visibleObjects) {
            PointCloud cloud = clouds.get(obj);
            GL11.glColor4f(cloud.color.getRed(),cloud.color.getGreen(),cloud.color.getBlue(),cloud.color.getAlpha());
            for (Point3d p: cloud.points) 
                drawPoint(trX(p.x), trY(p.y));
        }
    }
    
    private float trX(double x) {
        return (float) (x*10);
    }
    
    private float trY(double y) {
        return (float) (y*10);
    }
    
    private void drawPoint(float x, float y) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(x-pointSize, y-pointSize, 0);
        GL11.glVertex3f(x+pointSize, y-pointSize, 0);
        GL11.glVertex3f(x+pointSize, y+pointSize, 0);
        GL11.glVertex3f(x-pointSize, y+pointSize, 0);
        GL11.glEnd();
    }
    
    public static void main(String[] args) {
        Rendering r = new Rendering() {

            @Override
            public boolean isNewDataSetAvailable() {
                return true;
            }

            @Override
            public HashMap<String, PointCloud> getDataSet() {
                HashMap<String, PointCloud> map = new HashMap<String, PointCloud>();
                LinkedList<Point3d> points = new LinkedList<Point3d>();
                points.add(new Point3d(0, 0, 0));points.add(new Point3d(0, 1, 0));points.add(new Point3d(2, 0, 0));
                
                map.put("test", new PointCloud(Color.green, points));
                return map;
            }

            @Override
            public HashSet<String> getVisibleObjects() {
                HashSet<String> set = new HashSet<String>();
                set.add("test");
                return set;
            }
        };
        LwJglRenderingEngine2 engine = new LwJglRenderingEngine2("C:\\Libs\\lwjgl\\native\\windows\\", r);
        engine.start();
    }

    private void drawKOS() {
        GL11.glColor3f(0.2f,0.2f,0.2f);
        for (int i=1;i<=10;i++) {
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2f(trX(-10), trY(-i)); GL11.glVertex2f(trX(10), trY(-i));
            GL11.glVertex2f(trX(-10), trY(i)); GL11.glVertex2f(trX(10), trY(i));
            GL11.glVertex2f(trX(-i), trY(-10)); GL11.glVertex2f(trX(-i), trY(10));
            GL11.glVertex2f(trX(i), trY(-10)); GL11.glVertex2f(trX(i), trY(10));
            GL11.glEnd();
        }
        
        GL11.glColor3f(1,1,1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(trX(-10), trY(0));
        GL11.glVertex2f(trX(10), trY(0));
        GL11.glVertex2f(trX(0), trY(-10));
        GL11.glVertex2f(trX(0), trY(10));
        GL11.glEnd();
        
    }
    
    
}
