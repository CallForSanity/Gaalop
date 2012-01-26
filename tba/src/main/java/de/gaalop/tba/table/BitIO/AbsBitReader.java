/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.table.BitIO;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author christian
 */
public abstract class AbsBitReader {

    protected DataInputStream in;

    public void setDataInputStream(DataInputStream in) {
        this.in = in;
    }
    public abstract int read(int bitCount) throws IOException;

}
