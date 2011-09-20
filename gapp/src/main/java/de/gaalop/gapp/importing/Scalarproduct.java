package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import java.util.LinkedList;

/**
 * Declares a product of ParallelObject instances
 * @author Christian Steinmetz
 */
public class Scalarproduct {

    private LinkedList<ParallelObject> objects;

    public Scalarproduct() {
        objects = new LinkedList<ParallelObject>();
    }

    public Scalarproduct(LinkedList<ParallelObject> vectors) {
        this.objects = vectors;
    }

    public LinkedList<ParallelObject> getObjects() {
        return objects;
    }

    public void setObjects(LinkedList<ParallelObject> objects) {
        this.objects = objects;
    }



}
