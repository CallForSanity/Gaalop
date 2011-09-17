package de.gaalop.gapp.importing.irZwei;

import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class Factors extends ParallelObject {

    private LinkedList<ParallelObject> factors;

    public Factors() {
        factors = new LinkedList<ParallelObject>();
    }

    public Factors(LinkedList<ParallelObject> factors) {
        this.factors = factors;
    }

    public LinkedList<ParallelObject> getFactors() {
        return factors;
    }

    public void setFactors(LinkedList<ParallelObject> factors) {
        this.factors = factors;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitFactors(this, arg);
    }

    public void add(ParallelObject factor) {
        factors.add(factor);
    }

    public void add(Factors factors) {
        this.factors.addAll(factors.getFactors());
    }

    

}
