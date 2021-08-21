package de.gaalop.tba.table;

import de.gaalop.tba.BladeRef;
import de.gaalop.tba.IMultTable;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stores the product tables in a human readable format
 * @author Christian Steinmetz
 */
public class TableHumanReadable implements TableReaderIO {

    @Override
    public void readFromInputStream(DataInputStream filestream, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable) {
        try {
            BufferedReader d = new BufferedReader(new InputStreamReader(filestream, Charset.forName("UTF-8")));
            long line = 0;
            while (d.ready()) {
                String rest = d.readLine();
                if (line == 0) rest = "E"+rest;

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
    public void writeFromInputStream(int bladeCount, int dimension, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable, DataOutputStream outputStream) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
        long line = 0;
        for (int i=0;i<bladeCount;i++)
            for (int j=0;j<bladeCount;j++) {
                if (line!=0) out.print("E");
                out.print(i);out.print(";");
                out.print("E");out.print(j);out.print(";");
                out.print(innerTable.getProduct(i, j).print());out.print(";");
                out.print(outerTable.getProduct(i, j).print());out.print(";");
                out.print(geoTable.getProduct(i, j).print());
                out.println();
                line++;
            }
        out.close();
    }

}
