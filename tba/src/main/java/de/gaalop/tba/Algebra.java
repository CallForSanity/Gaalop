package de.gaalop.tba;

import de.gaalop.algebra.TCBlade;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.dfg.Expression;
import java.util.HashMap;
import java.util.Vector;

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
