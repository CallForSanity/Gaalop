package de.gaalop.productComputer;

import de.gaalop.algebra.AlStrategy;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.AlgebraPC;
import de.gaalop.tba.MultTableAbsDirectComputer;
import de.gaalop.tba.table.BitIO.BitReader;
import de.gaalop.tba.table.BitIO.BitWriter;
import de.gaalop.tba.table.TableCompressed;
import de.gaalop.tba.table.TableHumanReadable;
import de.gaalop.tba.table.TableReaderIO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Creates the multiplication table
 * @author Christian Steinmetz
 */
public class Main {

    public static void main(String[] args) throws IOException {
            ProductComputer productComputer = new ProductComputer();
            InputStream inputStream = AlStrategy.class.getResourceAsStream("algebra/5d/definition.csv");
            AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();
            alFile.loadFromFile(inputStream);
            AlgebraPC algebraPC = new AlgebraPC(alFile);
            productComputer.initialize(algebraPC);

            int bladeCount = (int) Math.pow(2, algebraPC.base.length);
            
            TableReaderIO io = new TableCompressed(new BitReader(), new BitWriter());
            io.writeToFile(
                    bladeCount,
                    algebraPC.base.length,
                    new MultTableAbsDirectComputer(alFile, new InnerProductCalculator()),
                    new MultTableAbsDirectComputer(alFile, new OuterProductCalculator()),
                    new MultTableAbsDirectComputer(alFile, new GeoProductCalculator()),
                    new FileOutputStream("products.csv")
                    );

    }

}
