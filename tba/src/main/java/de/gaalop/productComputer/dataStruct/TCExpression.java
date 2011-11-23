package de.gaalop.productComputer.dataStruct;

import de.gaalop.productComputer.dataStruct.visitor.TCExpressionVisitor;

/**
 * Represents an expression
 * @author Christian Steinmetz
 */
public abstract class TCExpression {

    /**
     * Returns if this expression is composed of at least two objects
     * @return a boolean value
     */
    public abstract boolean isComposite();

    /**
     * Returns a deep copy of this expression
     * @return The copy
     */
    public abstract TCExpression copyExpression();

    /**
     * Accept method in the visitor pattern
     * @param visitor The visitor to use
     * @param arg The optional argument
     */
    public abstract Object accept(TCExpressionVisitor visitor, Object arg);

}
