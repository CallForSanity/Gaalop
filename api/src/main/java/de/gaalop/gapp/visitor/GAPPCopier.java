package de.gaalop.gapp.visitor;

import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSignedMultivectorComponent;
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
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        return new GAPPAddMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappAddMv.getDestinationMv()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappAddMv.getSourceMv()),
                copySelectorset(gappAddMv.getSelectorsDest()),
                copySelectorset(gappAddMv.getSelectorsSrc())
                );
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        return new GAPPAssignMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappAssignMv.getDestinationMv()),
                copySelectorset(gappAssignMv.getSelectors()),
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
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappResetMv.getDestinationMv())
                );
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        return new GAPPSetMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappSetMv.getDestinationMv()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappSetMv.getSourceMv()),
                copySelectorset(gappSetMv.getSelectorsDest()),
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



}
