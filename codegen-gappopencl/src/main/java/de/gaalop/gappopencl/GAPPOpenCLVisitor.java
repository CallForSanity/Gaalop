/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gappopencl;

import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.instructionSet.CalculationType;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author patrick
 */
public class GAPPOpenCLVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor
    implements de.gaalop.gapp.variables.GAPPVariableVisitor {

    protected boolean gcdMetaInfo = true;
    protected Map<String,Map<Integer,Integer>> mvBladeMap = new HashMap<String,Map<Integer,Integer>>();
    protected StringBuilder result = new StringBuilder();
    
    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        if(gcdMetaInfo)
            result.append("//#pragma gcd multivector ").append(gappResetMv.getDestinationMv().getName()).append("\n");

        result.append("float16 ").append(gappResetMv.getDestinationMv().getName()).append(";\n");
        mvBladeMap.put(gappResetMv.getDestinationMv().getName(),new HashMap<Integer,Integer>());

        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        Integer thisMvSetCount = mvBladeMap.get(gappSetMv.getDestination().getName()).size();

        for(PosSelector sel : gappSetMv.getSelectorsDest()) {
            if(gcdMetaInfo)
                declareGCDMultivectorComponent(gappSetMv, thisMvSetCount, sel);

            result.append(gappSetMv.getDestination().getName());
            result.append(".s").append(getOpenCLIndex(thisMvSetCount));
            result.append(" = ");
            if(gappSetMv.getSelectorsSrc().get(0).getSign() < 0)
                result.append("-");
            result.append(gappSetMv.getSource().prettyPrint());
            result.append(".s");
            result.append(getOpenCLIndex(gappSetMv.getSelectorsSrc().get(0).getIndex()));
            result.append(";\n");

            mvBladeMap.get(gappSetMv.getDestination().getName()).put(sel.getIndex(),thisMvSetCount);

            ++thisMvSetCount;
        }

        return null;
    }

    protected void declareGCDMultivectorComponent(GAPPSetMv gappSetMv, Integer thisMvSetCount, PosSelector sel) {
        result.append("//#pragma gcd multivector_component ");
        result.append(gappSetMv.getDestination().getName());
        result.append(" .s").append(thisMvSetCount);
        result.append(" ").append(sel.getIndex()); // TODO
        result.append("\n");
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        final int vectorSize = getOpenCLVectorSize(gappSetVector.getSelectorsSrc().size());

        result.append("float").append(vectorSize).append(" ");
        result.append(gappSetVector.getDestination().getName());
        result.append(" = make_float").append(vectorSize).append("(");

        Iterator<Selector> it = gappSetVector.getSelectorsSrc().iterator();
        Selector sel = it.next();
        if(sel.getSign() < 0)
            result.append("-");
        result.append(gappSetVector.getSource().getName()).append(".s").append(sel.getIndex());
        while(it.hasNext()) {
            sel = it.next();
            result.append(",");
            if(sel.getSign() < 0)
                result.append("-");
            result.append(gappSetVector.getSource().getName());
            result.append(".s").append(mvBladeMap.get(gappSetVector.getSource().getName()).get(sel.getIndex()));
        }
        result.append(");\n");

        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return null;
    }

    protected int getOpenCLVectorSize(int in) {
        if(in <= 0)
            return -1;
        else if(in == 1)
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
        result.append(it.next().prettyPrint());
        while(it.hasNext()) {
            result.append(",").append(it.next().prettyPrint());
        }

        result.append(");\n");

        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        result.append(gappCalculateMvCoeff.getDestination().prettyPrint());
        result.append(" = ");

        switch(gappCalculateMvCoeff.getType()) {
            case DIVISION:
                result.append("(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(") / (");
                result.append(gappCalculateMvCoeff.getOperand2().prettyPrint());
                result.append(")");
                break;
            case ABS:
                result.append("abs(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case ACOS:
                result.append("acos(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case ASIN:
                result.append("asin(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case ATAN:
                result.append("atan(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case CEIL:
                result.append("ceil(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case COS:
                result.append("cos(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case FLOOR:
                result.append("floor(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case LOG:
                result.append("log(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case SIN:
                result.append("sin(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case SQRT:
                result.append("sqrt(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
            case TAN:
                result.append("tan(");
                result.append(gappCalculateMvCoeff.getOperand1().prettyPrint());
                result.append(")");
                break;
        }
        
        result.append(";\n");

        return null;
    }
    
    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        Integer thisMvSetCount = mvBladeMap.get(gappAssignMv.getDestinationMv().getName()).size();

        for(GAPPValueHolder val : gappAssignMv.getValues()) {
            result.append(gappAssignMv.getDestinationMv().getName());
            result.append(".s").append(thisMvSetCount);
            result.append(" = ");
            result.append(val.prettyPrint());
            result.append(";\n");

            mvBladeMap.get(gappAssignMv.getDestinationMv().getName()).put(gappAssignMv.getSelectors().get(0).getIndex(),thisMvSetCount);
            ++thisMvSetCount;
        }

        return null;
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

    @Override
    public Object visitConstant(GAPPConstant gappConstant, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitMultivector(GAPPMultivector gappMultivector, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitMultivectorComponent(GAPPMultivectorComponent gappMultivectorComponent, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitScalarVariable(GAPPScalarVariable gappScalarVariable, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitVector(GAPPVector gappVector, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
