package de.gaalop.tba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines an algebra by storing the base elements and the blades
 * @author christian
 */
public class Algebra {

	private String[] base;
	private Vector<Blade> blades;
        private int[] baseSquares;
	
	public Algebra() {
		blades = new Vector<Blade>();
	}
	
	public Algebra(String filename_products, boolean useAsRessource) {
		blades = new Vector<Blade>();
                try {
                    load(filename_products, useAsRessource);
                } catch (IOException ex) {
                    Logger.getLogger(Algebra.class.getName()).log(Level.SEVERE, null, ex);
                }
	}
	
	public Vector<Blade> getBlades() {
		return blades;
	}
	
	public Blade getBlade(int index) {
		return blades.get(index);
	}
	
	public void setBlade(int index, Blade bladeExpr) {
		if (index>blades.size()-1) blades.setSize(index+1);
		blades.set(index, bladeExpr);
	}

        public int[] getBaseSquares() {
            return baseSquares;
        }

        /**
         * Returns the index to a given blade
         * @param bladeExpr The blade to be searched
         * @return The index of the blade
         */
	public int getIndex(Blade bladeExpr) {
		if (bladeExpr.getBases().isEmpty()) return 0;
		for (int i=0;i<blades.size();i++)
			if (blades.get(i).equals(bladeExpr)) 
				return i;
		return -1;
	}

	public String[] getBase() {
		return base;
	}

	public void setBase(String[] base) {
		this.base = base;
	}

        /**
         * Loads products from a file, which is a ressource
         * @param filename_products The filename of the file
         * @param useAsRessource true, if filename_products is a ressource
         * @throws IOException
         */
	public void load(String filename_products, boolean useAsRessource) throws IOException {
            InputStream resourceAsStream;
            if (useAsRessource)
                resourceAsStream = getClass().getResourceAsStream(filename_products);
            else
                resourceAsStream = new FileInputStream(new File(filename_products));

            BufferedReader d = new BufferedReader(new InputStreamReader(resourceAsStream));

            String readed = d.readLine();

            base = readed.split(";");

            readed = d.readLine();

            String[] baseSquaresStr = readed.split(";");
            baseSquares = new int[baseSquaresStr.length];
            for (int i=0;i<baseSquaresStr.length;i++)
                baseSquares[i] = Integer.parseInt(baseSquaresStr[i].trim());


            int line = 0;
            while (d.ready()) {
                readed = d.readLine();
                Blade b = Blade.parseStr(readed,this);
                setBlade(line,b);

                line++;
            }


            d.close();
	}
	
}
