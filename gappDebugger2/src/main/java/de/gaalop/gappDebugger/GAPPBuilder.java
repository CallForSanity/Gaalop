package de.gaalop.gappDebugger;

import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.tba.Algebra;
import java.util.LinkedList;

/**
 * Builds a GAPP instruction list
 * @author Christian Steinmetz
 */
public class GAPPBuilder {

    private LinkedList<GAPPBaseInstruction> instructions = new LinkedList<GAPPBaseInstruction>();

    private boolean dirty = true;

    public LinkedList<GAPPBaseInstruction> getInstructions(Algebra algebra) {
        if (dirty) finish(algebra);
        return instructions;
    }

    /**
     * Adds an instruction at the end of the recent instruction list
     * @param instruction The instruction to add
     */
    public void add(GAPPBaseInstruction instruction) {
        dirty = true;
        instructions.add(instruction);
    }

    /**
     * In GAPP Blade names are used for gpc output. This method produces the blade names using a given algebra.
     * Further it updates the setOfVariables
     * @param algebra The algebra
     */
    private void finish(Algebra algebra) {
        SetOfVariablesUpdater.updateSetOfVariables(instructions);
        BladeNameSetter.updateSetOfVariables(instructions, algebra);
        dirty = false;
    }

}
