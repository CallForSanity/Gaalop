package de.gaalop.tba;

import de.gaalop.productComputer2.ProductComputer2;
import de.gaalop.productComputer2.ProductCalculator2;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.AlgebraPC;

/**
 * Provides methods for direct computation of the product of two blades
 * @author Christian Steinmetz
 */
public class MultTableAbsDirectComputer2 implements IMultTable {

    private ProductComputer2 computer;
    private ProductCalculator2 calculator;

    public MultTableAbsDirectComputer2(AlgebraDefinitionFile alFile, ProductCalculator2 calculator) {
        AlgebraPC algebraPC = new AlgebraPC(alFile);
        computer = new ProductComputer2();
        computer.initialize(algebraPC);
        this.calculator = calculator;
    }

    @Override
    public void createTable(int dimension) {
        //Do nothing
    }
    
    @Override
    public Multivector getProduct(Integer factor1, Integer factor2) {
        return computer.calcProduct(factor1, factor2, calculator);
    }

    @Override
    public void setProduct(Integer factor1, Integer factor2, Multivector product) {
        //Do nothing
    }
}
