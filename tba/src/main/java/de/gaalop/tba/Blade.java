package de.gaalop.tba;

import de.gaalop.common.BubbleSort;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;
import java.util.Arrays;
import java.util.Vector;

/**
 * Defines a blade with a sign and a coefficient
 * @author christian
 */
public class Blade {

	private Algebra algebra;
	
	private Vector<String> bases;
	private byte prefactor;
	
	public Blade(Algebra algebra) {
		this.algebra = algebra;
		prefactor = 1;
		bases = new Vector<String>();
	}
	
	public Blade(Algebra algebra, byte sign) {
		this.algebra = algebra;
		this.prefactor = sign;
		bases = new Vector<String>();
	}

        public Blade(Algebra algebra, Vector<String> bases, byte sign) {
		this.algebra = algebra;
		this.prefactor = sign;
		this.bases = bases;
	}

        /**
         * Parses a string and returns the parsed blade
         * @param toParse The string to parse
         * @param algebra The Algebra to use
         * @return The parsed blade
         */
	public static Blade parseStr(String toParse, Algebra algebra) {
		 Blade result = new Blade(algebra);

                 result.setPrefactor((byte) 1);

		 if (toParse.equals("0")) {
			 result.addBasis("1");
			 result.prefactor = 0;
			 return result;
		 }

		  String[] parts = toParse.split("\\^");

                  // parse only prefactor
		  if (parts[0].startsWith("-1")) {
		    result.setPrefactor((byte) -1);
		    parts[0] = parts[0].substring(2);
		  } else {
		    if (parts[0].startsWith("1")) {
		       result.setPrefactor((byte) 1);
		       if (!parts[0].equals("1")) {
		    	   parts[0] = parts[0].substring(1);
		       }
		    } else 
                        if (parts[0].startsWith("e")) {
                            //implicit 1
                            result.setPrefactor((byte) 1);
                        } else if (parts[0].startsWith("-e")) {
                            //implicit -1
                            result.setPrefactor((byte) -1);
                            parts[0] = parts[0].substring(1);
                        } else {
                            String intStr = parts[0].split("e")[0];
                            result.prefactor = (byte) Integer.parseInt(intStr);
                            if (parts[0].contains("e")) {
                                parts[0] = parts[0].substring(parts[0].indexOf("e"));
                            } else {
                                parts[0] = "";
                            }
                        }
		  }

                  //parse only blade
		  for (String part: parts) {
			if (part.isEmpty()) {
				result.addBasis("1");
			} else
                            result.addBasis(part);
				//result.addBasis((part.equals("e")) ? "einf" : part);
		  }
		  
		  result.makeCanonicial();
		  
		  return result;

	}
	
	public double getValue() {
		return prefactor;
	}
	
	@Override
	public String toString() {
		if (prefactor == 0) return "0";
		String t = (Math.abs(prefactor) == 1) ?((prefactor==1) ? "" : "-") : getValue()+"*";
		
		StringBuilder sb = new StringBuilder();
		for (String b: bases) 
			sb.append("^"+b);
		
		return t+sb.substring(1);
	}
	
	

	public void addBasis(String toAdd) {
		bases.add(toAdd);
	}

	private void setPrefactor(byte prefactor) {
		this.prefactor = prefactor;
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
			prefactor *= -1; //invert sign
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

        public Expression getExpression() {

            if (bases.size()>=1) {
                Expression result = getBaseVector(bases.get(0));
                for (int i=1;i<bases.size();i++)
                    result = ExpressionFactory.wedge(result, getBaseVector(bases.get(i)));
                return result;
            } else {
                System.err.println("häh");
                        return null;
            }

            
        }

        private Expression getBaseVector(String baseElement) {
            if (baseElement.equals("1")) 
                return new FloatConstant(1);
            else
                return new BaseVector(baseElement.substring(1));

        }
	
}
