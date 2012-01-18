package de.gaalop.tba;

import de.gaalop.algebra.TCBlade;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.dfg.Expression;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines an algebra by storing the base elements and the blades
 * @author Christian Steinmetz
 */
public class Algebra {

    private String[] base;
    private Vector<Blade> blades = new Vector<Blade>();
    private HashMap<Blade,Integer> indices = new HashMap<Blade, Integer>();

    private boolean dirty;
    

    public Algebra() {
        dirty = true;
    }

    public Algebra(String filename_blades, boolean useAsRessource) {
        try {
            load(filename_blades, useAsRessource);
        } catch (IOException ex) {
            Logger.getLogger(Algebra.class.getName()).log(Level.SEVERE, null, ex);
        }
        dirty = true;
    }

    public Algebra(String[] base, TCBlade[] blades) {
        this.base = base;
        for (TCBlade b: blades)
            this.blades.add(new Blade(b));
        dirty = true;
    }

    public Algebra(AlgebraDefinitionFile alFile) {
        this.base = alFile.base;
        for (Expression e: alFile.blades) 
            blades.add(Blade.createBladeFromExpression(e));
        dirty = true;
    }
    
    public int getBladeCount() {
        return blades.size();
    }

    public Blade getBlade(int index) {
        if (index < blades.size()) {
            return blades.get(index);
        } else
            return blades.get(0); //TODO chs search usages: inputsVector.bladeIndex can be greater then algebra bladecount
    }

    public void setBlade(int index, Blade bladeExpr) {
        if (index > blades.size() - 1) {
            blades.setSize(index + 1);
        }
        blades.set(index, bladeExpr);
        dirty = true;
    }

    /**
     * Returns the index to a given blade
     * @param bladeExpr The blade to be searched
     * @return The index of the blade
     */
    public int getIndex(Blade bladeExpr) {
        if (dirty) buildMap();
        if (bladeExpr.getBases().isEmpty())
            return 0;
        return indices.get(bladeExpr);
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
        if (useAsRessource) {
            resourceAsStream = getClass().getResourceAsStream(filename_blades);
        } else {
            resourceAsStream = new FileInputStream(new File(filename_blades));
        }

        BufferedReader d = new BufferedReader(new InputStreamReader(resourceAsStream));

        String readed = d.readLine();

        base = readed.split(";");


        int line = 0;
        while (d.ready()) {
            readed = d.readLine();
            Blade b = Parser.parseBlade(readed);
            setBlade(line, b);

            line++;
        }


        d.close();
    }

    /**
     * Returns the number of elements in the base
     * @return The number of base elements
     */
    public int getBaseCount() {
        return base.length - 1;
    }

    public void buildMap() {
        indices.clear();
        int i=0;
        for (Blade b: blades)
            indices.put(b, i++);
        dirty = false;
    }
}
