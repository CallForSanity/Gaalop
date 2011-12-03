package de.gaalop.productComputer;

import java.io.PrintStream;

/**
 *
 * @author christian
 */
public class CalcThread extends Thread {

    private ProductComputer productComputer;
    private InnerProductCalculator inner = new InnerProductCalculator();
    private OuterProductCalculator outer = new OuterProductCalculator();
    private GeoProductCalculator geo = new GeoProductCalculator();
    private int from;
    private int to;
    private int bladeCount;
    private PrintStream out;

    public CalcThread(int from, int to, int bladeCount, AlgebraPC algebraPC, PrintStream out) {
        this.out = out;
        this.from = from;
        this.to = to;
        this.bladeCount = bladeCount;
        productComputer = new ProductComputer();
        productComputer.initialize(algebraPC);
    }

    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            for (int j = 0; j < bladeCount; j++) {
                out.print("E"+i+";E"+j+";");
                out.print(productComputer.calcProduct(i, j, inner).print());
                out.print(";");
                out.print(productComputer.calcProduct(i, j, outer).print());
                out.print(";");
                out.println(productComputer.calcProduct(i, j, geo).print());
            }
        }
        out.close();
    }

        

}
