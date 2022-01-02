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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;


/**
 *
 * @author patrick
 */
public class GAPPOpenCLVisitor extends de.gaalop.gapp.visitor.CFGGAPPVisitor
    implements de.gaalop.gapp.variables.GAPPVariableVisitor {

    protected static int dotCount = 0;
    protected static final String lo = ".lo";
    protected static final String hi = ".hi";
    protected static final int maxOpenCLVectorSize = 16;
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

        printOpenCLVectorType(computeNearestOpenCLVectorSize(mvSizes.get(destMv)));
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
    
    protected static String formatBladeName(final String bladeName) {
        if(bladeName.equals("1.0") || bladeName.equals("1.0f"))
            return "1";
        
        // remove whitespaces from bladeIndex
        StringTokenizer tokenizer = new StringTokenizer(bladeName," \t\n\r\f()");
        StringBuilder bladeBuffer = new StringBuilder();
        while(tokenizer.hasMoreTokens())
            bladeBuffer.append(tokenizer.nextToken());
        return bladeBuffer.toString();
    }
        
    protected void declareGPCMultivectorComponent(String mv, Integer blade, SelectorIndex sel) {
        if(!gpcMetaInfo || mv.startsWith(GAPPOpenCLCodeGenerator.tempMv))
            return;
        
        result.append("//#pragma gpc multivector_component ");
        result.append(mv);
        result.append(" ").append(formatBladeName(sel.getBladeName()));
        result.append(" ").append(mv);
        if(mvSizes.get(mv) > 1)
            result.append(".s").append(blade);
        result.append("\n");
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        // determine vector sizes
        int wholeVectorSize = 0;
        for(SetVectorArgument setVectorArg : gappSetVector.getEntries()) {
            if(setVectorArg.isConstant())
                ++wholeVectorSize;
            else
                wholeVectorSize += ((PairSetOfVariablesAndIndices)setVectorArg).getSelectors().size();
        }
        
        // get destVecBase
        final String destVecBase = GAPPOpenCLCodeGenerator.getVarName(gappSetVector.getDestination().getName());

        // collect all entries as string
        ArrayList<String> entries = new ArrayList<String>();
        Iterator<SetVectorArgument> itSetVectorArg = gappSetVector.getEntries().iterator();
        while(itSetVectorArg.hasNext()) {
            final SetVectorArgument setVectorArg = itSetVectorArg.next();
            
            if(setVectorArg.isConstant())
                entries.add(String.valueOf(((ConstantSetVectorArgument)setVectorArg).getValue()));
            else {
                PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices)setVectorArg;

                // get a fresh selector iterator
                Iterator<Selector> itSelector = pair.getSelectors().iterator();
                // get all entries from selectors
                while (itSelector.hasNext())
                    entries.add(visitSelector(itSelector.next(),
                                              GAPPOpenCLCodeGenerator.getVarName(pair.getSetOfVariable().getName())));
            }
        }
        
        // parallel multiply operation
        Map<Integer,String> bladeMap = new HashMap<Integer,String>();
        Iterator<String> itEntries = entries.iterator();
        int vectorSizeRemainder = wholeVectorSize;
        int subvectorIndex = 0;
        int bladeGlobalIndex = 0;
        do {
            // compute nearest OpenCL vector size
            final int openCLVectorSize = computeNearestOpenCLVectorSize(vectorSizeRemainder);
            // set destVec name
            final String destVec = destVecBase + "_" + subvectorIndex;
            
            // print declaration
            printOpenCLVectorType(openCLVectorSize);
            result.append(" ");
            result.append(destVec);
            result.append(" = (");
            printOpenCLVectorType(openCLVectorSize);
            result.append(")(");

            // print entries
            int bladeLocalIndex = 0;
            // print first entry
            if(itEntries.hasNext()) {
                result.append(itEntries.next());
                ++bladeLocalIndex;
            }
            // print further entries
            while(bladeLocalIndex++ < openCLVectorSize && itEntries.hasNext()) {
                result.append(",");
                result.append(itEntries.next());
            }

            // fill remaining vector space with zeros
            assert(bladeLocalIndex > 0); // cannot be the first entry
            while(bladeLocalIndex++ < openCLVectorSize)
                result.append(",0");

            // print end of line
            result.append(");\n");

            // update bladeIndex map
            for(bladeLocalIndex = 0; bladeLocalIndex < openCLVectorSize; ++bladeLocalIndex)
                bladeMap.put(bladeGlobalIndex++,destVec + ".s" + bladeLocalIndex);
            mvBladeMap.put(destVecBase,bladeMap);

            // compute vector size remainder
            vectorSizeRemainder -= maxOpenCLVectorSize; // if wholeVectorSize < 16 all should be done now
            // increment subvector index
            ++subvectorIndex;
        } while(vectorSizeRemainder > 0);
        
        return null;
    }
    
    protected void printOpenCLVectorType(final int openCLVectorSize) {
        result.append("float");
        
        if(openCLVectorSize != 1)
            result.append(openCLVectorSize);
    }

    protected String visitSelector(final Selector sel, final String sourceName) {
        StringBuilder out = new StringBuilder();
        
        if (sel.getSign() < 0)
            out.append("-");
        final String lookupBladeCoeff = mvBladeMap.get(sourceName).get(sel.getIndex());
        if(lookupBladeCoeff == null) {
            if(sourceName.equals("1.0"))
                out.append("1");
            else
                out.append(sourceName);
        }
        else
            out.append(lookupBladeCoeff);
        
        return out.toString();
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return null;
    }

    protected int computeNearestOpenCLVectorSize(final int in) {
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
        else
            return maxOpenCLVectorSize;
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
        
        // compute bladeIndex coeff name
        final String bladeCoeff = getBladeCoeff(destMv,thisMvSetCount);
        // update bladeIndex map
        mvBladeMap.get(destMv).
                put(gappDotVectors.getDestSelector().getIndex(),bladeCoeff);

        // special case for operands of size 1
        final int operandSize = mvBladeMap.get(GAPPOpenCLCodeGenerator.getVarName(gappDotVectors.getParts().get(0).getName())).size();
        if(operandSize == 1) {
            result.append(bladeCoeff).append(" = ");
            visitDotVectorsParallelMultiply(gappDotVectors,0);
            return null;
        }
        
        // save dot count for multiplication to be used later
        final int multiplyDotCount = dotCount;
        
        // parallel multiply operation
        int operandSizeRemainder = operandSize;
        int openCLVectorSize;
        int subvectorIndex = 0;
        do {
            // get vector size
            openCLVectorSize = computeNearestOpenCLVectorSize(operandSizeRemainder);
            
            // print vector data type
            printOpenCLVectorType(openCLVectorSize);
            
            // print operation
            result.append(" ").append(GAPPOpenCLCodeGenerator.dot).append(multiplyDotCount);
            result.append("_").append(subvectorIndex);
            //visitWriteMask(operandSize);
            result.append(" = ");
            visitDotVectorsParallelMultiply(gappDotVectors,subvectorIndex);
            
            // compute operand size remainder
            operandSizeRemainder -= openCLVectorSize;
            // increment subvector index
            ++subvectorIndex;
        } while(operandSizeRemainder > 0);

        // in case of multiple float16 add them together first
        openCLVectorSize = computeNearestOpenCLVectorSize(operandSize);
        if(operandSize / maxOpenCLVectorSize > 1) {
            assert(openCLVectorSize == maxOpenCLVectorSize);
            
            // print vector data type (always float16 here)
            printOpenCLVectorType(maxOpenCLVectorSize);
            // print varname
            result.append(" ").append(GAPPOpenCLCodeGenerator.dot).append(dotCount+1);
            result.append("_0");
            // print assignment
            result.append(" = ");

            // print first addition
            result.append(" ").append(GAPPOpenCLCodeGenerator.dot).append(dotCount);
            result.append("_0");
            // print further additions
            operandSizeRemainder = operandSize - openCLVectorSize;
            subvectorIndex = 1;
            while(operandSizeRemainder / maxOpenCLVectorSize > 0) {
                // print operation
                result.append(" + ");
                result.append(" ").append(GAPPOpenCLCodeGenerator.dot).append(dotCount);
                result.append("_").append(subvectorIndex++);
            
                // compute operand size remainder
                operandSizeRemainder -= maxOpenCLVectorSize;
            }
            result.append(";\n");
        }
        // we created a new variable above
        ++dotCount;
        
        // parallel pyramid sum reduce operations
        int multiplyIndex = 1;
        openCLVectorSize = computeNearestOpenCLVectorSize(operandSize);
        while((openCLVectorSize >>= 1) > 1) {
            // print type
            printOpenCLVectorType(openCLVectorSize);
            // print varname
            result.append(" ").append(GAPPOpenCLCodeGenerator.dot).append(dotCount+1);
            result.append("_0");
            // print assignment
            result.append(" = ");
            // print addition
            result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append("_0").append(lo);
            result.append(" + ");
            result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append("_0").append(hi);
            
            // in case of another existing vector of same size
            // add it to this sum.
            if((operandSize % (openCLVectorSize << 1)) / openCLVectorSize > 0) {
                // mathematically, there can only be one more fitting vector
                // of that size.
                result.append(" + ");
                result.append(GAPPOpenCLCodeGenerator.dot).append(multiplyDotCount).append("_").append(multiplyIndex++);
                // (dotCount has to be from multiplication,
                // therefore use multiplyDotCount.
                // Count those using multiplyIndex.)
            }
            
            // print end of line
            result.append(";\n");

            ++dotCount;
        }

        // last step directly assigns to destination
        result.append(bladeCoeff);
        result.append(" = ");
        result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append("_0").append(lo);
        result.append(" + ");
        result.append(GAPPOpenCLCodeGenerator.dot).append(dotCount).append("_0").append(hi);
        result.append(";\n");
        ++dotCount;
        
        return null;
    }
    
    public void visitDotVectorsParallelMultiply(GAPPDotVectors gappDotVectors,
                                                final int subvectorIndex) {
        Iterator<GAPPVector> it = gappDotVectors.getParts().iterator();
        result.append(it.next().getName());
        result.append("_").append(subvectorIndex);
        while (it.hasNext()) {
            result.append(" * ");
            result.append(it.next().getName());
            result.append("_").append(subvectorIndex);
        }
        result.append(";\n");
    }
    
    public void visitWriteMask(int operandSize) {
        result.append(".s");
        for(int counter = 0; counter < operandSize; ++counter)
            result.append(getOpenCLIndex(counter));
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

        // create bladeIndex map
        Map<Integer,String> bladeMap = new HashMap<Integer,String>();
        Iterator<GAPPValueHolder> it = gappAssignInputsVector.getValues().iterator();
        while(it.hasNext()) {
            // instead of explicitly declaring the inputs vector
            // just put the elements into the bladeIndex map
            bladeMap.put(bladeMap.size(), it.next().prettyPrint());
        }

        // add bladeIndex map to multivector->bladeIndex map
        mvBladeMap.put(inputsArrayName,bladeMap);

        return null;
    }
}
