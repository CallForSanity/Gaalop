package de.gaalop.gapp.visitor;

import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPValueHolderCopier;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.LinkedList;

/**
 * Implements a visitor for (deep) copying instructions
 * @author Christian Steinmetz
 */
public class GAPPCopier implements GAPPVisitor {

    /**
     * Copies a selectorset
     * @param sel The selectorset to be copied
     * @return The copy
     */
    private Selectorset copySelectorset(Selectorset sel) {
        Selectorset copy = new Selectorset();
        for (Selector cur: sel)
            copy.add(new Selector(cur.getIndex(),cur.getSign()));
        return copy;
    }

    /**
     * Copies a posSelectorset
     * @param sel The posSelectorset to be copied
     * @return The copy
     */
    private PosSelectorset copyPosSelectorset(PosSelectorset sel) {
        PosSelectorset copy = new PosSelectorset();
        for (PosSelector cur: sel)
            copy.add(new PosSelector(cur.getIndex()));
        return copy;
    }

    private Selector copySelector(Selector sel) {
        return new Selector(sel.getIndex(),sel.getSign());
    }

     /**
     * Copies a variableset
     * @param var The variableset to be copied
     * @return The copy
     */
    private Variableset copyVariableset(Variableset var) {
        Variableset copy = new Variableset();

        for (GAPPValueHolder cur: var)
            copy.add(GAPPValueHolderCopier.copyValueHolder(cur));
  
        return copy;
    }

    /**
     * Copies a list of vectors (deep copy)
     * @param vectors The list of vectors to be copied
     * @return The copied list
     */
    private LinkedList<GAPPVector> copyVectors(LinkedList<GAPPVector> vectors) {
        LinkedList<GAPPVector> result = new LinkedList<GAPPVector>();
        for (GAPPVector part: vectors)
            result.add((GAPPVector) GAPPValueHolderCopier.copyValueHolder(part));
        return result;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        return new GAPPAssignMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappAssignMv.getDestinationMv()),
                copyPosSelectorset(gappAssignMv.getSelectors()),
                copyVariableset(gappAssignMv.getValues())
                );
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        return new GAPPDotVectors(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappDotVectors.getDestination()),
                copySelector(gappDotVectors.getDestSelector()),
                copyVectors(gappDotVectors.getParts())
                );
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        return new GAPPResetMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(
                    gappResetMv.getDestinationMv()),
                    gappResetMv.getSize()
                );
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        return new GAPPSetMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappSetMv.getDestinationMv()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappSetMv.getSource()),
                copyPosSelectorset(gappSetMv.getSelectorsDest()),
                copySelectorset(gappSetMv.getSelectorsSrc())
                );
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        return new GAPPSetVector(
                (GAPPVector) GAPPValueHolderCopier.copyValueHolder(gappSetVector.getDestination()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappSetVector.getSourceMv()),
                copySelectorset(gappSetVector.getSelectorsSrc())
                );
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return new GAPPCalculateMv(gappCalculateMv.getType(),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMv.getTarget()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMv.getOperand1()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMv.getOperand2())
                );
    }

    @Override
    public Object visitAssignVector(GAPPAssignVector gappAssignVector, Object arg) {
        return new GAPPAssignVector(
                (GAPPVector) GAPPValueHolderCopier.copyValueHolder(gappAssignVector.getDestination()),
                copyVariableset(gappAssignVector.getValues())
                );
    }

}
