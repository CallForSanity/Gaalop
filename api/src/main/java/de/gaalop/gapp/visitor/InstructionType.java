package de.gaalop.gapp.visitor;

import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
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

    resetMv, assignMv, setMv, setVector, dotVectors, calculateMv, assignVector, calculateMvCoeff, addMv;

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
    public Object visitAssignVector(GAPPAssignVector gappAssignVector, Object arg) {
        return assignVector;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        return calculateMvCoeff;
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        return addMv;
    }

}
