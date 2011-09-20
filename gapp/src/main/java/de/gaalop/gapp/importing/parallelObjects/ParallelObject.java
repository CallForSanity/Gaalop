package de.gaalop.gapp.importing.parallelObjects;

/**
 *
 * @author Christian Steinmetz
 */
public abstract class ParallelObject {

    private boolean negatedInSum = false;

    public abstract Object accept(ParallelObjectVisitor visitor, Object arg);

    public boolean isNegatedInSum() {
        return negatedInSum;
    }

    public void setNegatedInSum(boolean negatedInSum) {
        this.negatedInSum = negatedInSum;
    }

    @Override
    public abstract String toString();

}
