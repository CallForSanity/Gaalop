/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.gappopencl;

import de.gaalop.gapp.ConstantSetVectorArgument;
import de.gaalop.gapp.PairSetOfVariablesAndIndices;
import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.SelectorIndex;
import de.gaalop.gapp.SetVectorArgument;
import de.gaalop.gapp.instructionSet.CalculationType;
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
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

    protected Map<String,Integer> mvSizes;
    protected boolean gpcMetaInfo = true;
    protected Map<String,Map<Integer,Integer>> mvBladeMap = new HashMap<String,Map<Integer,Integer>>();
    protected StringBuilder result = new StringBuilder();

    public GAPPOpenCLVisitor(Map<String, Integer> mvSizes) {
        this.mvSizes = mvSizes;
    }
    
    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        if(gpcMetaInfo)
            result.append("//#pragma gpc multivector ").append(gappResetMv.getDestinationMv().getName()).append("\n");

        result.append("float");
        result.append(getOpenCLVectorSize(mvSizes.get(gappResetMv.getDestinationMv().getName())));
        result.append(" ");
        result.append(gappResetMv.getDestinationMv().getName()).append(";\n");
        mvBladeMap.put(gappResetMv.getDestinationMv().getName(),new HashMap<Integer,Integer>());

        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        Integer thisMvSetCount = mvBladeMap.get(gappSetMv.getDestination().getName()).size();

        for(PosSelector sel : gappSetMv.getSelectorsDest()) {
            if(gpcMetaInfo)
                declareGPCMultivectorComponent(gappSetMv.getDestination().getName(), thisMvSetCount, sel);

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

    protected void declareGPCMultivectorComponent(String mv, Integer blade, SelectorIndex sel) {
        result.append("//#pragma gpc multivector_component ");
        result.append(mv);
        result.append(" ").append(sel.getBladeName());
        result.append(" ").append(mv).append(".s").append(blade);
        result.append("\n");
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        
        // determine vector sizes
        int vectorSize = 0;
        for(SetVectorArgument setVectorArg : gappSetVector.getEntries()) {
            if(setVectorArg.isConstant())
                ++vectorSize;
            else
                vectorSize += ((PairSetOfVariablesAndIndices)setVectorArg).getSelectors().size();
        }
        final int openCLVectorSize = getOpenCLVectorSize(vectorSize);

        // print declation
        result.append("float").append(openCLVectorSize).append(" ");
        result.append(gappSetVector.getDestination().getName());
        result.append(" = make_float").append(openCLVectorSize).append("(");

        Iterator<SetVectorArgument> itSetVectorArg = gappSetVector.getEntries().iterator();
        visitSetVectorArg(itSetVectorArg.next());    
        while(itSetVectorArg.hasNext()) {
            result.append(",");
            visitSetVectorArg(itSetVectorArg.next());    
        }
        
        // fill remaining vector space with zeros
        for(int counter = vectorSize; counter < openCLVectorSize; ++counter)
            result.append(",0");

        result.append(");\n");

        // update blade map
        Map<Integer,Integer> bladeMap = new HashMap<Integer,Integer>();
        for(int blade = 0; blade < vectorSize; ++blade)
            bladeMap.put(blade,blade);
        mvBladeMap.put(gappSetVector.getDestination().getName(),bladeMap);

        return null;
    }

    protected void visitSetVectorArg(final SetVectorArgument setVectorArg) {
        if(setVectorArg.isConstant())
            result.append(((ConstantSetVectorArgument)setVectorArg).getValue());
        else {
            final PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices)setVectorArg;
            Iterator<Selector> itSel = pair.getSelectors().iterator();
            visitSelector(itSel.next(), pair.getSetOfVariable().getName());
            while (itSel.hasNext()) {
                result.append(",");
                visitSelector(itSel.next(), pair.getSetOfVariable().getName());
            }
        }
    }

    protected void visitSelector(final Selector sel, final String sourceName) {
        if (sel.getSign() < 0)
            result.append("-");
        result.append(sourceName);
        result.append(".s").append(mvBladeMap.get(sourceName).get(sel.getIndex()));
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return null;
    }

    protected int getOpenCLVectorSize(final int in) {
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
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        result.append(gappCalculateMvCoeff.getDestination().prettyPrint());
        result.append(" = ");
        printCalculateOp(gappCalculateMvCoeff.getType(),
                         gappCalculateMvCoeff.getOperand1().prettyPrint(),
                         gappCalculateMvCoeff.getOperand2().prettyPrint());
        result.append(";\n");

        return null;
    }

    protected void printCalculateOp(CalculationType type,String operand1,String operand2) {
        switch (type) {
            case DIVISION:
                result.append("(");
                result.append(operand1);
                result.append(") / (");
                result.append(operand2);
                result.append(")");
                break;
            case ABS:
                result.append("abs(");
                result.append(operand1);
                result.append(")");
                break;
            case ACOS:
                result.append("acos(");
                result.append(operand1);
                result.append(")");
                break;
            case ASIN:
                result.append("asin(");
                result.append(operand1);
                result.append(")");
                break;
            case ATAN:
                result.append("atan(");
                result.append(operand1);
                result.append(")");
                break;
            case CEIL:
                result.append("ceil(");
                result.append(operand1);
                result.append(")");
                break;
            case COS:
                result.append("cos(");
                result.append(operand1);
                result.append(")");
                break;
            case FLOOR:
                result.append("floor(");
                result.append(operand1);
                result.append(")");
                break;
            case LOG:
                result.append("log(");
                result.append(operand1);
                result.append(")");
                break;
            case SIN:
                result.append("sin(");
                result.append(operand1);
                result.append(")");
                break;
            case SQRT:
                result.append("sqrt(");
                result.append(operand1);
                result.append(")");
                break;
            case TAN:
                result.append("tan(");
                result.append(operand1);
                result.append(")");
                break;
        }
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

    protected static int dotCount = 0;

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        Integer thisMvSetCount = mvBladeMap.get(gappDotVectors.getDestination().getName()).size();

        // print gpc meta info
        if(gpcMetaInfo)
            declareGPCMultivectorComponent(gappDotVectors.getDestination().getName(),
                                           thisMvSetCount,
                                           gappDotVectors.getDestSelector());
        
        // determine vector size
        int openCLVectorSize = getOpenCLVectorSize(mvBladeMap.get(gappDotVectors.getParts().get(0).getName()).size());        

        // parallel multiply operation
        result.append("float").append(openCLVectorSize).append(" ");
        result.append("dot").append(dotCount);
        result.append(" = ");
        Iterator<GAPPVector> it = gappDotVectors.getParts().iterator();
        result.append(it.next().getName());
        while(it.hasNext()) {
            result.append(" * ");
            result.append(it.next().getName());
        }
        result.append(";\n");

        // parallel pyramid reduce
        while((openCLVectorSize >>= 1) > 1) {
            result.append("float").append(openCLVectorSize);
            result.append(" dot").append(dotCount+1);
            result.append(" = ");
            result.append("dot").append(dotCount).append(".odd");
            result.append(" + ");
            result.append("dot").append(dotCount).append(".even");
            result.append(";\n");

            ++dotCount;
        }

        // last step directly assigns to destination
        result.append(gappDotVectors.getDestination().getName());
        result.append(".s").append(thisMvSetCount);
        result.append(" = ");
        result.append("dot").append(dotCount).append(".odd");
        result.append(" + ");
        result.append("dot").append(dotCount).append(".even");
        result.append(";\n");
        ++dotCount;

        // update blade map
        mvBladeMap.get(gappDotVectors.getDestination().getName()).
                put(thisMvSetCount,
                    gappDotVectors.getDestSelector().getIndex());
        
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

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gappAssignInputsVector, Object arg) {
        final int openCLVectorSize = getOpenCLVectorSize(gappAssignInputsVector.getValues().size());

        result.append("float");
        result.append(openCLVectorSize);
        result.append(" inputsVector");
        result.append(" = make_float");
        result.append(openCLVectorSize);
        result.append("(");

        Map<Integer,Integer> bladeMap = new HashMap<Integer,Integer>();
        Iterator<GAPPValueHolder> it = gappAssignInputsVector.getValues().iterator();
        result.append(it.next().prettyPrint());
        bladeMap.put(bladeMap.size(), bladeMap.size());
        while(it.hasNext()) {
            result.append(",").append(it.next().prettyPrint());
            bladeMap.put(bladeMap.size(), bladeMap.size());
        }
        result.append(");\n");

        mvBladeMap.put("inputsVector",bladeMap);

        return null;
    }
}
