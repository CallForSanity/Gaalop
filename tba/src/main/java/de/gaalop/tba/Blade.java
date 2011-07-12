package de.gaalop.tba;

import java.util.Arrays;
import java.util.Vector;

public class Blade {

	private Algebra algebra;
	
	private Vector<String> bases;
	private byte sign;
	public double coefficient;
	
	public Blade(Algebra algebra) {
		this.algebra = algebra;
		sign = 1;
		bases = new Vector<String>();
		coefficient = 1;
	}
	
	public Blade(Algebra algebra, byte sign, double coeff) {
		this.algebra = algebra;
		this.sign = sign;
		bases = new Vector<String>();
		coefficient = coeff;
	}

	public static Blade parseStr(String toParse, Algebra algebra) {
		 Blade result = new Blade(algebra);
		 if (toParse.equals("0")) {
			 result.addBasis("1");
			 result.coefficient = 0;
			 return result;
		 }
		 
		  String[] parts = toParse.split("\\^");
		  //TODO chs Attention maybe switching to correct coefficent
		  if (parts[0].startsWith("-1")) {
		    result.setSign((byte) -1);
		    parts[0] = parts[0].substring(2);
		  } else {
		    if (parts[0].startsWith("1")) {
		       result.setSign((byte) 1);
		       if (!parts[0].equals("1")) {
		    	   parts[0] = parts[0].substring(1);
		       }
		    }
		  }
		  
		  for (String part: parts) {
			if (part.isEmpty()) {
				result.addBasis("1");
			} else 
				result.addBasis((part.equals("e")) ? "einf" : part);
		  }
		  
		  result.makeCanoncial();
		  
		  return result;

	}
	
	public double getValue() {
		return sign*coefficient;
	}
	
	@Override
	public String toString() {
		if (coefficient < 10E-7) return "0";
		String t = (Math.abs(coefficient-1) < 10E-7) ?((sign==1) ? "" : "-") : getValue()+"*";
		
		StringBuilder sb = new StringBuilder();
		for (String b: bases) 
			sb.append("^"+b);
		
		return t+sb.substring(1);
	}
	
	

	public void addBasis(String toAdd) {
		bases.add(toAdd);
	}

	private void setSign(byte sign) {
		this.sign = sign;		
	}
	
	private int[] getBasisArr() {
		String[] base = algebra.getBase();
		
		//determine to each contained base the basis index
		int[] basesInt = new int[bases.size()];
		for (int i=0;i<basesInt.length;i++) {
			String curItem = bases.get(i);
			
			boolean valid = false;
			
			for (int j=0;j<base.length;j++) 
				if (base[j].equals(curItem)) {
					valid = true;
					basesInt[i] = j;
				}
			
			if (!valid) {
				System.err.println("Fault Base "+curItem);
			}
		}
		
		return basesInt;
	}
	
	private void makeCanoncial() {
		String[] base = algebra.getBase();
		
		int[] basesInt = getBasisArr();
		
		//doBubbleSort on basesInt and count the exchanges
		int numOfChanges = BubbleSort.doBubbleSort(basesInt);
		
		bases.clear();
		
		//update object members
		for (int i=0;i<basesInt.length;i++)
			bases.add(base[basesInt[i]]);
		
		if (numOfChanges % 2 != 0) {
			sign *= -1; //invert sign
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		makeCanoncial();
		if (obj instanceof Blade) {
			Blade comp = (Blade) obj;
			comp.makeCanoncial();
			return Arrays.equals(comp.getBasisArr(), getBasisArr());
		}
		return false;
	}
	
	public Vector<String> getBases() {
		return bases;
	}
	
}
