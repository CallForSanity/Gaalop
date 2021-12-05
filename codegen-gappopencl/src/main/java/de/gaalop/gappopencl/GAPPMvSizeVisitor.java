package de.gaalop.gappopencl;

import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Patrick Charrier
 */
public class GAPPMvSizeVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor {

    protected Map<String,Integer> mvSizes = new HashMap<String,Integer>();
    
    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        incrementSize(GAPPOpenCLCodeGenerator.getVarName(gappDotVectors.getDestination().getName()),1);        
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        getMvSizes().put(GAPPOpenCLCodeGenerator.getVarName(gappResetMv.getDestination().getName()), 0);
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        incrementSize(GAPPOpenCLCodeGenerator.getVarName(gappSetMv.getDestination().getName()),
                      gappSetMv.getSelectorsDest().size());        
        return null;
    }

    protected void incrementSize(final String gappMvName,final int add) {
        getMvSizes().put(gappMvName,getMvSizes().get(gappMvName) + add);
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
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        incrementSize(GAPPOpenCLCodeGenerator.getVarName(gappCalculateMvCoeff.getDestination().getName()),1);        
        return null;
    }

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gAPPAssignInputsVector, Object arg) {
        return null;
    }

    /**
     * @return the mvSizes
     */
    public Map<String,Integer> getMvSizes() {
        return mvSizes;
    }
    
}
