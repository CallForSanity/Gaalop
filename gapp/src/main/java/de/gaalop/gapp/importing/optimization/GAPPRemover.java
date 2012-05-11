package de.gaalop.gapp.importing.optimization;

import de.gaalop.gapp.visitor.EmptyCFGGAPPVisitor;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import java.util.LinkedList;

/**
 * Removes particular GAPP Instructions
 *
 * @author Christian Steinmetz
 */
public class GAPPRemover extends EmptyCFGGAPPVisitor {
    //return Boolean: true, if command should be removed, otherwise false or null

    @Override
    public void visit(AssignmentNode node) {
        if (node.getGAPP() != null) {
            LinkedList<GAPPBaseInstruction> instructions = node.getGAPP().getInstructions();
            LinkedList<GAPPBaseInstruction> delInstructions = new LinkedList<GAPPBaseInstruction>();
            for (GAPPBaseInstruction instr : instructions) {
                Object result = instr.accept(this, null);

                if (result != null && ((Boolean) result).booleanValue()) {
                    delInstructions.add(instr);
                }
            }

            for (GAPPBaseInstruction instr : delInstructions) {
                instructions.remove(instr);
            }

        }
        super.visit(node);
    }
}
