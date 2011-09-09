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
 * @author Christian Steinmetz
 */
public class Algebra {

	private String[] base;
	private Vector<Blade> blades;
        
	
	public Algebra() {
		blades = new Vector<Blade>();
	}
	
	public Algebra(String filename_blades, boolean useAsRessource) {
		blades = new Vector<Blade>();
                try {
                    load(filename_blades, useAsRessource);
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
         * Loads blades from a file, which is a ressource
         * @param filename_blades The filename of the file
         * @param useAsRessource true, if filename_products is a ressource
         * @throws IOException
         */
	public void load(String filename_blades, boolean useAsRessource) throws IOException {
            InputStream resourceAsStream;
            if (useAsRessource)
                resourceAsStream = getClass().getResourceAsStream(filename_blades);
            else
                resourceAsStream = new FileInputStream(new File(filename_blades));

            BufferedReader d = new BufferedReader(new InputStreamReader(resourceAsStream));

            String readed = d.readLine();

            base = readed.split(";");
            

            int line = 0;
            while (d.ready()) {
                readed = d.readLine();
                Blade b = Blade.parseStr(readed,this);
                setBlade(line,b);

                line++;
            }


            d.close();
	}

    public int getBaseCount() {
        return base.length-1;
    }
	
}
