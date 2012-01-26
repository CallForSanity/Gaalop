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
