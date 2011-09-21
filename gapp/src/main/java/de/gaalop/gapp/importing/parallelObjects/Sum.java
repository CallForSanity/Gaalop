package de.gaalop.gapp.importing.parallelObjects;

import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class Sum extends ParallelObject {

    private LinkedList<ParallelObject> summands;

    public Sum() {
        summands = new LinkedList<ParallelObject>();
    }

    public Sum(LinkedList<ParallelObject> summands) {
        this.summands = summands;
    }

    public LinkedList<ParallelObject> getSummands() {
        return summands;
    }

    public void setSummands(LinkedList<ParallelObject> summands) {
        this.summands = summands;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitSum(this, arg);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder((isNegated() ? "!" : "") + "(");
        for (ParallelObject obj: summands) {
            sb.append(obj.toString());
            sb.append(" +");
        }

        if (summands.size() >= 1)
            sb.delete(sb.length()-2, sb.length());

        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

}
