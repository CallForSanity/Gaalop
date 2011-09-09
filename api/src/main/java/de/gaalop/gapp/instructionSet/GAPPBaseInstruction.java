package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.visitor.PrettyPrint;

/**
 * Represents a base command from the GAPP IR.
 * @author Christian Steinmetz
 */
public abstract class GAPPBaseInstruction {

    /**
     * This method must be implemented by all subtypes.
     * It is a member of the Visitor pattern.
     * This method must call the congruous method in the given visitor.
     *
     * @param visitor The visitor to be used for calling
     * @param arg The argument used in calls
     *
     * @return An result object (null is permitted)
     */
    public abstract Object accept(GAPPVisitor visitor, Object arg);

    @Override
    public String toString() {
        PrettyPrint printer = new PrettyPrint();
        accept(printer, null);
        return printer.getResultString();
    }



}
