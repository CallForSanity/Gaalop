package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.SignedSummand;
import java.util.HashMap;

/**
 * Represents an assignment of a sum of scalarproducts
 * @author Christian Steinmetz
 */
public class Assignment {

    private String name;
    private int index;
    private HashMap<SignedSummand, Scalarproduct> summands;

    public Assignment(String name, int index, HashMap<SignedSummand, Scalarproduct> summands) {
        this.name = name;
        this.index = index;
        this.summands = summands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public HashMap<SignedSummand, Scalarproduct> getSummands() {
        return summands;
    }

    public void setSummands(HashMap<SignedSummand, Scalarproduct> summands) {
        this.summands = summands;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name+"["+index+"] = ");

        for (SignedSummand summand: summands.keySet()) {
            if (!summand.isPositiveSigned()) {
                result.append("(-");
                result.append(summands.get(summand).toString());
                result.append(")");
            } else {
                result.append(summands.get(summand).toString());
            }


        }

        return result.toString();
    }



}
