package de.gaalop.gaalet;


/**
 * 
 * Gaalop uses a float array, and gaalet its multivectors which actually are arrays too.
 * What happens at the optimization to Gaalet:
 * We have a GaalopIndex, this index is set. Every blade has its position. This GaalopIndex must 
 * be transformed into a GaaletIndex. 
 * 
 * It was just handier for programming and less confusing.
 * 
 * @author thomaskanold
 *
 */
public class BladeIndex {

	protected Integer blade;
	protected GaaletBladeTable table;
	
	BladeIndex(Integer blade, GaaletBladeTable table) {
		this.blade = blade;
		this.table = table;
	}

	BladeIndex(String blade, GaaletBladeTable table) {
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
		} else // it is not a hex
		{
			blade = new Integer(bladeIndex);
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
