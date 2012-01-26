package de.gaalop.tba.table;

import de.gaalop.tba.IMultTable;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Defines an interface for input/output operations of product tables
 * @author christian
 */
public interface TableReaderIO {

    /**
     * Reads the three tables from a filestream.
     * This method closes the filestream after reading.
     * @param filestream The filestream
     * @param innerTable The initialised inner product table
     * @param outerTable The initialised outer product table
     * @param geoTable The initialised geometric product table
     */
    public void readFromFile(InputStream filestream, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable);

    /**
     * Writes the three tables to a filestream.
     * This method closes the filestream after writing.
     * @param bladeCount The number of blades
     * @param dimension The dimension of the algebra
     * @param innerTable The inner product table
     * @param outerTable The outer product table
     * @param geoTable The geometric product table
     * @param outputStream The filestream
     */
    public void writeToFile(int bladeCount, int dimension, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable, OutputStream outputStream);

}
