package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 * Implements a bit reader which reads no padding bits,
 * except at the end of the file.
 * @author christian
 */
public class BitReader extends AbsBitReader {

    private long cache = 0;
    private int cachedBits = 0;

    @Override
    public int read(int bitCount) throws IOException {

        while (cachedBits < bitCount) {
            int data = (int) in.readByte();
            if (data < 0) data += 256;
            cache <<= 8;
            cache |= data;
            cachedBits += 8;
        }

        cachedBits -= bitCount;
        int result = (int) (cache >> cachedBits);
        
        for (int i=0;i<bitCount;i++)
            cache &= ~(1 << (i+cachedBits));

        return result;
    }

}
