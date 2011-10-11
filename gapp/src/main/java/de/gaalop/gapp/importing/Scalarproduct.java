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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (ParallelObject obj : objects) {
            sb.append(obj.toString());
            sb.append(" .");
        }

        if (objects.size() >= 1) {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append(")");
        return sb.toString();
    }
}
