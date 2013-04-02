package de.gaalop.tba.table;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.GeoProductCalculator;
import de.gaalop.productComputer.InnerProductCalculator;
import de.gaalop.productComputer.OuterProductCalculator;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.MultTableAbsDirectComputer;
import de.gaalop.tba.MultTableImpl;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.table.BitIO.BitReader;
import de.gaalop.tba.table.BitIO.BitWriter;
import de.gaalop.tba.table.BitIO.SimpleBitReader;
import de.gaalop.tba.table.BitIO.SimpleBitWriter;
import de.gaalop.tba.table.TableCompressed;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFileChooser;

/**
 * Creates a product table of an algebra, given by its definition file.
 * @author Christian Steinmetz
 */
public class Main {

    /**
     * @param args the command line arguments:
     * 0: directory path
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File dir = null;

        if (args.length == 0) {
            JFileChooser jFC = new JFileChooser("D:\\BscMsc\\Gaalop\\algebra\\src\\main\\resources\\de\\gaalop\\algebra\\algebra");
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
        TableReaderIO tableReaderIO = new TableCompressed(new SimpleBitReader(), new SimpleBitWriter());
        tableReaderIO.writeToFile((int) Math.pow(2,dimension), dimension, inner, outer, geo, new FileOutputStream(new File(dir, "products.csv")));
        
        System.out.println("Test");
        
        int bladeCount = (int) Math.pow(2, dimension);
        
        IMultTable innerStored = new MultTableImpl();
        innerStored.createTable(bladeCount);
        IMultTable outerStored = new MultTableImpl();
        outerStored.createTable(bladeCount);
        IMultTable geoStored = new MultTableImpl();
        geoStored.createTable(bladeCount);
        tableReaderIO.readFromFile(new FileInputStream(new File(dir, "products.csv")), innerStored, outerStored, geoStored);
        
        testEqual(innerStored, inner, dimension);
        testEqual(outerStored, outer, dimension);
        testEqual(geoStored, geo, dimension);
    }

    private static void testEqual(IMultTable t1, IMultTable t2, int dimension) {
        int bladeCount = (int) Math.pow(2,dimension);
        for (int i1 = 0;i1 < bladeCount;i1++)
            for (int i2 = 0;i2 < bladeCount;i2++)
                if (!testEqual(t1.getProduct(i1, i2), t2.getProduct(i1, i2)))
                    System.out.print(i1+"!="+i2+": "+t1.getProduct(i1, i2).print()+" != "+t2.getProduct(i1, i2).print());
        
    }

    private static boolean testEqual(Multivector m1, Multivector m2) {
        Vector<BladeRef> v1 = new Vector<BladeRef>(m1.getBlades());
        Vector<BladeRef> v2 = new Vector<BladeRef>(m2.getBlades());
        
        if (v1.size() != v2.size())
            return false;
        
        for (BladeRef b2: v2) {
            if (v1.contains(b2))
                v1.remove(b2);
        }
        
        return v1.isEmpty();
    }
    
}
