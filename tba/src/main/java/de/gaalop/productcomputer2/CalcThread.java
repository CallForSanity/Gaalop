package de.gaalop.productcomputer2;

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

    public CalcThread(int from, int to, int bladeCount) {
        this.from = from;
        this.to = to;
        this.bladeCount = bladeCount;
        productComputer = new ProductComputer();

        Algebra algebra = new Algebra9d();
        algebra.create();

        productComputer.initialize(algebra);
    }



    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            for (int j = 0; j < bladeCount; j++) {
                productComputer.calcProduct(i, j, inner);
                productComputer.calcProduct(i, j, outer);
                productComputer.calcProduct(i, j, geo);
            }
        }
    }

}
