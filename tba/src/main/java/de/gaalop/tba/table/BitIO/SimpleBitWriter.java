package de.gaalop.tba.table.BitIO;

import java.io.IOException;



/**
 * Implements a bit writer, that stores all values as a datatype,
 * which has at least the number of bits to be used.
 * The data types are java (unsigned) byte, char and int
 * @author Christian Steinmetz
 */
public class SimpleBitWriter extends AbsBitWriter {

    @Override
    public void write(int data, int bitCount) throws IOException {
        if (bitCount<=8)
            out.writeByte((data > 127) ? data-256 : data);
        else
            if (bitCount<=16)
                out.writeChar(data);
            else
                out.writeInt(data);
    }

}
