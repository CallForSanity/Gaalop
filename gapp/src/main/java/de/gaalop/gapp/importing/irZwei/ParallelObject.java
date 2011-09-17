package de.gaalop.gapp.importing.irZwei;

/**
 *
 * @author Christian Steinmetz
 */
public abstract class ParallelObject {

    public abstract Object accept(ParallelObjectVisitor visitor, Object arg);

}
