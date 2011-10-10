package de.gaalop.gapp.variables;

import java.util.LinkedList;

/**
 * Implements a visitor which copies a GAPPVariable.
 * Simply call the accept method of the GAPPVariable with an instance of this vistor,
 * and returned is a (deep) copy of the variable.
 * 
 * @author Christian Steinmetz
 */
public class GAPPValueHolderCopier implements GAPPVariableVisitor {

    private GAPPValueHolderCopier() {
    }

    //TODO chs JavaDoc
    public static GAPPValueHolder copyValueHolder(GAPPValueHolder valueHolder) {
        GAPPValueHolderCopier visitor = new GAPPValueHolderCopier();
        return (GAPPValueHolder) valueHolder.accept(visitor, null);
    }

    @Override
    public Object visitConstant(GAPPConstant gappConstant, Object arg) {
        return new GAPPConstant(gappConstant.getValue());
    }

    /**
     * Copies an array of blades (deep copy)
     * @param blades The array of blades
     * @return The copied array
     */
    private GAPPValueHolder[] copyBlades(GAPPValueHolder[] blades) {
        GAPPValueHolder[] bladesCopy = new GAPPValueHolder[blades.length];

        for (int blade=0;blade<blades.length;blade++)
            bladesCopy[blade] = (GAPPValueHolder) blades[blade].accept(this, null);

        return bladesCopy;
    }

    @Override
    public Object visitMultivector(GAPPMultivector gappMultivector, Object arg) {
        return new GAPPMultivector(gappMultivector.getName()); //Strings are immutable!
    }

    @Override
    public Object visitMultivectorComponent(GAPPMultivectorComponent gappMultivectorComponent, Object arg) {
        return new GAPPMultivectorComponent(
                gappMultivectorComponent.getName(),
                gappMultivectorComponent.getBladeIndex()
                ); //Strings are immutable!
    }

    @Override
    public Object visitScalarVariable(GAPPScalarVariable gappScalarVariable, Object arg) {
        return new GAPPScalarVariable(gappScalarVariable.getName()); //Strings are immutable!
    }

    /**
     * Copies a list of slots (deep copy)
     * @param slots The list of slots
     * @return The copied list
     */
    private LinkedList<GAPPValueHolder> copySlots(LinkedList<GAPPValueHolder> slots) {
        LinkedList<GAPPValueHolder> result = new LinkedList<GAPPValueHolder>();

        for (GAPPValueHolder slot: slots) 
            result.add((GAPPValueHolder) slot.accept(this, null));

        return result;
    }

    @Override
    public Object visitVector(GAPPVector gappVector, Object arg) {
        return new GAPPVector(gappVector.getName());
    }

}
