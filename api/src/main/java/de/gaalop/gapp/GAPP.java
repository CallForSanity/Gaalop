package de.gaalop.gapp;

import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.visitor.GAPPCopier;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.util.LinkedList;

/**
 * Stores GAPP instructions
 * @author Christian Steinmetz
 *
 */
public class GAPP {

    private LinkedList<GAPPBaseInstruction> instructions;

    /**
     * Creates a new GAPP instance with an empty instruction list
     */
    public GAPP() {
        instructions = new LinkedList<GAPPBaseInstruction>();
    }

    /**
     * Returns a (deep) copy of this instance
     * @return The copy
     */
    public GAPP getCopy() {
        GAPP copy = new GAPP();

        GAPPCopier copier = new GAPPCopier();
        for (GAPPBaseInstruction curInstr: instructions)
            copy.instructions.add((GAPPBaseInstruction) curInstr.accept(copier, null));

        return copy;
    }

    /**
     * Adds a instruction at the end of the current instruction list
     * @param toAdd The instruction to add
     */
    public void addInstruction(GAPPBaseInstruction toAdd) {
        instructions.add(toAdd);
    }

    /**
     * Accept method in the visitor pattern for traversing the GAPP data structure.
     * Calls the accept method of every instruction in this data structure.
     *
     * @param visitor The visitor used for calling
     * @param arg An argument, which will be used in the calls
     */
    public void accept(GAPPVisitor visitor, Object arg) {
        for (GAPPBaseInstruction inst : instructions) 
            inst.accept(visitor, null);
    }
}
