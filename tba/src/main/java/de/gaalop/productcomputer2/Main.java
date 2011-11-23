package de.gaalop.productcomputer2;

import de.gaalop.tba.BladeRef;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.UseAlgebra;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main method for computing products
 * @author christian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        correctness5d();
        //performanceOn9d(args);
    }

    private static void correctness5d() {
        ProductComputer productComputer = new ProductComputer();
        Algebra algebra = new Algebra5d();
        algebra.create();
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

    private static void performanceOn9d(String[] args) {
        try {
            long tick = System.currentTimeMillis();
            ProductComputer productComputer = new ProductComputer();
            Algebra algebra = new Algebra9d();
            algebra.create();
            productComputer.initialize(algebra);

            int bladeCount = (int) Math.pow(2,algebra.base.length);
            int n = Integer.parseInt(args[0]);
            CalcThread[] threads = new CalcThread[n];
            for (int i = 0; i < n; i++) {
                threads[i] = new CalcThread((i * bladeCount) / n, ((i + 1) * bladeCount) / n, bladeCount);
            }
            for (int i = 0; i < n; i++) {
                threads[i].start();
            }
            for (int i = 0; i < n; i++) {
                threads[i].join();
            }

            System.out.println((System.currentTimeMillis()-tick) / 1000+"s");

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Comparator<BladeRef> comp = new Comparator<BladeRef>() {
        public int compare(BladeRef o1, BladeRef o2) {
            return o1.getIndex()-o2.getIndex();
        }
    };

    private static void test(Multivector m1, Multivector m2, char c, int i, int j) {
        BladeRef[] blades1 = m1.getBlades().toArray(new BladeRef[0]);
        BladeRef[] blades2 = m2.getBlades().toArray(new BladeRef[0]);

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
