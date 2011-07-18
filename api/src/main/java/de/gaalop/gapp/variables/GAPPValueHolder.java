package de.gaalop.gapp.variables;

/**
 * Very basic abstract class, that declares a value holder class, e.g. Variable or Constant
 * @author christian
 */
public abstract class GAPPValueHolder {

    /**
     * Returns if this class represents a variable
     * @return 
     *  <value>true</value> if this is a variable,
     *  <value>false</value> otherwise (e.g. if this is a constant)
     */
    public abstract boolean isVariable();

    /**
     * Returns a pretty-formed string, which short outlines the contents
     * @return The pretty-formed string
     */
    public abstract String prettyPrint();

    /**
     * Declares the accept method in the visitor pattern.
     * All subclass have to call their own visit method in the given visitor
     * @param visitor The visitor to be used
     * @param arg An optional argument
     * @return The return value of the called visit method
     */
    public abstract Object accept(GAPPVariableVisitor visitor, Object arg);

}
