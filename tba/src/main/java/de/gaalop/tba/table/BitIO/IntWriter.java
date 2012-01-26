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
public class IntWriter extends AbsBitWriter {

    @Override
    public void write(int data, int bitCount) throws IOException {
        out.writeInt(data);
    }

}
