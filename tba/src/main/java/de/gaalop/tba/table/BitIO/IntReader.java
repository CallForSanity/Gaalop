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
public class IntReader extends AbsBitReader {

    @Override
    public int read(int bitCount) throws IOException {
        return in.readInt();
    }

}
