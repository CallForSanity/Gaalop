package de.gaalop.gealg;

public class GaalopIndex extends BladeIndex{

	public GaalopIndex(Integer blade, GealgBladeTable table) {
		super(blade, table);
	}
	
	public GaalopIndex(String blade, GealgBladeTable table) {
		super(blade, table);
	}	
	
	public GaaletIndex getGaaletIndex() {
		return table.gaalopToGaalet(this);
	}
}
