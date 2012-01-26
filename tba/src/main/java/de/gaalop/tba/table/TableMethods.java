/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author Christian Steinmetz
 */
public class TableMethods {

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
