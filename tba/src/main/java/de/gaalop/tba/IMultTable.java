package de.gaalop.tba;

import de.gaalop.tba.Multivector;

public interface IMultTable {

	public void createTable(int dimension);
	public Multivector getProduct(Integer factor1, Integer factor2);
	public void setProduct(Integer factor1, Integer factor2, Multivector product );
	
}
