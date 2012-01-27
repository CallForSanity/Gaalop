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

    protected static int dotCount = 0;
    protected Map<String,Integer> mvSizes;
    protected boolean gpcMetaInfo = true;
    protected Map<String,Map<Integer,String>> mvBladeMap = new HashMap<String,Map<Integer,String>>();
    protected StringBuilder result = new StringBuilder();

    public GAPPOpenCLVisitor(Map<String, Integer> mvSizes) {
        this.mvSizes = mvSizes;
    }
    
    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        final String destMv = GAPPOpenCLCodeGenerator.getVarName(gappResetMv.getDestination().getName());
        
        if(gpcMetaInfo && !destMv.startsWith(GAPPOpenCLCodeGenerator.tempMv))
            result.append("//#pragma gpc multivector ").append(destMv).append("\n");

        visitOpenCLVectorType(getOpenCLVectorSize(mvSizes.get(destMv)));
        result.append(" ");
        result.append(destMv).append(";\n");
        mvBladeMap.put(destMv,new HashMap<Integer,String>());

        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        final String destMv = GAPPOpenCLCodeGenerator.getVarName(gappSetMv.getDestination().getName());
        Integer thisMvSetCount = mvBladeMap.get(destMv).size();

        int selCount = 0;
        for(PosSelector sel : gappSetMv.getSelectorsDest()) {
            declareGPCMultivectorComponent(destMv, thisMvSetCount, sel);

            final String bladeCoeff = getBladeCoeff(destMv,thisMvSetCount);
            
            result.append(bladeCoeff);
            result.append(" = ");
            if(gappSetMv.getSelectorsSrc().get(0).getSign() < 0)
                result.append("-");
            result.append(mvBladeMap.get(GAPPOpenCLCodeGenerator.getVarName(gappSetMv.getSource().getName())).get(gappSetMv.getSelectorsSrc().get(selCount++).getIndex()));
            result.append(";\n");

            mvBladeMap.get(destMv).put(sel.getIndex(),bladeCoeff);

            ++thisMvSetCount;
        }

        return null;
    }

    protected String getBladeCoeff(String mv,int blade) {
        return mv + ((mvSizes.get(mv) == 1) ? "" : (".s" + getOpenCLIndex(blade)));
    }
    
    protected void declareGPCMultivectorComponent(String mv, Integer blade, SelectorIndex sel) {
        if(!gpcMetaInfo || mv.startsWith(GAPPOpenCLCodeGenerator.tempMv))
            return;
        
        result.append("//#pragma gpc multivector_component ");
        result.append(mv);
        result.append(" ").append(sel.getBladeName());
        result.append(" ").append(mv).append(".s").append(blade);
        result.append("\n");
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        final String destVec = GAPPOpenCLCodeGenerator.getVarName(gappSetVector.getDestination().getName());

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
        visitOpenCLVectorType(openCLVectorSize);
        result.append(" ");
        result.append(destVec);
        result.append(" = (");
        visitOpenCLVectorType(openCLVectorSize);
        result.append(")(");

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
            bladeMap.put(blade,destVec + ".s" + blade);
        mvBladeMap.put(destVec,bladeMap);

        return null;
    }
    
    protected void visitOpenCLVectorType(final int openCLVectorSize) {
        result.append("float");
        
        if(openCLVectorSize != 1)
            result.append(openCLVectorSize);
    }

    protected void visitSetVectorArg(final SetVectorArgument setVectorArg) {
        if(setVectorArg.isConstant())
            result.append(((ConstantSetVectorArgument)setVectorArg).getValue());
        else {
            final PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices)setVectorArg;
            Iterator<Selector> itSel = pair.getSelectors().iterator();
            visitSelector(itSel.next(), GAPPOpenCLCodeGenerator.getVarName(pair.getSetOfVariable().getName()));
            while (itSel.hasNext()) {
                result.append(",");
                visitSelector(itSel.next(), GAPPOpenCLCodeGenerator.getVarName(pair.getSetOfVariable().getName()));
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
        final String destMv = GAPPOpenCLCodeGenerator.getVarName(gappCalculateMvCoeff.getDestination().getName());
        final Integer thisMvSetCount = mvBladeMap.get(destMv).size();
        final String bladeCoeff = getBladeCoeff(destMv,thisMvSetCount);

        result.append(bladeCoeff);
        result.append(" = ");
        visitCalculateOp(gappCalculateMvCoeff.getType(),
                         GAPPOpenCLCodeGenerator.getVarName(gappCalculateMvCoeff.getOperand1().getName()),
                         GAPPOpenCLCodeGenerator.getVarName(gappCalculateMvCoeff.getOperand2().getName()));
        result.append(";\n");

        mvBladeMap.get(destMv).put(gappCalculateMvCoeff.getDestination().getBladeIndex(),bladeCoeff);

        return null;
    }

    protected void visitCalculateOp(CalculationType type,String operand1,String operand2) {
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
        final String destMv = GAPPOpenCLCodeGenerator.getVarName(gappAssignMv.getDestination().getName());
        Integer thisMvSetCount = mvBladeMap.get(destMv).size();

        int selCount = 0;
        for(GAPPValueHolder val : gappAssignMv.getValues()) {
            final String bladeCoeff = getBladeCoeff(destMv,thisMvSetCount);
            
            result.append(bladeCoeff);
            result.append(" = ");
            result.append(val.toString());
            result.append(";\n");

            mvBladeMap.get(destMv).put(gappAssignMv.getSelectors().get(selCount++).getIndex(),bladeCoeff);
            ++thisMvSetCount;
        }

        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        final String destMv = GAPPOpenCLCodeGenerator.getVarName(gappDotVectors.getDestination().getName());
        Integer thisMvSetCount = mvBladeMap.get(destMv).size();

        // print gpc meta info
        declareGPCMultivectorComponent(destMv,
                                       thisMvSetCount,
                                       gappDotVectors.getDestSelector());
        
        // compute blade coeff name
        final String bladeCoeff = getBladeCoeff(destMv,thisMvSetCount);
        // update blade map
        mvBladeMap.get(destMv).
                put(gappDotVectors.getDestSelector().getIndex(),bladeCoeff);

        // special case for operands of size 1
        final int operandSize = mvBladeMap.get(GAPPOpenCLCodeGenerator.getVarName(gappDotVectors.getParts().get(0).getName())).size();
        if(operandSize == 1) {
            result.append(bladeCoeff).append(" = ");
            visitDotVectorsParallelMultiply(gappDotVectors);
            return null;
        }
        
        // determine OpenCL vector size
        int openCLVectorSize = getOpenCLVectorSize(operandSize);        

        // parallel multiply operation
        visitOpenCLVectorType(openCLVectorSize);
        result.append(" ").append(GAPPOpenCLCodeGenerator.dot).append(dotCount);
        result.append(" = ");
        visitDotVectorsParallelMultiply(gappDotVectors);

        // parallel pyramid reduce
        while((openCLVectorSize >>= 1) > 1) {
            visitOpenCLVectorType(openCLVectorSize);
            result.append(" ").append(GAPPOpenCLCodeGenerator.dot).append(dotCount+1);
            result.append(" = ");
            result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append(".odd");
            result.append(" + ");
            result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append(".even");
            result.append(";\n");

            ++dotCount;
        }

        // last step directly assigns to destination
        result.append(bladeCoeff);
        result.append(" = ");
        result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append(".odd");
        result.append(" + ");
        result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append(".even");
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
        final String inputsArrayName = GAPPOpenCLCodeGenerator.getVarName(GAPPOpenCLCodeGenerator.inputsVector);
        
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

        mvBladeMap.put(inputsArrayName,bladeMap);

        return null;
    }
}
