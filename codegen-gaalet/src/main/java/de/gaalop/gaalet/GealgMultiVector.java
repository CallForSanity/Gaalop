package de.gaalop.gealg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.gaalop.dfg.Addition;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.BinaryOperation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;

public class GealgMultiVector {

	public 
	LinkedHashMap<Integer, Expression>  gaalopBlades; // blade number, value
	String name;
	
	public GealgMultiVector(String name) {
		this.name = name;
		gaalopBlades = new LinkedHashMap<Integer,Expression>();
	}
	
	public String getDefinition() {
		Map<Integer, Expression> list = getSortedGealgBlades();
		
		String result = " ";
		
		//create the hex string out of that list.	
		
		Set<Integer> gealg= list.keySet();
		Iterator<Integer> it = gealg.iterator();

		while (it.hasNext()) {
			int val = it.next();
			if (val < 16)
				result += "0x0";
			else 	
				result += "0x";
			result +=  Integer.toHexString(val);
			if (it.hasNext())
			result += ", ";
		}

		return result;
	};
	
	/**
	 * We save the multivector in map. 
	 * The key is the blade, and expression is the value that blade was asserted. 
	 * 
	 * Basically you get all information you need through this function.
	 */
	public Map<Integer, Expression> getGaalopBlades() {
		return gaalopBlades;
	}
	
	public int getBladePosInArray(int gaalopBlade) {
		int gealgBlade = GealgBladeTable.gaalopToGaalet(gaalopBlade);
	    int index = 0;
		for (Entry<Integer, Expression> newBlade: getSortedGealgBlades().entrySet()) {
			if (newBlade.getKey() == gealgBlade)
				return index;
			index ++;
		}
		return -1; 
	};
	
	public void addComponent(int GaalopBlade) {
		
//		for (int gaalop : gaalopBlades) { //no blade doubled
//			if (gaalop == GaalopBlade)
//				return;
//		}
		//if (gaalopBlades.get(GaalopBlade) != null)
		//	return; // no doubles
		gaalopBlades.put(new Integer(GaalopBlade),null);
		//gaalopBlades.put(new Integer(GaalopBlade+1),0.0f);		
		//System.out.println(this.name + " " + GaalopBlade + " assigned  size: " + gaalopBlades.toString());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GealgMultiVector) 
			return name.equals(((GealgMultiVector) obj).getName());
		else
			return false;
	}
	
	public String getName(){
		return name;
	}
	
	private Map<Integer,Expression>  getSortedGealgBlades() {
		Map<Integer,Expression> gealgList = getUnsortedGealgBlades();
			
//		Collections.sort(gealgList);
//		Collections.reverse(gealgList);
		return gealgList;
	}

	public void addGealgBlades(String blade) {
		//for (int n = 0; n < blades.length(); n=n+2) {

			int gaalop = GealgBladeTable.numberToBlade(blade);
			
			//System.out.println(this.name + " " + blades.substring(n, n+2) + " becomes:" + new Integer(gaalop));
			addComponent(gaalop);
		//}
		
	}
	
	public Map<Integer,Expression> getUnsortedGealgBlades(){
		Map<Integer, Expression>  gealgList = new LinkedHashMap<Integer,Expression>();
		
		for (Entry<Integer, Expression> newBlade : gaalopBlades.entrySet() ) {
			gealgList.put(GealgBladeTable.gaalopToGaalet(newBlade.getKey()),newBlade.getValue());
		}
		return gealgList;
	}
	
	public Map<Integer, Expression>  getUnsortedGaalopBlades(){
		return gaalopBlades;
	}
	
	/**
	 * retrieve the blade out of the array index.
	 * @param index position 
	 * @return gaalop blade from a GaaletMultivector  array index
	 */
	public int get(int index) {

		Set<Integer> list=gaalopBlades.keySet();
		System.out.println(gaalopBlades.size());
		return (Integer) list.toArray()[index];
	}

	public void addGealgTupel(Integer gealgBlade, Expression value) {
		gaalopBlades.put(gealgBlade, value); //Fixme, cant add gealg int to gaalop map
		
	}

	
	public Expression getExpression() {
		System.out.println(gaalopBlades);
		ExpressionCreator expCr = new ExpressionCreator();
		return expCr.createExpression(this);
	}

	/**
	 * Return the GealgBladeTable for this multivector
	 * atm this is a 5D Conformal vector. 
	 * @return
	 */
	public GealgBladeTable getTable() {

		return new GealgBladeTable();
	}
	
 }
