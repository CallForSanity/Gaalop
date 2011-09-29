package de.gaalop.gapp.importing.optimization;

import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
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
        LinkedList<Selector> delSelectors = new LinkedList<Selector>();
        LinkedList<GAPPValueHolder> delVariables = new LinkedList<GAPPValueHolder>();

        Selectorset selSet = gappAssignMv.getSelectors();
        Variableset varSet = gappAssignMv.getValues();

        Iterator<Selector> selIt = selSet.listIterator();
        Iterator<GAPPValueHolder> varIt = varSet.listIterator();
        while (selIt.hasNext() && varIt.hasNext()) {
            Selector curSel = selIt.next();
            GAPPValueHolder curVar = varIt.next();

            if (!curVar.isVariable())
                if (Math.abs(((GAPPConstant) curVar).getValue()) < 10E-04) {
                    delSelectors.add(curSel);
                    delVariables.add(curVar);
                }
        }
        

        for (Selector sel: delSelectors)
            selSet.remove(sel);

        for (GAPPValueHolder val: delVariables)
            varSet.remove(val);

        // if selList is Empty remove the whole command
        return selSet.isEmpty();
    }

}
