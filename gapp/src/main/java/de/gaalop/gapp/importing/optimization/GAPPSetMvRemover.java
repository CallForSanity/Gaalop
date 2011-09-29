package de.gaalop.gapp.importing.optimization;

import de.gaalop.gapp.instructionSet.GAPPSetMv;
import java.util.HashSet;

/**
 * Removes a given list of GAPPSetMv instructions
 * @author Christian Steinmetz
 */
public class GAPPSetMvRemover extends GAPPRemover {

    private HashSet<GAPPSetMv> toRemove;

    public GAPPSetMvRemover(HashSet<GAPPSetMv> toRemove) {
        this.toRemove = toRemove;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        if (toRemove.contains(gappSetMv)) 
            return true;
        
        return super.visitSetMv(gappSetMv, arg);
    }

}
