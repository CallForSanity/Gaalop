package de.gaalop.gapp.visitor;

import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.visitor.CFGGAPPVisitor;

/**
 * Implements an CFGGAPPVisitor with empty bodies of GAPP visit methods
 * @author Christian Steinmetz
 */
public class EmptyCFGGAPPVisitor extends CFGGAPPVisitor {

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return null;
    }

    @Override
    public Object visitAssignVector(GAPPAssignVector gappAssignVector, Object arg) {
        return null;
    }

}
