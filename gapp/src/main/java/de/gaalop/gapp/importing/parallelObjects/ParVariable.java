package de.gaalop.gapp.importing.parallelObjects;

/**
 * Represents a whole multivector
 * @author Christian Steinmetz
 */
public class ParVariable extends ParallelObject {

    private String name;

    public ParVariable(String name) {
        this.name = name;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitVariable(this, arg);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

}
