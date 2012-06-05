/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 * Implements a bit writes which produces no padding bits,
 * except at the end of the file.
 * @author christian
 */
public class BitWriter extends AbsBitWriter {

    private long cache = 0;
    private int cachedBits = 0;

    @Override
    public void write(int data, int bitCount) throws IOException {
        cache <<= bitCount;
        cache |= data;
        cachedBits += bitCount;
        while (cachedBits>=8) {
            int toWrite = (int) ((cache >> (cachedBits-8)) & 0xFF);
            if (toWrite > 127) toWrite -= 256;
            out.writeByte(toWrite);
            cachedBits -= 8;
            cache &= ~(0xFF << cachedBits);
        }
    }

    @Override
    public void finish() throws IOException {
        if (cachedBits > 0) {
            cache <<= 8-cachedBits;
            int toWrite = (int) (cache & 0xFF);
            if (toWrite > 127) toWrite -= 256;
            out.writeByte(toWrite);
            cache = 0;
            cachedBits = 0;
        }
        super.finish();
    }
}
