package de.gaalop.tba.table;

import de.gaalop.tba.IMultTable;
import de.gaalop.tba.table.BitIO.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Manages all table formats
 * @author Christian Steinmetz
 */
public class TableFormat {
    
    public static final int TABLE_COMPRESSED_MAX       = 1;
    public static final int TABLE_COMPRESSED_INT       = 2;
    public static final int TABLE_COMPRESSED_SIMPLE    = 3;
    public static final int TABLE_HUMAN_READABLE       = 69;

    /**
     * Returns the format for a given TableReaderIO instance
     * @param tableReaderIO The instance
     * @return The format of the given TableReaderIO
     */
    public static int getByteFormat(TableReaderIO tableReaderIO) {
        if (tableReaderIO instanceof TableCompressed) {
            TableCompressed tc = (TableCompressed) tableReaderIO;
            AbsBitReader reader = tc.getReader();
            if (reader instanceof MaxReader) 
                return TABLE_COMPRESSED_MAX;
            if (reader instanceof IntReader) 
                return TABLE_COMPRESSED_INT;
            if (reader instanceof SimpleBitReader) 
                return TABLE_COMPRESSED_SIMPLE;
        }
        if (tableReaderIO instanceof TableHumanReadable) 
            return TABLE_HUMAN_READABLE;
        
        throw new IllegalArgumentException("Table_IO type unknown");
    }
    
    /**
     * Creates an according TableReaderIO instance for a format
     * @param byteFormat The format
     * @return The created TableReaderIO instance
     */
    public static TableReaderIO getTableReaderIO(int byteFormat) {
        switch (byteFormat) {
            case TABLE_COMPRESSED_MAX:
                return new TableCompressed(new MaxReader(), new MaxWriter());
            case TABLE_COMPRESSED_INT:
                return new TableCompressed(new IntReader(), new IntWriter());
            case TABLE_COMPRESSED_SIMPLE:
                return new TableCompressed(new SimpleBitReader(), new SimpleBitWriter());
            case TABLE_HUMAN_READABLE:
                return new TableHumanReadable();
            default:
                throw new IllegalArgumentException("Products file format not recognized");
        }
    }

    public static void readFromFile(InputStream filestream, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable) throws IOException {
        DataInputStream in = new DataInputStream(filestream);
        byte format = in.readByte();
        getTableReaderIO(format).readFromInputStream(in, innerTable, outerTable, geoTable);
    }
    
    public static void writeToFile(int bladeCount, int dimension, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable, OutputStream outputStream, int format) throws IOException {
        DataOutputStream out = new DataOutputStream(outputStream);
        out.writeByte(format); //format
        getTableReaderIO(format).writeFromInputStream(bladeCount, dimension, innerTable, outerTable, geoTable, out);
    }
    
}
