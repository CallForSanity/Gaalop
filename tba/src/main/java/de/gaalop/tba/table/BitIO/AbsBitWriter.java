/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.table.BitIO;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author christian
 */
public abstract class AbsBitWriter {
    
    protected DataOutputStream out;

    public void setDataOutputStream(DataOutputStream out) {
        this.out = out;
    }

    public abstract void write(int data, int bitCount) throws IOException;

    public void finish() throws IOException {
        out.flush();
    }

}
