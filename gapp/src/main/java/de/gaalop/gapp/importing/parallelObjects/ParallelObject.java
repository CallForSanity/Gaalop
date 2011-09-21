package de.gaalop.gapp.importing.parallelObjects;

/**
 *
 * @author Christian Steinmetz
 */
public abstract class ParallelObject {

    private boolean negated = false;

    public abstract Object accept(ParallelObjectVisitor visitor, Object arg);

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public void negate() {
        negated = !negated;
    }

    @Override
    public abstract String toString();
    
    public abstract boolean isTerminal();

}
