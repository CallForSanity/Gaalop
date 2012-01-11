package de.gaalop.codegen_verilog.VerilogIR;






public class FPValue {

	private int fractionlength;
	private int wordlength;
	private int binarypointposition;
	private long binaryvalue;
	private long one = 1;
	private long signcheck = one << 63;
	private boolean negative;
	
	
	
	
	
	
	
	





	public FPValue(double f, int length, int fraction) {
		this.fractionlength = fraction + 1;
		this.wordlength = length;
		this.binarypointposition = fraction + 1;

		long l = (long) (f * Math.pow(2, fraction + 1));

		// SignBit ueberpruefen
		
		if ((signcheck & l) >>> 63 == one) {
			negative = true;
		} else {
			negative = false;
		}

		// genauigkeit vor dem Komma reduzieren

		long sl = l << (63 - (length));

		// sign bit wieder setzen
		if (negative == false ) {
			binaryvalue = sl >>> (63 - (length));
		} else {
			// sl = sl >>> (1);
			sl = (sl >>> 1 | signcheck);
			binaryvalue = sl >> (63 - (length) - 1);

		}

	}


//	public FPValue(long l, int length, int fraction){}
 
	
	
	
	
	
//	public FPValue add (FPValue fp){
//		if (this.getBinarypointposition() != fp.getBinarypointposition())
//		{
//			if(this.getBinarypointposition()<fp.getBinarypointposition()) {
//				binaryvalue =  binaryvalue  << (fp.getBinarypointposition()-getBinarypointposition()) ;
//				binarypointposition = binarypointposition +	(fp.getBinarypointposition()-getBinarypointposition());	
//				fractionlength = fractionlength + (fp.getBinarypointposition()-getBinarypointposition());
//				wordlength = wordlength + (fp.getBinarypointposition()-getBinarypointposition());
//				
//			}
//				
//			
//		}
//		
//		
//		
//		long result = this.binaryvalue + fp.getBinaryvalue();
//		
//		return null;
//		
//		
//		
//		
//		
//		
//	}
//	
	 
	
	
	
	
	
		
	public double getFloat() {
		return (double) (binaryvalue / Math.pow(2, fractionlength));
	}

	
	

	public long getBinaryvalue64() {
		return binaryvalue;
	}
	public int getBinaryvalue32() {
		return (int) binaryvalue;
	}

	
	public String getHexString32() {
		int r =this.getBinaryvalue32();
		return Integer.toHexString(r);
	}
	
	public String getHexString64() {
		return Long.toHexString(binaryvalue);
	}

	
	
	
	public void setBinaryvalue(long binaryvalue) {
		this.binaryvalue = binaryvalue;
	}

	

	
	public String getFullString() {
		return getBitSubString(binaryvalue, fractionlength, 63) + " . "
				+ getBitSubString(binaryvalue, 0, fractionlength - 1);

	}

	public String getBitSubString(long value, int pos1, int pos2) {
		long t = 1;
		long displayMask = t << (pos2);

		StringBuffer buf = new StringBuffer(100);

		for (int c = pos1 ; c <= pos2; c++) {
			buf.append((value & displayMask) == 0 ? '0' : '1');
			// value <<= 1;
			displayMask >>>= 1;
			if (c % 8 == 0)
				buf.append(' ');
		}

		return buf.toString();
	}


	public int getFractionlength() {
		return fractionlength;
	}









	public void setFractionlength(int fractionlength) {
		this.fractionlength = fractionlength;
	}









	public int getWordlength() {
		return wordlength;
	}









//	public void setWordlength(int wordlength) {
//		this.wordlength = wordlength;
//	}









	public int getBinarypointposition() {
		return binarypointposition;
	}









	public void setBinarypointposition(int binarypointposition) {
		this.binarypointposition = binarypointposition;
	}









	public boolean isNegative() {
		return negative;
	}









//	public void setNegative(boolean negative) {
//		this.negative = negative;
//	}




}
