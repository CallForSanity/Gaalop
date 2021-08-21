package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 * Implements a bit reader, that reads all values as a datatype,
 * which has at least the number of bits to be used.
 * The data types are java (unsigned) byte, char and int
 * @author Christian Steinmetz
 */
public class SimpleBitReader extends AbsBitReader {

    @Override
    public int read(int bitCount) throws IOException {
        if (bitCount<=8) {
            int b = (int) in.readByte();
            if (b < 0) b +=256;
            return b;
        }
        else
            if (bitCount<=16)
                return in.readChar();
            else
                return in.readInt();
    }

}
