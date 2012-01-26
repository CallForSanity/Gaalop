/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.table.BitIO;

import java.io.IOException;

/**
 *
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
        while (cachedBits>=16) {
            int toWrite = (int) ((cache >> (cachedBits-16)) & 0xFFFF);
            out.writeChar(toWrite);
            cachedBits -= 16;
            cache &= ~(0xFFFF << cachedBits);
        }
    }

    @Override
    public void finish() throws IOException {
        if (cachedBits > 0) {
            cache <<= 16-cachedBits;
            int toWrite = (int) (cache & 0xFFFF);
            out.writeChar(toWrite);
            cache = 0;
            cachedBits = 0;
        }
        super.finish();
    }
}
