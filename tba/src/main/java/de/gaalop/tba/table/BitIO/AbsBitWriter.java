package de.gaalop.tba.table.BitIO;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Defines an abstract bit writer, that write bits to a file.
 * Therefore an internal cache can be used.
 * @author Christian Steinmetz
 */
public abstract class AbsBitWriter {
    
    protected DataOutputStream out;

    public void setDataOutputStream(DataOutputStream out) {
        this.out = out;
    }

    /**
     * Writes a value in a number of bits to a file
     * @param data
     * @param bitCount The number of bits to write
     * @throws IOException
     */
    public abstract void write(int data, int bitCount) throws IOException;

    /**
     * Flush the cache
     * @throws IOException
     */
    public void finish() throws IOException {
        out.flush();
    }

}
