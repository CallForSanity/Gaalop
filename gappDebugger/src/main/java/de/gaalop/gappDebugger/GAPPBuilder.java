/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappDebugger;

import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.tba.Algebra;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class GAPPBuilder {

    private LinkedList<GAPPBaseInstruction> instructions = new LinkedList<GAPPBaseInstruction>();

    private boolean dirty = true;

    public LinkedList<GAPPBaseInstruction> getInstructions(Algebra algebra) {
        if (dirty) finish(algebra);
        return instructions;
    }

    public void add(GAPPBaseInstruction instruction) {
        dirty = true;
        instructions.add(instruction);
    }

    private void finish(Algebra algebra) {
        SetOfVariablesUpdater.updateSetOfVariables(instructions);
        BladeNameSetter.updateSetOfVariables(instructions, algebra);
        dirty = false;
    }

}
