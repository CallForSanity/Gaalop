package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 * Implements a bit writes which produces no padding bits,
 * except at the end of the file.
 * @author Christian Steinmetz
 */
public class MaxWriter extends AbsBitWriter {

    private long cache = 0;
    private int cachedBits = 0;

    @Override
    public void write(int data, int bitCount) throws IOException {
        cache <<= bitCount;
        long maskD = 0; maskD = ~maskD; maskD <<= bitCount; maskD = ~maskD; data &= maskD;
        cache |= data;
        cachedBits += bitCount;
        while (cachedBits>=16) {
            writeCharFromCache();
        }
    }
    
    private void writeCharFromCache() throws IOException {
        long cacheT = cache; cacheT >>= (cachedBits-16); 
        cacheT &= 0xFFFF;
        out.writeChar((int) cacheT);
        long maskC = 0; maskC = ~maskC; maskC <<= (cachedBits-16); maskC = ~maskC; cache &= maskC;
        cachedBits -= 16;
    }

    @Override
    public void finish() throws IOException {
        if (cachedBits == 0) return;
        if (cachedBits != 16) 
            write(0,16-cachedBits);
        writeCharFromCache();
    }
}
