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
