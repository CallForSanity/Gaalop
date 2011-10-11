package de.gaalop.gapp.importing.parallelObjects;

/**
 * Declares an abstract ParallelObject
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

    /**
     * Inverts the negate attribute
     */
    public void negate() {
        negated = !negated;
    }

    @Override
    public abstract String toString();

    /**
     * Returns if this ParallelObject is a terminal.
     * A terminal has no children.
     * @return
     */
    public abstract boolean isTerminal();
}
