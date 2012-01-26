/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.table;

import de.gaalop.tba.IMultTable;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author christian
 */
public interface TableReaderIO {

    public void readFromFile(InputStream filestream, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable);
    public void writeToFile(int bladeCount, int dimension, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable, OutputStream outputStream);

}
