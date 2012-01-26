package de.gaalop.tba.table;

import de.gaalop.tba.IMultTable;
import de.gaalop.tba.MultTableImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides methods for tables
 * @author Christian Steinmetz
 */
public class TableMethods {

    /**
     * Converts the format of a table
     * @param dimension The dimension of the algebra
     * @param fromFormat The format of the source table
     * @param fromFile The file of the source table
     * @param toFormat The format of the destination table
     * @param toFile The file of the destination table
     */
    public static void convertFormat(int dimension, TableReaderIO fromFormat, File fromFile, TableReaderIO toFormat, File toFile) {
        
        int bladeCount = (int) Math.pow(2, dimension);

        // read fromFile
        IMultTable inner = new MultTableImpl();
        inner.createTable(bladeCount);
        IMultTable outer = new MultTableImpl();
        outer.createTable(bladeCount);
        IMultTable geo = new MultTableImpl();
        geo.createTable(bladeCount);
        
        try {
            fromFormat.readFromFile(new FileInputStream(fromFile), inner, outer, geo);
            toFormat.writeToFile(bladeCount, dimension, inner, outer, geo, new FileOutputStream(toFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableMethods.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
