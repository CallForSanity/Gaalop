package de.gaalop.gaalet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;

/**
 * So we have a Multivector definition and we want tools to know about it. 
 * What we simply do is, we write it out like:  a = (3*e1 + (2*e2));
 * 
 * @author thomaskanold
 */
public class ExpressionCreator {

	public ExpressionCreator() {

	}
	
	/** recursive function to create  an Expression out of a multivector
	 * 
	 */
	public Expression createExpression(GaaletMultiVector vec){
		
		GaaletBladeTable table = vec.getTable();
		
		if (vec.getGaalopBlades().size() <= 0)
		  return new FloatConstant(0.0f);  //empty vector
		
		Set<Integer> key = vec.getGaalopBlades().keySet();
		
		Set<Expression> expressionBlades = new HashSet<Expression> (); // here we store every blades 
		//multiplication with a base vector.
		
		for (Integer index :key ) { //lets create a set of the basisBlades expressions
			vec.getGaalopBlades().get(key);
			// what we do is: we search in the table for the right BladeExpression and multiplicate
			// it with the value of the blade stored in the multivector.
			//System.out.println(table.getExpression(index)+ "  :: "+vec.getGaalopBlades().get(index));
			expressionBlades.add(new Multiplication(table.getExpression(index),vec.getGaalopBlades().get(index)));
		}
		//this times blade
		return recCreateAddTree(expressionBlades); // add up all expressions
	}

	/**
	 * adds up all expressions in a set.
	 */
	
	private Expression recCreateAddTree( Set<Expression> set){
		Expression exp;
		Iterator <Expression>it = set.iterator();
		if (it.hasNext()){
			exp=it.next();
			if(it.hasNext()){ // next iteration step			
				set.remove(exp);
				return new Addition (exp, recCreateAddTree(set));
			}
			else
				return exp;
		}
		else
			return new FloatConstant(0.0f); //this should never happen	
	}

	
	
}
