package de.gaalop.tba;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;
import java.util.Arrays;
import java.util.Vector;

/**
 * Defines a blade with a sign and a coefficient
 * @author Christian Steinmetz
 */
public class Blade {

	private Algebra algebra;
	
	private Vector<String> bases;
	
	public Blade(Algebra algebra) {
		this.algebra = algebra;
		bases = new Vector<String>();
	}

        public Blade(Algebra algebra, Vector<String> bases) {
		this.algebra = algebra;
		this.bases = bases;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String b: bases) 
                    sb.append("^"+b);
                if (bases.size()>0)
                   return sb.substring(1);
                else
                    return "";
	}

	public void addBasis(String toAdd) {
		bases.add(toAdd);
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


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Blade) {
			Blade comp = (Blade) obj;
			return Arrays.equals(comp.getIndicesArray(), getIndicesArray());
		}
		return false;
	}
	
	public Vector<String> getBases() {
		return bases;
	}

        /**
         * Creates an expression of this blade
         * @return The expression
         */
        public Expression getExpression() {

            if (bases.size()>=1) {
                Expression result = getBaseVector(bases.get(0));
                for (int i=1;i<bases.size();i++)
                    result = ExpressionFactory.wedge(result, getBaseVector(bases.get(i)));
                return result;
            } else {
                throw new IllegalStateException("Blade: Blade contains no base element!");
            }

            
        }

        /**
         * Converts a string into either an BaseVector or FloatConstant
         * @param string The string
         * @return The converted string
         */
        private Expression getBaseVector(String string) {
            if (string.equals("1"))
                return new FloatConstant(1);
            else
                return new BaseVector(string.substring(1));

        }
	
}
