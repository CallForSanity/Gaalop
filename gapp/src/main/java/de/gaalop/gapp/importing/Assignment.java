package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.SignedSummand;
import de.gaalop.gapp.variables.GAPPVariable;
import java.util.HashMap;

/**
 * Represents an assignment of a sum of scalarproducts
 * @author Christian Steinmetz
 */
public class Assignment {

    private GAPPVariable arg;
    private HashMap<SignedSummand, Scalarproduct> summands;

    public Assignment(GAPPVariable arg, HashMap<SignedSummand, Scalarproduct> summands) {
        this.arg = arg;
        this.summands = summands;
    }

    public GAPPVariable getArg() {
        return arg;
    }

    public HashMap<SignedSummand, Scalarproduct> getSummands() {
        return summands;
    }

    public void setArg(GAPPVariable arg) {
        this.arg = arg;
    }

    public void setSummands(HashMap<SignedSummand, Scalarproduct> summands) {
        this.summands = summands;
    }

}
