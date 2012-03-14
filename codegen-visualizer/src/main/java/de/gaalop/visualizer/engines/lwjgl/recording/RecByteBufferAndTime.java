package de.gaalop.visualizer.engines.lwjgl.recording;

import java.nio.ByteBuffer;

/**
 *
 * @author Christian Steinmetz
 */
public class RecByteBufferAndTime {
    
    private ByteBuffer byteBuffer;
    private long delay;

    public RecByteBufferAndTime(ByteBuffer byteBuffer, long delay) {
        this.byteBuffer = byteBuffer;
        this.delay = delay;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public long getDelay() {
        return delay;
    }

}
