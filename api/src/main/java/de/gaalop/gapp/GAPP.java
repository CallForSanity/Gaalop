package de.gaalop.gapp;

import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.util.LinkedList;

/**
 * 
 * @author Christian Steinmetz
 *
 */

public class GAPP {

    private LinkedList<GAPPBaseInstruction> instructions;
    

    public GAPP() {
        instructions = new LinkedList<GAPPBaseInstruction>();
    }



    public GAPP getCopy() {
        //TODO Chris GAPP.getCopy();
        return null;
    }

    public void addInstruction(GAPPBaseInstruction toAdd) {
        instructions.add(toAdd);
    }

    public void accept(GAPPVisitor visitor, Object arg) {
         for (GAPPBaseInstruction inst: instructions)
             inst.accept(visitor, null);
    }

}
