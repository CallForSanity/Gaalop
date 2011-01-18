package de.gaalop.gaalet;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.gaalop.dfg.Expression;

/**
 * 
 * It also sorts the Gaalet Blades when returning the definition.
 * @author thomaskanold
 *
 */
public class GaaletMultiVector {

	public 
	LinkedHashMap<Integer, Expression>  gaalopBlades; // blade number, value
	String name;
	
	public GaaletMultiVector(String name) {
		this.name = name;
		gaalopBlades = new LinkedHashMap<Integer,Expression>();
	}
	
	public String getDefinition() {
		Map<Integer, Expression> list = getSortedGaaletBlades();
		
		String result = " ";
		
		//create the hex string out of that list.	
		
		Set<Integer> Gaalet= list.keySet();
		Iterator<Integer> it = Gaalet.iterator();

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
		int GaaletBlade = GaaletBladeTable.gaalopToGaalet(gaalopBlade);
	    int index = 0;
		for (Entry<Integer, Expression> newBlade: getSortedGaaletBlades().entrySet()) {
			if (newBlade.getKey() == GaaletBlade)
				return index;
			index ++;
		}
		return -1; 
	};
	
	public void addComponent(int GaalopBlade) {
		gaalopBlades.put(new Integer(GaalopBlade),null);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GaaletMultiVector) 
			return name.equals(((GaaletMultiVector) obj).getName());
		else
			return false;
	}
	
	public String getName(){
		return name;
	}
	
	private Map<Integer,Expression>  getSortedGaaletBlades() {
		Map<Integer,Expression> GaaletList = getUnsortedGaaletBlades();
			
		List list = new LinkedList(GaaletList.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer,Expression>>() {
			@Override
			public int compare(final Entry<Integer, Expression> o1,
					final Entry<Integer, Expression> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		
		Map<Integer,Expression> sorted = new LinkedHashMap<Integer, Expression>();
		
		for (Iterator it = list.iterator(); it.hasNext(); ) {
			Map.Entry <Integer,Expression> entry = (Map.Entry <Integer,Expression>)it.next();
			sorted.put(entry.getKey(),entry.getValue());
		}
		
		return sorted;
	}

	public void addGaaletBlades(String blade) {
			int gaalop = GaaletBladeTable.numberToBlade(blade);
			addComponent(gaalop);
	}
	
	private Map<Integer,Expression> getUnsortedGaaletBlades(){
		LinkedHashMap<Integer, Expression>  GaaletList = new LinkedHashMap<Integer,Expression>();
		
		for (Entry<Integer, Expression> newBlade : gaalopBlades.entrySet() ) {
			GaaletList.put(GaaletBladeTable.gaalopToGaalet(newBlade.getKey()),newBlade.getValue());
		}
		
		return GaaletList;
	}
	
	/**
	 * retrieve the blade out of the array index.
	 * @param index position 
	 * @return gaalop blade from a GaaletMultivector  array index
	 */
	public int get(int index) {

		Set<Integer> list=gaalopBlades.keySet();
		return (Integer) list.toArray()[index];
	}

	public void addGaaletTupel(Integer GaaletBlade, Expression value) {
		gaalopBlades.put(GaaletBlade, value); 
		
	}

	
	public Expression getExpression() {
		ExpressionCreator expCr = new ExpressionCreator();
		return expCr.createExpression(this);
	}

	/**
	 * Return the GaaletBladeTable for this multivector
	 * atm this is a 5D Conformal vector. 
	 * @return
	 */
	public GaaletBladeTable getTable() {

		return new GaaletBladeTable();
	}
	
 }
