package de.gaalop.tba;

import de.gaalop.tba.Multivector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class MultTableLoader {
	
	private class ReplaceString {
		public String regex;
		public String replacement;
		
		public ReplaceString(String regex,String replacement) {
			this.regex = regex;
			this.replacement = replacement;
		}
	}
	
	/**
	 * Splittet einen String anhand des Separators und berücksichtigt
	 * zusammenhängende Bereiche
	 * 
	 * @param str
	 *            Der String
	 * @param separator
	 *            Der Seperator
	 * @param start
	 *            Startzeichen des zusammenhängenden Bereiches
	 * @param ende
	 *            Endzeichen des zusammenhängenden Bereiches
	 * @return Das nach dem Separator gesplittete Array
	 */
	private static String[] splitSpez(String str, char separator, char start,
			char ende) {
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
				if (start == ende) {
					if (c == start)
						inString = !inString;
				} else {
					if (c == start)
						inString = true;
					if (c == ende)
						inString = false;
				}
			}
		}
		String sub = str.substring(posStart);
		if (sub.startsWith('"'+"") && sub.endsWith('"'+"")) sub = sub.substring(1,sub.length()-1);
		liste.add(sub);

		return liste.toArray(new String[0]);
	}

	
	public void load(IMultTable tableInner,IMultTable tableOuter,IMultTable tableGeo,Algebra algebra,String filename_Products, String filename_Replaces) {
		String[] base = algebra.getBase();
		
		int bladeCount = algebra.getBlades().size();
		tableInner.createTable(bladeCount);
		tableOuter.createTable(bladeCount);
		tableGeo.createTable(bladeCount);
		
		LinkedList<ReplaceString> replaces = new LinkedList<MultTableLoader.ReplaceString>();
		
                InputStream resourceAsStream = getClass().getResourceAsStream(filename_Replaces);


		try {
                    BufferedReader dRepl = new BufferedReader(new InputStreamReader(resourceAsStream));

			
			while (dRepl.ready()) {
				String readed = dRepl.readLine();
				
				String[] parts = splitSpez(readed,';','"','"');
				
				replaces.add(new ReplaceString(parts[0],parts[1]));
				
			}
			
			dRepl.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
		 resourceAsStream = getClass().getResourceAsStream(filename_Products);

                 try {
			BufferedReader d = new BufferedReader(new InputStreamReader(resourceAsStream));
		
		

			
			int line=0;
			while (d.ready()) {
				String readed = d.readLine();
				
				if (line > 0) {
					
					for (ReplaceString curRepl: replaces)
						readed = readed.replaceAll(curRepl.regex, curRepl.replacement);
					
					String[] parts = readed.split(";");

					int index0 = algebra.getIndex(Blade.parseStr(parts[0],algebra));
					int index1 = algebra.getIndex(Blade.parseStr(parts[1],algebra));
				
					tableInner.setProduct(index0,index1,Multivector.parse(parts[2],base,algebra));
					tableOuter.setProduct(index0,index1,Multivector.parse(parts[3],base,algebra));
					tableGeo.setProduct(index0,index1,Multivector.parse(parts[4],base,algebra));
					
				}
				
				line++;
			}
			
			
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//füge auch noch die standard produkte mit 1 ein
		
		for (int i=0;i<bladeCount;i++) {
			Blade blade = algebra.getBlade(i);
			Multivector mvBlade = new Multivector(algebra);
			mvBlade.addBlade(blade);
			
			Multivector mvNull = new Multivector(algebra);
			
			tableInner.setProduct(0, i, mvNull);
			tableInner.setProduct(i, 0, mvNull);
			tableOuter.setProduct(0, i, mvBlade);
			tableOuter.setProduct(i, 0, mvBlade);
			tableGeo.setProduct(0, i, mvBlade);
			tableGeo.setProduct(i, 0, mvBlade);
			
		}
		/*
		Multivector mv = new Multivector(algebra);
		mv.addBlade(algebra.getBlade(0));
		tableInner.setProduct(0, 0, mv);
		*/
	}
	
}
