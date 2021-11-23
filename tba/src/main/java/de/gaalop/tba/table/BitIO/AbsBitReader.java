package de.gaalop.tba.table.BitIO;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Defines an abstract bit reader, that read bits from a file
 * @author Christian Steinmetz
 */
public abstract class AbsBitReader {

    protected DataInputStream in;
    
    public void setDataInputStream(DataInputStream in) {
        this.in = in;
    }

    /**
     * Read a number of bits from a file and returns the value
     * @param bitCount The number of bits to read
     * @return The value
     * @throws IOException
     */
    public abstract int read(int bitCount) throws IOException;

}
