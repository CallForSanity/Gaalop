package de.gaalop.productComputer.exe.direct;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.tba.Multivector;
import de.gaalop.productComputer2.AlgebraPC;
import de.gaalop.productComputer2.ProductCalculator;
import de.gaalop.productComputer2.ProductComputer;
import de.gaalop.tba.IMultTable;

/**
 * Provides methods for direct computation of the product of two blades
 * @author Christian Steinmetz
 */
public class MultTableAbsDirectComputer implements IMultTable {

    private ProductComputer computer;
    private ProductCalculator calculator;

    public MultTableAbsDirectComputer(AlgebraDefinitionFile alFile, ProductCalculator calculator) {
        AlgebraPC algebraPC = new AlgebraPC(alFile);
        computer = new ProductComputer();
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
