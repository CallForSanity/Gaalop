/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappDebugger;

import de.gaalop.gapp.PairSetOfVariablesAndIndices;
import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.SetVectorArgument;
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.tba.Algebra;
import java.util.LinkedList;

/**
 * Sets all bladenames in all GAPP instructions
 * @author Christian Steinmetz
 */
public class BladeNameSetter implements GAPPVisitor {

    private Algebra algebra;

    private BladeNameSetter(Algebra algebra) {//make use of static method mandatory
        this.algebra = algebra;
    }

    /**
     * Set all bladenames
     * @param instructions The GAPP instructions
     */
    public static void updateSetOfVariables(LinkedList<GAPPBaseInstruction> instructions, Algebra algebra) {
        BladeNameSetter updater = new BladeNameSetter(algebra);
        for (GAPPBaseInstruction i: instructions)
            i.accept(updater, null);
    }

    private void processPosSelector(PosSelector posSelector) {
        posSelector.setBladeName(algebra.getBlade(posSelector.getIndex()).toString());
    }

    private void processSelector(Selector selector) {
        selector.setBladeName(algebra.getBlade(selector.getIndex()).toString());
    }

    private void processPosSelectorset(PosSelectorset set) {
        for (PosSelector sel: set)
            processPosSelector(sel);
    }

    private void processSelectorset(Selectorset set) {
        for (Selector sel: set)
            processSelector(sel);
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        processPosSelectorset(gappAssignMv.getSelectors());
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        processSelector(gappDotVectors.getDestSelector());
        return null;
    } 

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        processPosSelectorset(gappSetMv.getSelectorsDest());
        processSelectorset(gappSetMv.getSelectorsSrc());
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        for (SetVectorArgument entry: gappSetVector.getEntries()) 
            if (!entry.isConstant()) {
                PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) entry;
                processSelectorset(pair.getSelectors());
            }
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        return null;
    }

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gAPPAssignInputsVector, Object arg) {
        return null;
    }

}
