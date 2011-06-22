package de.gaalop.tba;

import de.gaalop.tba.Multivector;

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
