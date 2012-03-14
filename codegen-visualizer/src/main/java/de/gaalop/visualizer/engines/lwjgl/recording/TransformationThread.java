package de.gaalop.visualizer.engines.lwjgl.recording;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import org.lwjgl.opengl.Display;

/**
 * Implements a thread that transforms a bytebuffer to an image.
 * After transforming, the storage process is started
 * @author Christian Steinmetz
 */
public class TransformationThread extends Thread {
    
    private final LinkedList<RecByteBufferAndTime> list = new LinkedList<RecByteBufferAndTime>();
    
    private boolean terminate = false;
    
    private Recorder recorder;

    public TransformationThread(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Terminates the transformation.
     * This method does not stop the transformation of posted images
     */
    public void terminate() {
        terminate = true;
    }

    /**
     * Adds a frame
     * @param buffer The ByteBuffer of the frame
     * @param delay The delay between the last frame and this frame in ms
     */
    public void addFrame(ByteBuffer buffer, long delay) {
        RecByteBufferAndTime b = new RecByteBufferAndTime(buffer, delay);
        synchronized (list) {
            list.add(b);
        }
    }

    @Override
    public void run() {
        while (!terminate) {
            if (!list.isEmpty()) {
                RecByteBufferAndTime b = list.removeFirst();
                BufferedImage image = transformPixelsRGBBuffer2ARGB_ByHand(b.getByteBuffer());
                recorder._addFrame(image, b.getDelay());
            }
            Thread.yield();
        }
        recorder._finish();
    }
    
    /**
     * Transforms an rgb pixel array to an argb BufferedImage
     * Code from pc
     * @param pixelsRGB The rgb pixel array
     * @return The created image
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

}
