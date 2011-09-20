package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.SignedSummand;
import de.gaalop.gapp.variables.GAPPMultivector;
import java.util.HashMap;

/**
 * Represents an assignment of a sum of scalarproducts
 * @author Christian Steinmetz
 */
public class Assignment {

    private GAPPMultivector arg;
    private int index;
    private HashMap<SignedSummand, Scalarproduct> summands;

    public Assignment(GAPPMultivector arg, int index, HashMap<SignedSummand, Scalarproduct> summands) {
        this.arg = arg;
        this.index = index;
        this.summands = summands;
    }

    public GAPPMultivector getArg() {
        return arg;
    }

    public HashMap<SignedSummand, Scalarproduct> getSummands() {
        return summands;
    }

    public void setArg(GAPPMultivector arg) {
        this.arg = arg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSummands(HashMap<SignedSummand, Scalarproduct> summands) {
        this.summands = summands;
    }


}
