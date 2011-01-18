package de.gaalop.gaalet;

import java.util.List;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;

public class FunctionHelper {

	/**
	 * We dont know what how to handle functions. They can come from external libraries or not. 
	 * This function shall give a generic approach: It just creates a String out of the function call and 
	 * says it is a variable.
	 * @param name
	 * @param args
	 * @return 
	 */
    public static Expression getFunctionExpression(String name, List <Expression> args) {
		String argsText = new String();
		boolean onePlus = false;
		
		for (Expression exp : args) {
			argsText+=exp.toString();
			argsText+=", ";
			onePlus = true;
		}
		
		if (onePlus)
		  return new Variable(name+"("+ argsText.substring(0, argsText.length()-2) +")");
		
		return new Variable(name+"( )");
	}
	
	
}
