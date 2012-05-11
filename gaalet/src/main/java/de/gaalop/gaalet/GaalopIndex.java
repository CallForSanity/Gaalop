package de.gaalop.gaalet;

public class GaalopIndex extends BladeIndex{

	public GaalopIndex(Integer blade, GaaletBladeTable table) {
		super(blade, table);
	}
	
	public GaalopIndex(String blade, GaaletBladeTable table) {
		super(blade, table);
	}	
	
	public GaaletIndex getGaaletIndex() {
		return table.gaalopToGaalet(this);
	}
}
