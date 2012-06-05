package de.gaalop.gapp.visitor;

import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;

/**
 * Declares all commands of the GAPP IR
 * and implements a visitor for determining the
 * type of a given GAPPBaseInstruction instance
 * @author Christian Steinmetz
 */
public enum InstructionType implements GAPPVisitor {

    resetMv, assignMv, setMv, setVector, dotVectors, calculateMv, assignInputsVector, calculateMvCoeff;

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        return assignMv;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        return dotVectors;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        return resetMv;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        return setMv;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        return setVector;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return calculateMv;
    }

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gAPPAssignInputsVector, Object arg) {
        return assignInputsVector;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        return calculateMvCoeff;
    }


}
