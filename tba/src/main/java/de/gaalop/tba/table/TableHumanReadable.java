package de.gaalop.tba.table;

import de.gaalop.tba.BladeRef;
import de.gaalop.tba.IMultTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stores the product tables in a human readable format
 * @author christian
 */
public class TableHumanReadable implements TableReaderIO {

    @Override
    public void readFromFile(InputStream filestream, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable) {
        try {
            BufferedReader d = new BufferedReader(new InputStreamReader(filestream));
            int line = 0;
            while (d.ready()) {
                String rest = d.readLine();

                BladeRef ref0 = Parser.parseBladeRef(rest.substring(0, rest.indexOf(';')));
                rest = rest.substring(rest.indexOf(';') + 1);
                BladeRef ref1 = Parser.parseBladeRef(rest.substring(0, rest.indexOf(';')));
                rest = rest.substring(rest.indexOf(';') + 1);

                int index0 = ref0.getIndex();
                int index1 = ref1.getIndex();
                innerTable.setProduct(index0, index1, Parser.parseMultivector(rest.substring(0, rest.indexOf(';'))));
                rest = rest.substring(rest.indexOf(';') + 1);
                outerTable.setProduct(index0, index1, Parser.parseMultivector(rest.substring(0, rest.indexOf(';'))));
                rest = rest.substring(rest.indexOf(';') + 1);
                geoTable.setProduct(index0, index1, Parser.parseMultivector(rest));
                line++;
            }
            d.close();
        } catch (IOException ex) {
            Logger.getLogger(TableCompressed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeToFile(int bladeCount, int dimension, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable, OutputStream outputStream) {
        PrintStream out = new PrintStream(outputStream);

        for (int i=0;i<bladeCount;i++)
            for (int j=0;j<bladeCount;j++) {
                out.print("E");out.print(i);out.print(";");
                out.print("E");out.print(j);out.print(";");
                out.print(innerTable.getProduct(i, j).print());out.print(";");
                out.print(outerTable.getProduct(i, j).print());out.print(";");
                out.print(geoTable.getProduct(i, j).print());
                out.println();
            }
        out.close();
    }

}
