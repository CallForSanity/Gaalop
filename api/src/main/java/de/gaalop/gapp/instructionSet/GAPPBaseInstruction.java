package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Represents a base command from the GAPP IR.
 * @author christian
 */
public abstract class GAPPBaseInstruction {

    /**
     * This method must be implemented by all subtypes.
     * It is a member of the Visitor pattern.
     * This method must call the congruous method in the given visitor.
     *
     * @param visitor The visitor to be used for calling
     * @param arg The argument used in calls
     */
    public abstract void accept(GAPPVisitor visitor, Object arg);
    
}
