/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp.visitor;

import de.gaalop.gapp.instructionSet.*;

/**
 * Defines a interface of a visitor for the GAPP Data structure
 * @author christian
 */
public interface GAPPVisitor {

    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg);
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg);
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg);
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg);
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg);
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg);

}
