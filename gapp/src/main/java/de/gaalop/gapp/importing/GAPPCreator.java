package de.gaalop.gapp.importing;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.InputVariable;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectType;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectVisitor;
import de.gaalop.gapp.importing.parallelObjects.Product;
import de.gaalop.gapp.importing.parallelObjects.Sum;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
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
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Creates GAPP code from the ParallelObject data structure
 * @author Christian Steinmetz
 */
public class GAPPCreator implements ParallelObjectVisitor {
    // arg: GAPPMultivectorComponent: destination of this operation, otherwise null
    // return: GAPPMultivectorComponent if a new one is created, otherwise null

    public GAPP gapp;
    
    private int curTmp = -1;

    private final String PREFIX_MV = "mv";
    private final String PREFIX_VE = "ve";

    public void setGapp(GAPP gapp) {
        this.gapp = gapp;
    }

    

    private String createTMP(String prefix) {
        curTmp++;
        return prefix+curTmp;
    }

    public GAPPMultivector createMv() {
        GAPPMultivector mv = new GAPPMultivector(createTMP(PREFIX_MV));
        gapp.addInstruction(new GAPPResetMv(mv));
        return mv;
    }

    private GAPPVector createVe() {
        return new GAPPVector(createTMP(PREFIX_VE));
    }

    public GAPPCreator(GAPP gapp) {
        this.gapp = gapp;
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        DotProduct dp = new DotProduct();
        dp.setNegated(product.isNegated());
        int factorNo = 0;
        for (ParallelObject obj: product.getFactors()) {
            dp.set(0, factorNo, obj);
            factorNo++;
        }
        return dp.accept(this, arg);
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        //arg must be filled!

        GAPPMultivectorComponent destination = (GAPPMultivectorComponent) arg;

        GAPPMultivectorComponent altDestination = destination;
        if (extCalculation.isNegated())
            destination = new GAPPMultivectorComponent(createTMP(PREFIX_MV), 0);

        if (!extCalculation.getOperand1().isTerminal()) {
            GAPPMultivector mvTmp1 = createMv();
            GAPPMultivectorComponent gMvC1 = new GAPPMultivectorComponent(mvTmp1.getName(), 0);
            extCalculation.getOperand1().accept(this, gMvC1);
            extCalculation.setOperand1(new MvComponent(new MultivectorComponent(mvTmp1.getName(), 0)));
        }

        if (extCalculation.getOperand2() != null) {
            if (!extCalculation.getOperand2().isTerminal()) {
                GAPPMultivector mvTmp2 = createMv();
                GAPPMultivectorComponent gMvC2 = new GAPPMultivectorComponent(mvTmp2.getName(), 0);
                extCalculation.getOperand2().accept(this, gMvC2);
                extCalculation.setOperand2(new MvComponent(new MultivectorComponent(mvTmp2.getName(), 0)));
            }
        }


        gapp.addInstruction(new GAPPCalculateMv(extCalculation.getType(),
                new GAPPMultivector(destination.getName()),
                createMultivectorFromParallelObjectTerminal(extCalculation.getOperand1()),
                (extCalculation.getOperand2() == null)
                    ? null
                    : createMultivectorFromParallelObjectTerminal(extCalculation.getOperand2())
                ));

        if (extCalculation.isNegated()) {

            Selectorset selDest = new Selectorset();
            selDest.add(new Selector(altDestination.getBladeIndex(), (byte) 1));

            Selectorset selSrc = new Selectorset();
            selSrc.add(new Selector(destination.getBladeIndex(), (byte) -1));

            GAPPSetMv setMv = new GAPPSetMv(
                    new GAPPMultivector(altDestination.getName()),
                    new GAPPMultivector(destination.getName()),
                    selDest, selSrc);

            gapp.addInstruction(setMv);
        }

        return null;
    }

    private GAPPMultivector createMultivectorFromParallelObjectTerminal(ParallelObject object) {
         GAPPMultivectorCreator creator = new GAPPMultivectorCreator(this);
         return (GAPPMultivector) object.accept(creator, null);
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        //arg must be filled!
        GAPPMultivectorComponent destination = (GAPPMultivectorComponent) arg;

        Selectorset selSet = new Selectorset();
        selSet.add(new Selector(
                destination.getBladeIndex(),
                (constant.isNegated()) ? (byte) -1 : (byte) 1)
                );

        Variableset varSet = new Variableset();
        varSet.add(new GAPPConstant(constant.getValue()));

        gapp.addInstruction(new GAPPAssignMv(new GAPPMultivector(destination.getName()), selSet, varSet));

        return null;
    }

    //TODO chs optimize with assigning inputvalues at begin of clucalc programm in large multivector
    //and replace all inputvalues with the mvComponent

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        //arg must be filled!
        GAPPMultivectorComponent destination = (GAPPMultivectorComponent) arg;

        Selectorset selDestSet = new Selectorset();
        selDestSet.add(new Selector(
                destination.getBladeIndex(),
                (mvComponent.isNegated()) ? (byte) -1 : (byte) 1)
                );

        Selectorset selSrcSet = new Selectorset();
        selSrcSet.add(new Selector(
                mvComponent.getMultivectorComponent().getBladeIndex(),
                (byte) 1)
                );

        gapp.addInstruction(new GAPPSetMv(
                new GAPPMultivector(destination.getName()),
                new GAPPMultivector(mvComponent.getMultivectorComponent().getName()),
                selDestSet, selSrcSet));

        return null;
    }

    @Override
    public Object visitDotProduct(DotProduct dotProduct, Object arg) {

        //make all inner elements to terminals
        for (int row=0;row<dotProduct.getHeight();row++)
            for (int col=0;col<dotProduct.getWidth();col++) {
                if (!dotProduct.get(row, col).isTerminal()) {
                    ParallelObject nonTerminalObj = dotProduct.get(row, col);
                    GAPPMultivector mvTmp = createMv();
                    GAPPMultivectorComponent gMvC = new GAPPMultivectorComponent(mvTmp.getName(),0);
                    //TODO chs use GAPPSignedMultivectorComponent to reduce number of operations
                    nonTerminalObj.accept(this, gMvC);
                    dotProduct.set(row, col, new MvComponent(new MultivectorComponent(mvTmp.getName(), 0)));
                    //TODO chs Please be more efficient on memory (use all blades)
                }
            }

        //now all inner elements are terminals
        //transformation is now easier
        
        //optimize order in every row
        optimizeOrder(dotProduct);

        GAPPMultivectorComponent destination = (arg == null)
                ? new GAPPMultivectorComponent(createTMP(PREFIX_MV), 0)
                : (GAPPMultivectorComponent) arg;


        GAPPMultivectorComponent altDestination = destination;
        if (dotProduct.isNegated())
            destination = new GAPPMultivectorComponent(createTMP(PREFIX_MV), 0);

        //create vectors for GAPPDotProduct
        LinkedList<GAPPVector> parts = new LinkedList<GAPPVector>();
        GAPPDotVectors dotVectors = new GAPPDotVectors(destination, parts);

        for (ParallelVector vector: dotProduct.getFactors())
            parts.add(createVectorFromParallelVector(vector));

        gapp.addInstruction(dotVectors);

        if (dotProduct.isNegated()) {

            Selectorset selDest = new Selectorset();
            selDest.add(new Selector(altDestination.getBladeIndex(), (byte) 1));

            Selectorset selSrc = new Selectorset();
            selSrc.add(new Selector(destination.getBladeIndex(), (byte) -1));

            GAPPSetMv setMv = new GAPPSetMv(
                    new GAPPMultivector(altDestination.getName()),
                    new GAPPMultivector(destination.getName()),
                    selDest, selSrc);

            gapp.addInstruction(setMv);
        }


        
        return (arg == null) ? destination: null;
    }

    @Override
    public Object visitInputVariable(InputVariable inputVariable, Object arg) {
        //arg must be filled!
        GAPPMultivectorComponent destination = (GAPPMultivectorComponent) arg;

        Selectorset selSet = new Selectorset();
        selSet.add(new Selector(
                destination.getBladeIndex(),
                (inputVariable.isNegated()) ? (byte) -1 : (byte) 1)
                );
        
        Variableset varSet = new Variableset();
        varSet.add(new GAPPScalarVariable(inputVariable.getVariable().getName()));

        gapp.addInstruction(new GAPPAssignMv(new GAPPMultivector(destination.getName()), selSet, varSet));

        return null;
    }

    private void optimizeOrder(DotProduct dotProduct) {
        //TODO chs optimize order, so that less as possible multivectors are in one column
    }

    private GAPPVector createVectorFromParallelVector(ParallelVector vector) {
        GAPPVector destination = createVe();
        //It may occour:
        // - MvComponent
        // - Constant, InputVariable

        // distinguish four cases:
        //1. only MvComponents from one multivector (using setVector (one operation))
        //2. only Constants with/or InputVariables (using assignMv and then setVector (three operations))
        //3. only MvComponents from more than one multivector (using setMv and then setVector (> three operations))
        //4. a mix of all three types (using assignMv,setMv and then setVector (> three operations))

        int numberOfMvComponents = countTypeInParallelVector(vector, ParallelObjectType.mvComponent);
        
        if (numberOfMvComponents == 0) 
            case2(destination, vector);
        else {
            int numberOfConstants = countTypeInParallelVector(vector, ParallelObjectType.constant);
            int numberOfInputVariables = countTypeInParallelVector(vector, ParallelObjectType.inputVariable);

            HashSet<String> multivectors = getMultivectors(vector);

            if (numberOfConstants + numberOfInputVariables == 0) {
                // case 1 or 3
                
                if (multivectors.size() == 1) 
                    case1(destination, vector);
                else
                    case3(destination, vector, multivectors);
                
            } else 
                case4(destination, vector, multivectors);
        }

        return destination;
    }

    //only MvComponents from one multivector (using setVector (one operation))
    private void case1(GAPPVector destination, ParallelVector vector) {
        Selectorset selSet = new Selectorset();
        GAPPMultivector source = null;
        int slotNo = 0;
        for (ParallelObject slot: vector.getSlots()) {
            MultivectorComponent mvC = ((MvComponent) slot).getMultivectorComponent();
            if (slotNo == 0)
                source = new GAPPMultivector(mvC.getName());
            selSet.add(new Selector(mvC.getBladeIndex(), slot.isNegated() ? (byte) -1 : (byte) 1));
            slotNo++;
        }

        gapp.addInstruction(new GAPPSetVector(destination, source, selSet));
    }

    //only Constants with/or InputVariables (using assignMv and then setVector (three operations))
    private void case2(GAPPVector destination, ParallelVector vector) {
        GAPPMultivector mvTmp = createMv();
        Variableset varMvDestSet = new Variableset();

        Selectorset selSet = new Selectorset();
        int slotNo = 0;
        for (ParallelObject obj: vector.getSlots()) {
            GAPPValueHolder valueHolder = null;
            switch (ParallelObjectType.getType(obj)) {
                case constant:
                    valueHolder = new GAPPConstant(((Constant) obj).getValue());
                    break;
                case inputVariable:
                    valueHolder = new GAPPScalarVariable(((InputVariable) obj).getVariable().getName());
                    break;
            }
            varMvDestSet.add(valueHolder);
            selSet.add(new Selector(slotNo, obj.isNegated() ? (byte) -1: (byte) 1));
            slotNo++;
        }


        gapp.addInstruction(new GAPPAssignMv(
                    mvTmp,
                    selSet,
                    varMvDestSet
                ));

        gapp.addInstruction(new GAPPSetVector(
                    destination,
                    mvTmp,
                    createIncreasingSelectorSet(vector.getSlots().size())
                ));
    }

    private Selectorset createIncreasingSelectorSet(int numberOfSlots) {
        Selectorset result = new Selectorset();
        for (int slot = 0;slot<numberOfSlots;slot++)
            result.add(new Selector(slot, (byte) 1));
        return result;
    }

    //only MvComponents from more than one multivector (using setMv and then setVector (> three operations))
    //#GAPPInstructions: multivectors.size() + 2
    private void case3(GAPPVector destination, ParallelVector vector, HashSet<String> multivectors) {
        GAPPMultivector mvTmp = createMv();

        copyFromManyMultivectors(mvTmp, vector, multivectors);

        gapp.addInstruction(new GAPPSetVector(
                    destination,
                    mvTmp,
                    createIncreasingSelectorSet(vector.getSlots().size())
                ));
    }

    private void copyFromManyMultivectors(GAPPMultivector mvTmp, ParallelVector vector, HashSet<String> multivectors) {
        for (String mv: multivectors) {
            Selectorset selSetDest = new Selectorset();
            Selectorset selSetSrc = new Selectorset();

            int slotNo = 0;
            for (ParallelObject object: vector.getSlots()) {
                if (ParallelObjectType.getType(object) == ParallelObjectType.mvComponent) {
                    MultivectorComponent mvC = ((MvComponent) object).getMultivectorComponent();
                    if (mvC.getName().equals(mv)) {
                        selSetSrc.add(new Selector(mvC.getBladeIndex(), (byte) 1));
                        selSetDest.add(new Selector(slotNo, object.isNegated() ? (byte) -1 : (byte) 1));
                    }
                }

                slotNo++;
            }


            gapp.addInstruction(new GAPPSetMv(mvTmp, new GAPPMultivector(mv), selSetDest, selSetSrc));
        }
    }

    //a mix of all three types (using assignMv,setMv and then setVector (> three operations))
    //#GAPPInstructions: multivectors.size() + 3
    private void case4(GAPPVector destination, ParallelVector vector, HashSet<String> multivectors) {
        GAPPMultivector mvTmp = createMv();
        Variableset varMvDestSet = new Variableset();
        Selectorset selSet = new Selectorset();

        int slotNo = 0;
        for (ParallelObject obj: vector.getSlots()) {
            GAPPValueHolder valueHolder = null;
            switch (ParallelObjectType.getType(obj)) {
                case constant:
                    valueHolder = new GAPPConstant(((Constant) obj).getValue());
                    selSet.add(new Selector(slotNo, obj.isNegated() ? (byte) -1 : (byte) 1));
                    varMvDestSet.add(valueHolder);
                    break;
                case inputVariable:
                    valueHolder = new GAPPScalarVariable(((InputVariable) obj).getVariable().getName());
                    selSet.add(new Selector(slotNo, obj.isNegated() ? (byte) -1 : (byte) 1));
                    varMvDestSet.add(valueHolder);
                    break;
            }
            slotNo++;
        }

        copyFromManyMultivectors(mvTmp, vector, multivectors);

        
        gapp.addInstruction(new GAPPAssignMv(
                    mvTmp,
                    selSet,
                    varMvDestSet
                ));

        gapp.addInstruction(new GAPPSetVector(
                    destination,
                    mvTmp,
                    createIncreasingSelectorSet(vector.getSlots().size())
                ));
    }

    //TODO chs Think about problem with size of multivectors!
    //Is it possible to annotate every multivector with a arbitrary size (i.e > bladecount)?
    private HashSet<String> getMultivectors(ParallelVector vector) {
        HashSet<String> mvs = new HashSet<String>();
        for (ParallelObject obj: vector.getSlots())
            if (ParallelObjectType.getType(obj) == ParallelObjectType.mvComponent)
                mvs.add(((MvComponent) obj).getMultivectorComponent().getName());
        return mvs;
    }

    private int countTypeInParallelVector(ParallelVector vector, ParallelObjectType type) {
        int count = 0;
        for (ParallelObject obj: vector.getSlots())
            if (ParallelObjectType.getType(obj) == type)
                count++;
        return count;
    }



    // ========================= Illegal methods ===============================

    @Override
    public Object visitSum(Sum sum, Object arg) {
        throw new IllegalStateException("Sums should have been removed by DotProductCreator&Finder");
    }



}
