package de.gaalop.gapp;

import de.gaalop.gapp.variables.GAPPSetOfVariables;

/**
 * Stores a setOfVariable conntainer and a number of indices
 * @author Christian Steinmetz
 */
public class PairSetOfVariablesAndIndices extends SetVectorArgument {

    private GAPPSetOfVariables setOfVariable;
    private Selectorset selectors;

    public PairSetOfVariablesAndIndices(GAPPSetOfVariables setOfVariable, Selectorset selectors) {
        this.setOfVariable = setOfVariable;
        this.selectors = selectors;
    }

    public Selectorset getSelectors() {
        return selectors;
    }

    public GAPPSetOfVariables getSetOfVariable() {
        return setOfVariable;
    }

    public void setSelectors(Selectorset selectors) {
        this.selectors = selectors;
    }

    public void setSetOfVariable(GAPPSetOfVariables setOfVariable) {
        this.setOfVariable = setOfVariable;
    }

    @Override
    public String toString() {
        return setOfVariable.toString()+selectors.toString();
    }

    @Override
    public boolean isConstant() {
        return false;
    }

}
