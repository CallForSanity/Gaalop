/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappDebugger;

import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class GAPPBuilder {

    private LinkedList<GAPPBaseInstruction> instructions = new LinkedList<GAPPBaseInstruction>();

    public LinkedList<GAPPBaseInstruction> getInstructions() {
        return instructions;
    }

    public void add(GAPPBaseInstruction instruction) {
        System.out.print(instruction);
        instructions.add(instruction);
    }

    public void finish() { //TODO chs
        //GappSetMv sources sind hier GAPPMultivectors, können aber auch GAPPVectors sein!
        //blade names of PosSelector and Selector! Uses algebra!
    }

}
