package de.gaalop.gapp.visitor;

import de.gaalop.gapp.instructionSet.*;

/**
 * Declares an interface of a visitor for the GAPP data structure
 * @author Christian Steinmetz
 */
public interface GAPPVisitor {

    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg);

    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg);

    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg);

    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg);

    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg);

    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg);

    public Object visitAssignVector(GAPPAssignVector gappAssignVector, Object arg);
}
