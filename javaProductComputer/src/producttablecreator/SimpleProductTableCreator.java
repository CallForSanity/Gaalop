package producttablecreator;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.GeoProductCalculator;
import de.gaalop.productComputer.InnerProductCalculator;
import de.gaalop.productComputer.OuterProductCalculator;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.MultTableAbsDirectComputer;
import de.gaalop.tba.table.BitIO.BitReader;
import de.gaalop.tba.table.BitIO.BitWriter;
import de.gaalop.tba.table.TableCompressed;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 * Creates a product table of an algebra, given by its definition file.
 * @author Christian Steinmetz
 */
public class SimpleProductTableCreator {

    /**
     * @param args the command line arguments:
     * 0: directory path
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File dir = null;
        if (args.length == 0) {
            JFileChooser jFC = new JFileChooser();
            jFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (jFC.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                dir = jFC.getSelectedFile();
                System.out.println(dir.getCanonicalPath());
            } else {
                System.out.println("No directory chosen!");
                System.exit(1);
            }
        } else 
            dir = new File(args[0]);
        if (!dir.exists()) {
            System.out.println("The given first parameter, is not the path of an existing directory!");
            System.exit(2);
        }
        File definitionFile = new File(dir, "definition.csv");
        if (!definitionFile.exists()) {
            System.out.println("There is no file named 'definition.csv' in the directory!");
            System.exit(3);
        }
        AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();
        FileInputStream inputStream = new FileInputStream(definitionFile);
        alFile.loadFromFile(inputStream);
        inputStream.close();
        IMultTable inner = new MultTableAbsDirectComputer(alFile, new InnerProductCalculator());
        IMultTable outer = new MultTableAbsDirectComputer(alFile, new OuterProductCalculator());
        IMultTable geo = new MultTableAbsDirectComputer(alFile, new GeoProductCalculator());
        
        int dimension = alFile.base.length-1;
        TableCompressed compressed = new TableCompressed(new BitReader(), new BitWriter());
        compressed.writeToFile((int) Math.pow(2,dimension), dimension, inner, outer, geo, new FileOutputStream(new File(dir, "products.csv")));
        System.exit(0);
    }

}
