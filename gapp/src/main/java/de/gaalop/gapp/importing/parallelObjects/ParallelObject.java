package de.gaalop.gapp.importing.parallelObjects;

/**
 *
 * @author Christian Steinmetz
 */
public abstract class ParallelObject {

    public abstract Object accept(ParallelObjectVisitor visitor, Object arg);

}
