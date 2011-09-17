/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import java.util.LinkedList;

/**
 * Declares a vector of ParallelObject instances
 * @author Christian Steinmetz
 */
public class ParallelVector {

    private LinkedList<ParallelObject> slots;

    public ParallelVector() {
        slots = new LinkedList<ParallelObject>();
    }

    public ParallelVector(LinkedList<ParallelObject> slots) {
        this.slots = slots;
    }

    public LinkedList<ParallelObject> getSlots() {
        return slots;
    }

    public void setSlots(LinkedList<ParallelObject> slots) {
        this.slots = slots;
    }

}
