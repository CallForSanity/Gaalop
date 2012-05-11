package de.gaalop.gapp;

/**
 * Represents an argument of setVector which can be either a constant,
 * a multivector component list, or a vector component list
 * @author Christian Steinmetz
 */
public abstract class SetVectorArgument {

    /**
     * Returns, if this argument is a constant
     * @return Is this argument a constant?
     */
    public abstract boolean isConstant();

}
