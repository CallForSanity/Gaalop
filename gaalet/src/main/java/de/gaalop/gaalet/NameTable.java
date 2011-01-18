package de.gaalop.gaalet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * This class implements singleton pattern.
 * Some C++ variable names are not maple conform. For example "class->var" should throw a syntax error.
 * However 'cause we still want to use those variables we need to replace them in the code we give to maple.
 * NameTable is supposed to be the lookup table for all variables. 
 * @author Thomas Kanold
 *
 */
public class NameTable {

	private static NameTable instance = null;
	
	/**
	 * describes if the name table is used or just the true variable name.
	 */
	private static boolean use; 


	private Map <String, String> table;
	
	private NameTable() {
		table = new HashMap<String, String>();
	}
	
	public static NameTable getInstance() {
		if (instance == null) {
			instance = new NameTable();
		} 
		return instance;
	}
	
	/**
	 * Does the table already has stored the variable.
	 * @param textv True is we already have a key for the variable.
	 * @return 
	 */
	public boolean has(String textv) {
		return table.containsValue(textv);
	}
	
	/**
	 * 
	 * @param textv  The variable name.
	 * @return null means we have no key yet.
	 */
	private String getKey(String textv) {
		String key = null;
		
		for (Entry <String, String> entr: table.entrySet()) {
			if (entr.getValue().equals(textv)) { 
				key = entr.getKey();
				break;
			}
		}
		
		return key;
	}
	
	/**
	 * If the variable name already exists, no new entry will be created.
	 * One variable name has only one entry in the table.
	 * 
	 * @param textv The variable name we want to add to the table
	 * @return
	 */

	public String add(String textv) {
		if (use)
			return addValue(textv);
		return textv;  // we dont want to use NameTable
	}
	
	private String addValue(String textv) {
		String key = getKey(textv);
		if (key==null) {
			key = createKey(textv);
			table.put(key, textv);
		}
		return key;
	}

	/**
	 * If the key is not found in the Table, get will return the key. That way
	 * this function can also be used by other plugins, which haven't stored anything in there.
	 * 
	 * @param key
	 * @return Returns the stored name. If name is not in table it returns the key.
	 */
	public String get(String key) {
		if ((table.get(key) == null) || (!use))
			return key;
		
		return table.get(key);
	}
	
	
	/**
	 * Create a "random" hash name for the HashTable as a key.
	 * @param text
	 * @return
	 */
	public String createKey(String text) {
		Random r = new Random(text.hashCode());
		String prefix = "hash";
		String key = new String();
		key += prefix; 
		while(table.containsKey(key)) {
			key = prefix + Math.abs(r.nextInt());
		}	
		return key;
	}
	
	public static boolean isInUse() {
		return use;
	}

	public static void setUse(boolean use) {
		NameTable.use = use;
	}
}
