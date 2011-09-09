package de.gaalop.common;

import java.util.LinkedList;

/**
 * Provides methods for advanced string handling
 * @author Christian Steinmetz
 */
public class StringMethods {

    /**
	 * Splits a string at a given separator and takes care of connected sequence of chars
         *
	 * @param str
	 *            The string to split
	 * @param separator
	 *            the separator where to split
	 * @param start
	 *            beginning char of a connected sequence of chars
	 * @param end
	 *            ending char of a connected sequence of chars
	 * @return the array which contains the separated sequences of chars
	 */
	public static String[] splitStrIgnoreConnectedSequences(String str, char separator, char start,
			char end) {
		LinkedList<String> liste = new LinkedList<String>();
		char c;
		boolean inString = false;
		int posStart = 0;
		int laenge = str.length();
		for (int i = 0; i < laenge; i++) {
			c = str.charAt(i);
			if (c == separator && !inString) {
				String sub = str.substring(posStart, i);
				if (sub.startsWith('"'+"") && sub.endsWith('"'+"")) sub = sub.substring(1,sub.length()-1);
				liste.add(sub);
				posStart = i + 1;
			} else {
				if (start == end) {
					if (c == start)
						inString = !inString;
				} else {
					if (c == start)
						inString = true;
					if (c == end)
						inString = false;
				}
			}
		}
		String sub = str.substring(posStart);
		if (sub.startsWith('"'+"") && sub.endsWith('"'+""))
                    sub = sub.substring(1,sub.length()-1);
		liste.add(sub);

		return liste.toArray(new String[0]);
	}

}
