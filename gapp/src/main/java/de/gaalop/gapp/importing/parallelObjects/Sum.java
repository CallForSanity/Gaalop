package de.gaalop.gapp.importing.parallelObjects;

import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class Sum extends ParallelObject {

    private LinkedList<SignedSummand> summands;

    public Sum() {
        summands = new LinkedList<SignedSummand>();
    }

    public Sum(LinkedList<SignedSummand> summands) {
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
        return visitor.visitSum(this, arg);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (SignedSummand obj: summands) {
            sb.append(obj.toString());
            sb.append(" +");
        }

        if (summands.size() >= 1)
            sb.delete(sb.length()-2, sb.length());

        sb.append(")");
        return sb.toString();
    }

}
