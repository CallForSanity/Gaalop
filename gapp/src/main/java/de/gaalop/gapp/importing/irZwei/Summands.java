package de.gaalop.gapp.importing.irZwei;

import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class Summands extends ParallelObject {

    private LinkedList<SignedSummand> summands;

    public Summands() {
        summands = new LinkedList<SignedSummand>();
    }

    public Summands(LinkedList<SignedSummand> summands) {
        this.summands = summands;
    }

    public LinkedList<SignedSummand> getSummands() {
        return summands;
    }

    public void setSummands(LinkedList<SignedSummand> summands) {
        this.summands = summands;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitSummands(this, arg);
    }

}
