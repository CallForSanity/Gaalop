package de.gaalop.tba;

/**
 * Implements the IMultTable interface.
 *
 * Stores the product table as an two dimensional array.
 *
 * @author Christian Steinmetz
 */
public class MultTableImpl implements IMultTable {

    private Multivector[][] products;

    @Override
    public void createTable(int dimension) {
        products = new Multivector[dimension][dimension];
    }

    @Override
    public Multivector getProduct(Integer factor1, Integer factor2) {
        return products[factor1][factor2];
    }

    @Override
    public void setProduct(Integer factor1, Integer factor2, Multivector product) {
        products[factor1][factor2] = product;
    }
}
