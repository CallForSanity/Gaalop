package de.gaalop.gapp.importing.parallelObjects;

import java.util.LinkedList;

/**
 * Represents a product of ParallelObjects
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

    /**
     * Adds a factor to the factors list
     * @param factor The factor to be added
     */
    public void add(ParallelObject factor) {
        factors.add(factor);
    }

    /**
     * Adds all factors of a product to the factors list
     * @param product The product
     */
    public void add(Product product) {
        this.factors.addAll(product.getFactors());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder((isNegated() ? "!" : "") + "(");
        for (ParallelObject obj: factors) {
            sb.append(obj.toString());
            sb.append(" *");
        }

        if (factors.size() >= 1)
            sb.delete(sb.length()-2, sb.length());

        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean isTerminal() {
        return false;
    }


    

}
