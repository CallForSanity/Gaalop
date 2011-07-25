package de.gaalop.tba;

import de.gaalop.common.BubbleSort;
import java.util.Arrays;
import java.util.Vector;

/**
 * Defines a blade with a sign and a coefficient
 * @author christian
 */
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

        /**
         * Parses a string and returns the parsed blade
         * @param toParse The string to parse
         * @param algebra The Algebra to use
         * @return The parsed blade
         */
	public static Blade parseStr(String toParse, Algebra algebra) {
		 Blade result = new Blade(algebra);

                 result.coefficient = 1;
                 result.setSign((byte) 1);

		 if (toParse.equals("0")) {
			 result.addBasis("1");
			 result.coefficient = 0;
			 return result;
		 }

		  String[] parts = toParse.split("\\^");

		  if (parts[0].startsWith("-1")) {
		    result.setSign((byte) -1);
		    parts[0] = parts[0].substring(2);
		  } else {
		    if (parts[0].startsWith("1")) {
		       result.setSign((byte) 1);
		       if (!parts[0].equals("1")) {
		    	   parts[0] = parts[0].substring(1);
		       }
		    } else 
                        if (parts[0].startsWith("e")) {
                            //implicit 1
                            result.setSign((byte) 1);
                        } else if (parts[0].startsWith("-e")) {
                            //implicit -1
                            result.setSign((byte) -1);
                            parts[0] = parts[0].substring(1);
                        } else {
                            String floatStr = parts[0].split("e")[0];
                            float value = Float.parseFloat(floatStr);

                            if (value<0)
                                result.setSign((byte) -1);
                            else
                                result.setSign((byte) 1);

                            result.coefficient = Math.abs(value);
                            if (parts[0].contains("e")) {
                                parts[0] = parts[0].substring(parts[0].indexOf("e"));
                            } else {
                                parts[0] = "";
                            }
                        }
		  }
		  
		  for (String part: parts) {
			if (part.isEmpty()) {
				result.addBasis("1");
			} else 
				result.addBasis((part.equals("e")) ? "einf" : part);
		  }
		  
		  result.makeCanonicial();
		  
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

        /**
         * Returns the indices of the base elements in this blade
         * @return The indices
         */
	private int[] getIndicesArray() {
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

        /**
         * Makes this blade canonical
         */
	private void makeCanonicial() {
		String[] base = algebra.getBase();
		
		int[] basesInt = getIndicesArray();
		
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
		makeCanonicial();
		if (obj instanceof Blade) {
			Blade comp = (Blade) obj;
			comp.makeCanonicial();
			return Arrays.equals(comp.getIndicesArray(), getIndicesArray());
		}
		return false;
	}
	
	public Vector<String> getBases() {
		return bases;
	}
	
}
