package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 * Implements a bit writer, that stores all values as a integer
 * @author christian
 */
public class IntWriter extends AbsBitWriter {

    @Override
    public void write(int data, int bitCount) throws IOException {
        out.writeInt(data);
    }

}
