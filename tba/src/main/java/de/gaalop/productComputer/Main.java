package de.gaalop.productComputer;

import de.gaalop.algebra.AlStrategy;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.UseAlgebra;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class defines a main method that can be used for creating product tables.
 * Note that a perfomance c++ implementation exists for a fast computation of product tables.
 * @author christian
 */
public class Main {

    /**
     * Creates product tables
     * @param args number_of_threads definition_path use_built_in
     */
    public static void main(String[] args) {
        correctness5d(); //test first of all the correctness by comparing with the 5d table
        performanceOnArbitraryAlgebra(parseAlgebraDefinitionFile(args),Integer.parseInt(args[0])); //now the real performance computation
    }

    private static AlgebraDefinitionFile parseAlgebraDefinitionFile(String[] args) {
        try {
            InputStream inputStream = (Boolean.parseBoolean(args[2])) ? AlStrategy.class.getResourceAsStream(args[1]) : new FileInputStream(args[1]);
            AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();
            alFile.loadFromFile(inputStream);
            return alFile;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static void correctness5d() {
        ProductComputer productComputer = new ProductComputer();
        AlgebraDefinitionFile alFile = parseAlgebraDefinitionFile(new String[]{"1","algebra/5d/definition.csv","true"});
        AlgebraPC algebra = new AlgebraPC(alFile);
        productComputer.initialize(algebra);

        UseAlgebra use = UseAlgebra.get5dConformalGALive();

        InnerProductCalculator inner = new InnerProductCalculator();
        OuterProductCalculator outer = new OuterProductCalculator();
        GeoProductCalculator geo = new GeoProductCalculator();

        int bladeCount = (int) Math.pow(2,algebra.base.length);
        for (int i=0;i<bladeCount;i++)
            for (int j=0;j<bladeCount;j++) {
                test(use.getTableInner().getProduct(i, j), productComputer.calcProduct(i, j, inner),'.',i,j);
                test(use.getTableOuter().getProduct(i, j), productComputer.calcProduct(i, j, outer),'^',i,j);
                test(use.getTableGeo().getProduct(i, j), productComputer.calcProduct(i, j, geo),'*',i,j);
            }
    }

    private static void performanceOnArbitraryAlgebra(AlgebraDefinitionFile alFile, int threadCount) {
        try {
            long tick = System.currentTimeMillis();
            ProductComputer productComputer = new ProductComputer();
            AlgebraPC algebraPC = new AlgebraPC(alFile);
            productComputer.initialize(algebraPC);

            int bladeCount = (int) Math.pow(2,algebraPC.base.length);
            CalcThread[] threads = new CalcThread[threadCount];
            File[] tmpFiles = new File[threadCount];
            for (int i = 0; i < threadCount; i++) {
                try {
                    tmpFiles[i] = File.createTempFile("GaalopProducts", ".csv");
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                int from = (i * bladeCount) / threadCount;
                int to = ((i + 1) * bladeCount) / threadCount;

                if (i == threadCount-1)
                    to = bladeCount;
                
                try {
                    threads[i] = new CalcThread(from, to, bladeCount, algebraPC, new PrintStream(tmpFiles[i]));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (int i = 0; i < threadCount; i++) {
                threads[i].start();
            }
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
            }

            try {
                //merge all products files
                PrintWriter productsOut = new PrintWriter("products.csv");
                for (int i = 0; i < threadCount; i++) {
                    BufferedReader reader = new BufferedReader(new FileReader(tmpFiles[i]));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null)
                            productsOut.println(line);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tmpFiles[i].delete();
                }

                productsOut.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }


            System.out.println((System.currentTimeMillis()-tick) / 1000+"s");

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Comparator<BladeRef> comp = new Comparator<BladeRef>() {
        @Override
        public int compare(BladeRef o1, BladeRef o2) {
            return o1.getIndex()-o2.getIndex();
        }
    };

    private static void test(Multivector m1, Multivector m2, char c, int i, int j) {
        LinkedList<BladeRef> l1 = new LinkedList<BladeRef>();
        LinkedList<BladeRef> l2 = new LinkedList<BladeRef>();

        for (BladeRef r1: m1.getBlades())
            if (r1.getPrefactor() != 0)
                l1.add(r1);

        for (BladeRef r2: m1.getBlades())
            if (r2.getPrefactor() != 0)
                l2.add(r2);


        BladeRef[] blades1 = l1.toArray(new BladeRef[0]);
        BladeRef[] blades2 = l2.toArray(new BladeRef[0]);

        if (blades1.length != blades2.length) {
            System.err.println(i+""+c+""+j+":"+m1+" != "+m2);
            return;
        }

        Arrays.sort(blades1, comp);
        Arrays.sort(blades2, comp);

        for (int b=0;b<blades1.length;b++) {
            if (blades1[b].getIndex() != blades2[b].getIndex() || blades1[b].getPrefactor() != blades2[b].getPrefactor()) {
                System.err.println(i+""+c+""+j+":"+m1+" != "+m2);
                return;
            }
        }

            
    }

}
