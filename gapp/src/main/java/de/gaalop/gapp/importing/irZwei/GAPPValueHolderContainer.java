package de.gaalop.gapp.importing.irZwei;

import de.gaalop.gapp.variables.GAPPValueHolder;

/**
 *
 * @author Christian Steinmetz
 */
public class GAPPValueHolderContainer extends ParallelObject {

    private GAPPValueHolder gappValueHolder;

    public GAPPValueHolderContainer(GAPPValueHolder gappValueHolder) {
        this.gappValueHolder = gappValueHolder;
    }

    public GAPPValueHolder getGappValueHolder() {
        return gappValueHolder;
    }

    public void setGappValueHolder(GAPPValueHolder gappValueHolder) {
        this.gappValueHolder = gappValueHolder;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitGAPPValueHolderContainer(this, arg);
    }

}
