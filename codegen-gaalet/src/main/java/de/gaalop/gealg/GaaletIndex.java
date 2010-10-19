package de.gaalop.gealg;

public class GaaletIndex extends BladeIndex{

	public GaaletIndex(Integer blade, GealgBladeTable table) {
		super(blade, table);
	}
	
	public GaaletIndex(String blade, GealgBladeTable table) {
		super(blade, table);
	}

	public GaalopIndex getGaalopIndex() {
		return table.gaaletToGaalop(this);
	}

}
