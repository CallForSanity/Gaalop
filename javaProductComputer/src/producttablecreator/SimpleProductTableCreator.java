package producttablecreator;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.GeoProductCalculator;
import de.gaalop.productComputer.InnerProductCalculator;
import de.gaalop.productComputer.OuterProductCalculator;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.MultTableAbsDirectComputer;
import de.gaalop.tba.MultTableImpl;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.table.BitIO.MaxReader;
import de.gaalop.tba.table.BitIO.MaxWriter;
import de.gaalop.tba.table.TableCompressed;
import de.gaalop.tba.table.TableFormat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
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
        
        File[] subDirs = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (subDirs.length == 0) {
            computeTableOfSingleAlgebra(dir);
        } else {
            for (File dirS: subDirs) {
                System.out.println("Compute "+dirS.getCanonicalPath());
                computeTableOfSingleAlgebra(dirS);
            }
        }
        
        System.exit(0);
    }
    
    private static void computeTableOfSingleAlgebra(File dir) throws FileNotFoundException, IOException {
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
        DataOutputStream dout =  new DataOutputStream(new FileOutputStream(new File(dir, "products.csv")));
        TableFormat.writeToFile((int) Math.pow(2,dimension), dimension, inner, outer, geo, dout, TableFormat.TABLE_COMPRESSED_MAX);
        //Test written products
        
        int bladeCount = (int) Math.pow(2,dimension);
        
        IMultTable innerS = new MultTableImpl();innerS.createTable(bladeCount);
        IMultTable outerS = new MultTableImpl();outerS.createTable(bladeCount);
        IMultTable geoS = new MultTableImpl();geoS.createTable(bladeCount);
        TableFormat.readFromFile(new DataInputStream(new FileInputStream(new File(dir, "products.csv"))), innerS, outerS, geoS);
        
        
        
        boolean ok = true;
        
        for (int i=0;i<bladeCount;i++)
            for (int j=0;j<bladeCount;j++) {
                if (!multivectorsEqual(inner.getProduct(i, j), innerS.getProduct(i, j)))
                    ok = false;
                if (!multivectorsEqual(outer.getProduct(i, j), outerS.getProduct(i, j)))
                    ok = false;
                if (!multivectorsEqual(geo.getProduct(i, j) ,geoS.getProduct(i, j)))
                    ok = false;
            }
        
        System.out.println("Successful="+ok);
        
        
    }

    private static boolean multivectorsEqual(Multivector m1, Multivector m2) {
        if (m1.getBlades().size() != m2.getBlades().size()) 
            return false;
        HashSet<BladeRef> set1 = new HashSet<BladeRef>(m1.getBlades());
        
        for (BladeRef bref2: m2.getBlades()) {
            if (set1.contains(bref2)) {
                set1.remove(bref2);
            } else
                return false;
        }
        
        return set1.isEmpty();
    }

}
