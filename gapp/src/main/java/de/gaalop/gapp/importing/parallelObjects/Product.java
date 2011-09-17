package de.gaalop.gapp.importing.parallelObjects;

import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class Product extends ParallelObject {

    private LinkedList<ParallelObject> factors;

    public Product() {
        factors = new LinkedList<ParallelObject>();
    }

    public Product(LinkedList<ParallelObject> factors) {
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
        return visitor.visitProduct(this, arg);
    }

    public void add(ParallelObject factor) {
        factors.add(factor);
    }

    public void add(Product factors) {
        this.factors.addAll(factors.getFactors());
    }

    

}
