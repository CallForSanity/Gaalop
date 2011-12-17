package de.gaalop.gapp.variables;

/**
 * Implements a visitor which copies a GAPPVariable.
 * Simply call the accept method of the GAPPVariable with an instance of this visitor,
 * and a (deep) copy of the variable will be returned.
 * 
 * @author Christian Steinmetz
 */
public class GAPPValueHolderCopier implements GAPPVariableVisitor {

    private GAPPValueHolderCopier() {
    }

    /**
     * Facade method for copying a value holder object
     * @param valueHolder The object to copy
     * @return The copied object
     */
    public static GAPPValueHolder copyValueHolder(GAPPValueHolder valueHolder) {
        GAPPValueHolderCopier visitor = new GAPPValueHolderCopier();
        return (GAPPValueHolder) valueHolder.accept(visitor, null);
    }

    @Override
    public Object visitConstant(GAPPConstant gappConstant, Object arg) {
        return new GAPPConstant(gappConstant.getValue());
    }

    @Override
    public Object visitMultivector(GAPPMultivector gappMultivector, Object arg) {
        return new GAPPMultivector(gappMultivector.getName()); //Strings are immutable!
    }

    @Override
    public Object visitMultivectorComponent(GAPPMultivectorComponent gappMultivectorComponent, Object arg) {
        return new GAPPMultivectorComponent(
                gappMultivectorComponent.getName(),
                gappMultivectorComponent.getBladeIndex()); //Strings are immutable!
    }

    @Override
    public Object visitScalarVariable(GAPPScalarVariable gappScalarVariable, Object arg) {
        return new GAPPScalarVariable(gappScalarVariable.getName()); //Strings are immutable!
    }

    @Override
    public Object visitVector(GAPPVector gappVector, Object arg) {
        return new GAPPVector(gappVector.getName());
    }
}
