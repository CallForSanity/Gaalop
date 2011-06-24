package de.gaalop.gapp.visitor;

import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;

/**
 * Declares all commands of the GAPP IR
 * and implements a visitor for determining type of a given GAPPBaseInstruction instance
 * @author christian
 */
public enum InstructionType implements GAPPVisitor {

    resetMv,assignMv,setMv,addMv,setVector,dotVectors;

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
       return addMv;
    }

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

}
