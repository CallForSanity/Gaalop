package de.gaalop.gealg;

public class BladeIndex {

	protected Integer blade;
	protected GealgBladeTable table;
	
	BladeIndex(Integer blade, GealgBladeTable table) {
		this.blade = blade;
		this.table = table;
	}

	BladeIndex(String blade, GealgBladeTable table) {
		this.table = table;
		set(blade);
	}	
	
	public void set(Integer bladeIndex) {
		this.blade = bladeIndex;
	}
	
	
	/**
	 * Accepts decimal or hex string.
	 * @param bladeIndex hex must be of this form "0x01".
	 */
	public void set(String bladeIndex) {
		String hex = bladeIndex;
		
		if ((hex.length()>1)&&(hex.charAt(1) =='x')) {
			hex = hex.substring(2);
			blade = Integer.valueOf(hex,16);
			System.out.println("BladeIndex: " +blade);			
		} else // it is not a hex
		{
			blade = new Integer(bladeIndex);
			System.out.println("BladeIndex: " +blade);
		};
	}
	
	public Integer get() {
		return blade;
	}
	
	public String getHexString() {
		String result = new String();
		if (blade < 16)
			result += "0x0";
		else 	
			result += "0x";
		result +=  Integer.toHexString(blade);
		
		return result;
	}
}
