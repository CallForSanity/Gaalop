package de.gaalop.gaalet;

public class GaaletIndex extends BladeIndex{

	public GaaletIndex(Integer blade, GaaletBladeTable table) {
		super(blade, table);
	}
	
	public GaaletIndex(String blade, GaaletBladeTable table) {
		super(blade, table);
	}

	public GaalopIndex getGaalopIndex() {
		return table.gaaletToGaalop(this);
	}

}
