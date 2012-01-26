package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 * Implements a bit reader, that reads all values as a integer
 * @author christian
 */
public class IntReader extends AbsBitReader {

    @Override
    public int read(int bitCount) throws IOException {
        return in.readInt();
    }

}
