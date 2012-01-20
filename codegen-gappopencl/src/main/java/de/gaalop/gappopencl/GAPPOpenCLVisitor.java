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

    protected static Integer numInputsVectors = -1;
    protected Map<String,Integer> mvSizes;
    protected boolean gpcMetaInfo = true;
    protected Map<String,Map<Integer,String>> mvBladeMap = new HashMap<String,Map<Integer,String>>();
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
        mvBladeMap.put(gappResetMv.getDestinationMv().getName(),new HashMap<Integer,String>());

        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        Integer thisMvSetCount = mvBladeMap.get(gappSetMv.getDestination().getName()).size();

        int selCount = 0;
        for(PosSelector sel : gappSetMv.getSelectorsDest()) {
            if(gpcMetaInfo)
                declareGPCMultivectorComponent(gappSetMv.getDestination().getName(), thisMvSetCount, sel);

            final String bladeCoeff = gappSetMv.getDestination().getName() + ".s" + getOpenCLIndex(thisMvSetCount);
            
            result.append(bladeCoeff);
            result.append(" = ");
            if(gappSetMv.getSelectorsSrc().get(0).getSign() < 0)
                result.append("-");
            result.append(mvBladeMap.get(gappSetMv.getSource().getName()).get(gappSetMv.getSelectorsSrc().get(selCount++).getIndex()));
            result.append(";\n");

            mvBladeMap.get(gappSetMv.getDestination().getName()).put(sel.getIndex(),bladeCoeff);

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
        Map<Integer,String> bladeMap = new HashMap<Integer,String>();
        for(int blade = 0; blade < vectorSize; ++blade)
            bladeMap.put(blade,gappSetVector.getDestination().getName() + ".s" + blade);
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
        final String lookupBladeCoeff = mvBladeMap.get(sourceName).get(sel.getIndex());
        if(lookupBladeCoeff == null)
            result.append(sourceName);
        else
            result.append(lookupBladeCoeff);
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
        result.append(gappCalculateMvCoeff.getDestination().getName());
        result.append(" = ");
        printCalculateOp(gappCalculateMvCoeff.getType(),
                         gappCalculateMvCoeff.getOperand1().getName(),
                         gappCalculateMvCoeff.getOperand2().getName());
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

        int selCount = 0;
        for(GAPPValueHolder val : gappAssignMv.getValues()) {
            final String bladeCoeff = gappAssignMv.getDestinationMv().getName() + ".s" + thisMvSetCount.toString();
            result.append(bladeCoeff);
            result.append(" = ");
            result.append(val.toString());
            result.append(";\n");

            mvBladeMap.get(gappAssignMv.getDestinationMv().getName()).put(gappAssignMv.getSelectors().get(selCount++).getIndex(),bladeCoeff);
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
        
        // compute blade coeff name
        final String bladeCoeff = gappDotVectors.getDestination().getName() + ".s" + getOpenCLIndex(thisMvSetCount);
        // update blade map
        mvBladeMap.get(gappDotVectors.getDestination().getName()).
                put(gappDotVectors.getDestSelector().getIndex(),bladeCoeff);

        // special case for operands of size 1
        final int operandSize = mvBladeMap.get(gappDotVectors.getParts().get(0).getName()).size();
        if(operandSize == 1) {
            result.append(bladeCoeff).append(" = ");
            visitDotVectorsParallelMultiply(gappDotVectors);
            return null;
        }
        
        // determine OpenCL vector size
        int openCLVectorSize = getOpenCLVectorSize(operandSize);        

        // parallel multiply operation
        result.append("float").append(openCLVectorSize).append(" ");
        result.append("dot").append(dotCount);
        result.append(" = ");
        visitDotVectorsParallelMultiply(gappDotVectors);

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
        result.append(bladeCoeff);
        result.append(" = ");
        result.append("dot").append(dotCount).append(".odd");
        result.append(" + ");
        result.append("dot").append(dotCount).append(".even");
        result.append(";\n");
        ++dotCount;
        
        return null;
    }
    
    public void visitDotVectorsParallelMultiply(GAPPDotVectors gappDotVectors) {
        Iterator<GAPPVector> it = gappDotVectors.getParts().iterator();
        result.append(it.next().getName());
        while (it.hasNext()) {
            result.append(" * ");
            result.append(it.next().getName());
        }
        result.append(";\n");
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
        final String inputsArrayName = "inputsVector" + (++numInputsVectors).toString();
        
        result.append("float ");
        result.append(inputsArrayName);
        result.append("[");
        result.append(gappAssignInputsVector.getValues().size());
        result.append("];\n");

        Map<Integer,String> bladeMap = new HashMap<Integer,String>();
        Iterator<GAPPValueHolder> it = gappAssignInputsVector.getValues().iterator();
        while(it.hasNext()) {
            final String bladeCoeff = inputsArrayName + "[" + bladeMap.size() + "]";
            
            result.append(bladeCoeff).append(" = ");
            result.append(it.next().prettyPrint());
            result.append(";\n");
            bladeMap.put(bladeMap.size(), bladeCoeff);
        }

        mvBladeMap.put("inputsVector",bladeMap);

        return null;
    }
}
