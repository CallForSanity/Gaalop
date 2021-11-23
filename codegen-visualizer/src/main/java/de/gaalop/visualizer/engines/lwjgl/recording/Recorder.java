package de.gaalop.visualizer.engines.lwjgl.recording;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * Implements a basic recorder. Provides the startRecording() and stopRecording() method,
 * as well as the makeScreenshot method
 * 
 * @author Christian Steinmetz
 */
public abstract class Recorder {

    private long lastTime = -1;
    private long curTime = -1;

    private TransformationThread thread;
    
    /**
     * Make a screenshot from the current LWJGL Display
     * Code from pc
     */
    public void makeScreenshot() {
        curTime = System.currentTimeMillis();
       
        ByteBuffer screenBuffer = ByteBuffer.allocateDirect(Display.getDisplayMode().getWidth() * Display.getDisplayMode().getHeight() * 3);
        
        try {
                GL11.glReadBuffer(GL11.GL_BACK);
                GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
                GL11.glReadPixels(0, 0, Display.getDisplayMode().getWidth(),
                                Display.getDisplayMode().getHeight(), GL11.GL_RGB,
                                GL11.GL_UNSIGNED_BYTE, screenBuffer);
                
                long delay = (lastTime == -1) ? 0: curTime-lastTime;
                lastTime = curTime;
                thread.addFrame(screenBuffer, delay);
                
        } catch (Exception e) {
                System.out.println("Streaming exception.");
                e.printStackTrace();
        }
    }
    
    /**
     * Adds the frame as image
     * @param image The image
     * @param delay The delay in ms between this and last image
     */
    public abstract void _addFrame(BufferedImage image, long delay);

    /**
     * Starts the recording
     */
    public void startRecording() {
        thread = new TransformationThread(this);
        _startRecording();
        thread.start();
    }

    /**
     * Stops the recording
     */
    public void stopRecording() {
        System.out.println("Stopped recording");
        thread.terminate();
    }
    
    /**
     * This method is called when starting recording
     */
    protected abstract void _startRecording();
    
    /**
     * This method is called when finishing recording
     */
    public abstract void _finish();

}
