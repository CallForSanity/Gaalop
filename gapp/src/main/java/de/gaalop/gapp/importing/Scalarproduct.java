package de.gaalop.gapp.importing;

import java.util.LinkedList;

/**
 * Declares a product of ParallelVector instances
 * @author Christian Steinmetz
 */
public class Scalarproduct {

    private LinkedList<ParallelVector> vectors;

    public Scalarproduct() {
        vectors = new LinkedList<ParallelVector>();
    }

    public Scalarproduct(LinkedList<ParallelVector> vectors) {
        this.vectors = vectors;
    }

    public LinkedList<ParallelVector> getVectors() {
        return vectors;
    }

    public void setVectors(LinkedList<ParallelVector> vectors) {
        this.vectors = vectors;
    }



}
