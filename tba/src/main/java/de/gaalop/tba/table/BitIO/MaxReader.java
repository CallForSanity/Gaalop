package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 * Implements a bit reader which reads no padding bits,
 * except at the end of the file.
 * @author Christian Steinmetz
 */
public class MaxReader extends AbsBitReader {

    private long cache = 0;
    private int cachedBits = 0;
    
    @Override
    public int read(int bitCount) throws IOException {
        while (cachedBits < bitCount) {
            cache <<= 16;
            cache |= in.readChar();
            cachedBits += 16;
        }
        long cacheD = cache;
        cacheD >>= cachedBits-bitCount;
        long maskD = 0;maskD = ~maskD;maskD <<= bitCount;maskD = ~maskD;cacheD &= maskD;
        
        long maskC = 0;maskC = ~maskC;maskC <<= cachedBits-bitCount;maskC=~maskC;cache &= maskC;
        cachedBits -= bitCount;
        
        return (int) cacheD;
    }

}
