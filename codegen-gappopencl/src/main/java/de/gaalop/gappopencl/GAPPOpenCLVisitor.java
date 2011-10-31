/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gappopencl;

import de.gaalop.gapp.Selector;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.Iterator;


/**
 *
 * @author patrick
 */
public class GAPPOpenCLVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor {
    
    private StringBuilder result = new StringBuilder();
    
    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        result.append("float");
        // append the right size here
        result.append(gappSetVector.getDestination());
        result.append(" = make_float");
        // append the right size here
        result.append("(");

        Iterator<Selector> it = gappSetVector.getSelectorsSrc().iterator();
        Selector sel = it.next();
        result.append(sel.getSign()).append(gappSetVector.getSource()).append(".s").append(sel.getIndex());
        while(it.hasNext()) {
            sel = it.next();
            result.append(",");
            result.append(sel.getSign()).append(gappSetVector.getSource()).append(".s").append(sel.getIndex());
        }
        result.append(");\n");

        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected int getOpenCLVectorSize(int in) {
        if(in == 1)
            return in;
        else if(in == 2)
            return in;
        else if(in <= 4)
            return 4;
        else if(in <= 8)
            return 8;
        else if(in <= 16)
            return 16;

        assert(false);
        return -1;
    }

    protected String getOpenCLIndex(Integer index) {
        if(index < 10)
            return index.toString();
        else switch(index) {
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
        }
        
        assert(false);
        return "fail";
    }

    @Override
    public Object visitAssignVector(GAPPAssignVector gappAssignVector, Object arg) {
        final int vectorSize = getOpenCLVectorSize(gappAssignVector.getValues().size());

        result.append("float");
        result.append(vectorSize);
        result.append(" ");
        result.append(gappAssignVector.getDestination().getName());
        result.append(" = make_float");
        result.append(vectorSize);
        result.append("(");

        Iterator<GAPPValueHolder> it = gappAssignVector.getValues().iterator();
        result.append(it.next()).append(",");
        while(it.hasNext()) {
            result.append(it.next()).append(",");
        }

        result.append(");\n");

        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        result.append(gappDotVectors.getDestination().getName());
        result.append(" = ");

        Iterator<GAPPVector> it = gappDotVectors.getParts().iterator();
        result.append(it.next().getName());

        while(it.hasNext()) {
            result.append(" * ");
            result.append(it.next().getName());
        }
        result.append(";\n");
        
        return null;
    }

    String getCode() {
        return result.toString();
    }
}
