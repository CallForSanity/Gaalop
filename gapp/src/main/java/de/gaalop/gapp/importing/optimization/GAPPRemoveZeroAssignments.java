package de.gaalop.gapp.importing.optimization;

import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Valueset;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPValueHolder;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Optimizes the GAPP Code by
 * removing assignMv instructions with a zero value.
 *
 * @author Christian Steinmetz
 */
public class GAPPRemoveZeroAssignments extends GAPPRemover {
    //return Boolean: true, if command should be removed, otherwise false or null

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        //Remove all zeros in AssignMv
        LinkedList<PosSelector> delSelectors = new LinkedList<PosSelector>();
        LinkedList<GAPPConstant> delVariables = new LinkedList<GAPPConstant>();

        PosSelectorset selSet = gappAssignMv.getSelectors();
        Valueset valSet = gappAssignMv.getValues();

        Iterator<PosSelector> selIt = selSet.listIterator();
        Iterator<GAPPConstant> valIt = valSet.listIterator();
        while (selIt.hasNext() && valIt.hasNext()) {
            PosSelector curSel = selIt.next();
            GAPPValueHolder curVar = valIt.next();

            if (!curVar.isVariable()) {
                GAPPConstant constant = (GAPPConstant) curVar;
                if (Math.abs(constant.getValue()) < 10E-04) {
                    delSelectors.add(curSel);
                    delVariables.add(constant);
                }
            }
        }


        for (PosSelector sel : delSelectors) {
            selSet.remove(sel);
        }

        for (GAPPConstant val : delVariables) {
            valSet.remove(val);
        }

        // if selList is Empty remove the whole command
        return selSet.isEmpty();
    }
}
