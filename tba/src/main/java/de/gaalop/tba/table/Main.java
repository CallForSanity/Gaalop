/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.table;

import de.gaalop.algebra.AlStrategy;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.GeoProductCalculator;
import de.gaalop.productComputer.InnerProductCalculator;
import de.gaalop.productComputer.OuterProductCalculator;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.MultTableAbsDirectComputer;
import de.gaalop.tba.table.BitIO.BitReader;
import de.gaalop.tba.table.BitIO.BitWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Christian Steinmetz
 */
public class Main {

    public static void main(String[] args) throws IOException {
        int[] dimensions = {2,3,4,5,6,9};
        for (int dimension: dimensions) {
            AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();
            String baseDirPath = "algebra/"+dimension+"d/";
            alFile.loadFromFile(AlStrategy.class.getResourceAsStream(baseDirPath+"definition.csv"));
            IMultTable inner = new MultTableAbsDirectComputer(alFile, new InnerProductCalculator());
            IMultTable outer = new MultTableAbsDirectComputer(alFile, new OuterProductCalculator());
            IMultTable geo = new MultTableAbsDirectComputer(alFile, new GeoProductCalculator());

            TableCompressed compressed = new TableCompressed(new BitReader(), new BitWriter());
            compressed.writeToFile((int) Math.pow(2,dimension), dimension, inner, outer, geo, new FileOutputStream("/home/christian/GaalopMaster/tbaAndGapp/algebra/src/main/resources/de/gaalop/algebra/algebra/"+dimension+"d/products.csv"));
        }
    }

}
