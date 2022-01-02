package de.gaalop.gapp.importing;

import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.gapp.ConstantSetVectorArgument;
import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.PairSetOfVariablesAndIndices;
import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.SetVectorArgument;
import de.gaalop.gapp.Valueset;
import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParVariable;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectType;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectVisitor;
import de.gaalop.gapp.importing.parallelObjects.Product;
import de.gaalop.gapp.importing.parallelObjects.Sum;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.tba.Algebra;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Creates GAPP code from the ParallelObject data structure
 * @author Christian Steinmetz
 */
public class GAPPCreator implements ParallelObjectVisitor {
    // arg: GAPPMultivectorComponent: destination of this operation, otherwise null
    // return: GAPPMultivectorComponent if a new one is created, otherwise null

    private static final boolean USE_DOTPRODUCT_OPTIMIZER = false;
    public GAPP gapp;
    private int curTmp = -1;
    private final String PREFIX_VE = "ve";
    private final String PREFIX_TMPMV = "tempmv";

    private HashSet<String> variables;
    private int bladeCount;
    private Algebra algebra;

    public GAPPCreator(HashSet<String> variables, int bladeCount, Algebra algebra) {
        this.variables = variables;
        this.bladeCount = bladeCount;
        this.algebra = algebra;
    }

    public void setGapp(GAPP gapp) {
        this.gapp = gapp;
    }

    /**
     * Creates a new temp variable name with a prefix
     * @param prefix The prefix that should be used
     * @return The temp variable name
     */
    private String createTMP(String prefix) {
        curTmp++;

        while (variables.contains(prefix + curTmp)) {
            curTmp++;
        }

        return prefix + curTmp;
    }

    /**
     * Creates a new temporary GAPPMultivector and inserts a resetMv command in GAPP
     * @return The new GAPPMultivector
     */
    public GAPPMultivector createMv() {
        GAPPMultivector mv = new GAPPMultivector(createTMP(PREFIX_TMPMV));
        gapp.addInstruction(new GAPPResetMv(mv, 1));
        return mv;
    }

    /**
     * Creates a temporary multivector with one entry and returns the according GAPPMultivectorComponent
     * @return The GAPPMultivectorComponent
     */
    public GAPPMultivectorComponent createMvComp() {
        GAPPMultivector mv = new GAPPMultivector(createTMP(PREFIX_TMPMV));
        gapp.addInstruction(new GAPPResetMv(mv, 1));
        return new GAPPMultivectorComponent(mv.getName(), 0);
    }

    /**
     * Creates a new GAPPVector
     * @return The new GAPPVector
     */
    private GAPPVector createVe() {
        return new GAPPVector(createTMP(PREFIX_VE));
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        DotProduct dp = new DotProduct();
        dp.setNegated(product.isNegated());
        int factorNo = 0;
        for (ParallelObject obj : product.getFactors()) {
            dp.set(0, factorNo, obj);
            factorNo++;
        }
        return dp.accept(this, arg);
    }

    @Override
    public Object visitVariable(ParVariable variable, Object arg) {
        throw new IllegalStateException("Variable should not appear in GAPPCreator!");
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        //arg must be filled! Note that this method is only called for scalar destinations!

        GAPPMultivectorComponent destination = (GAPPMultivectorComponent) arg;

        GAPPMultivectorComponent altDestination = destination;
        if (extCalculation.isNegated()) {
            destination = createMvComp();
        }

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


        gapp.addInstruction(new GAPPCalculateMvCoeff(extCalculation.getType(),
                destination,
                createMultivectorFromParallelObjectTerminal(extCalculation.getOperand1()),
                (extCalculation.getOperand2() == null)
                ? null
                : createMultivectorFromParallelObjectTerminal(extCalculation.getOperand2())));

        if (extCalculation.isNegated()) {

            PosSelectorset selDest = new PosSelectorset();
            selDest.add(new PosSelector(altDestination.getBladeIndex(), algebra.getBlade(altDestination.getBladeIndex()).toString()));

            Selectorset selSrc = new Selectorset();
            selSrc.add(new Selector(destination.getBladeIndex(), (byte) -1, algebra.getBlade(destination.getBladeIndex()).toString()));

            GAPPSetMv setMv = new GAPPSetMv(
                    new GAPPMultivector(altDestination.getName()),
                    new GAPPMultivector(destination.getName()),
                    selDest, selSrc);

            gapp.addInstruction(setMv);
        }


        return null;
    }

    /**
     * Creates a new GAPPMultivector from ParallelObject which is a terminal
     * @param object The ParallelObject which is a terminal
     * @return The new GAPPMultivector
     */
    private GAPPMultivector createMultivectorFromParallelObjectTerminal(ParallelObject object) {
        GAPPMultivectorCreator creator = new GAPPMultivectorCreator(this, bladeCount, algebra);
        return (GAPPMultivector) object.accept(creator, null);
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        //arg must be filled!
        GAPPMultivectorComponent destination = (GAPPMultivectorComponent) arg;

        PosSelectorset selSet = new PosSelectorset();
        selSet.add(new PosSelector(destination.getBladeIndex(), algebra.getBlade(destination.getBladeIndex()).toString()));

        Valueset valSet = new Valueset();
        valSet.add(new GAPPConstant(((constant.isNegated()) ? -1 : 1) * constant.getValue()));

        gapp.addInstruction(new GAPPAssignMv(new GAPPMultivector(destination.getName()), selSet, valSet));

        return null;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        //arg must be filled!
        GAPPMultivectorComponent destination = (GAPPMultivectorComponent) arg;

        PosSelectorset selDestSet = new PosSelectorset();
        selDestSet.add(new PosSelector(destination.getBladeIndex(), algebra.getBlade(destination.getBladeIndex()).toString()));

        Selectorset selSrcSet = new Selectorset();
        selSrcSet.add(new Selector(
                mvComponent.getMultivectorComponent().getBladeIndex(),
                (mvComponent.isNegated()) ? (byte) -1 : (byte) 1,
                algebra.getBlade(mvComponent.getMultivectorComponent().getBladeIndex()).toString()));

        gapp.addInstruction(new GAPPSetMv(
                new GAPPMultivector(destination.getName()),
                new GAPPMultivector(mvComponent.getMultivectorComponent().getName()),
                selDestSet, selSrcSet));

        return null;
    }

    @Override
    public Object visitDotProduct(DotProduct dotProduct, Object arg) {

        //make all inner elements to terminals
        for (int row = 0; row < dotProduct.getHeight(); row++) {
            for (int col = 0; col < dotProduct.getWidth(); col++) {
                if (!dotProduct.get(row, col).isTerminal()) {
                    ParallelObject nonTerminalObj = dotProduct.get(row, col);
                    GAPPMultivector mvTmp = createMv();
                    GAPPMultivectorComponent gMvC = new GAPPMultivectorComponent(mvTmp.getName(), 0);
                    nonTerminalObj.accept(this, gMvC);
                    dotProduct.set(row, col, new MvComponent(new MultivectorComponent(mvTmp.getName(), 0)));
                }
            }
        }

        //now all inner elements are terminals
        //transformation is now easier

        //optimize order in every row
        if (USE_DOTPRODUCT_OPTIMIZER) {
            new DotProductOptimizer().optimizeOrder(dotProduct);
        }

        GAPPMultivectorComponent destination = (arg == null)
                ? createMvComp()
                : (GAPPMultivectorComponent) arg;


        GAPPMultivectorComponent altDestination = destination;
        if (dotProduct.isNegated()) {
            destination = createMvComp();
        }

        //create vectors for GAPPDotProduct
        LinkedList<GAPPVector> parts = new LinkedList<GAPPVector>();
        GAPPDotVectors dotVectors = new GAPPDotVectors(destination, parts, algebra.getBlade(destination.getBladeIndex()).toString());

        for (ParallelVector vector : dotProduct.getFactors()) {
            parts.add(createVectorFromParallelVector(vector));
        }

        gapp.addInstruction(dotVectors);

        if (dotProduct.isNegated()) {

            PosSelectorset selDest = new PosSelectorset();
            selDest.add(new PosSelector(altDestination.getBladeIndex(), algebra.getBlade(altDestination.getBladeIndex()).toString()));

            Selectorset selSrc = new Selectorset();
            selSrc.add(new Selector(destination.getBladeIndex(), (byte) -1, algebra.getBlade(destination.getBladeIndex()).toString()));

            GAPPSetMv setMv = new GAPPSetMv(
                    new GAPPMultivector(altDestination.getName()),
                    new GAPPMultivector(destination.getName()),
                    selDest, selSrc);

            gapp.addInstruction(setMv);
        }



        return (arg == null) ? destination : null;
    }

    /**
     * Creates a GAPPVector from a ParallelVector
     * @param vector The ParallelVector
     * @return The new GAPPVector
     */
    private GAPPVector createVectorFromParallelVector(ParallelVector vector) {
        GAPPVector destination = createVe();
        //It may occour:
        // - MvComponent
        // - Constant

        LinkedList<SetVectorArgument> list = new LinkedList<SetVectorArgument>();
        SetVectorArgument last = null;
        for (ParallelObject object : vector.getSlots()) {
            switch (ParallelObjectType.getType(object)) {
                case constant:
                    last = new ConstantSetVectorArgument(((object.isNegated()) ? -1 : 1) * ((Constant) object).getValue());
                    list.add(last);
                    break;
                case mvComponent:
                    MultivectorComponent mvC = ((MvComponent) object).getMultivectorComponent();
                    Selector sel = new Selector(mvC.getBladeIndex(), object.isNegated() ? (byte) -1 : (byte) 1, algebra.getBlade(mvC.getBladeIndex()).toString());
                    if (last != null && !last.isConstant()) {
                        PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) last;
                        
                        if (pair.getSetOfVariable().getName().equals(mvC.getName())) {
                            //merge
                            pair.getSelectors().add(sel);
                        } else {
                            Selectorset selSet = new Selectorset();
                            selSet.add(sel);
                            last = new PairSetOfVariablesAndIndices(new GAPPMultivector(mvC.getName()), selSet);
                            list.add(last);
                        }
                    } else {
                        Selectorset selSet = new Selectorset();
                        selSet.add(sel);
                        last = new PairSetOfVariablesAndIndices(new GAPPMultivector(mvC.getName()), selSet);
                        list.add(last);
                    }
                    break;
                default:
                    System.err.println("createVectorFromParallelVector for "+ParallelObjectType.getType(object)+" is not implemented!");
                    break;

            }
        }
        gapp.addInstruction(new GAPPSetVector(destination, list));
        return destination;
    }

    // ========================= Illegal methods ===============================
    @Override
    public Object visitSum(Sum sum, Object arg) {
        throw new IllegalStateException("Sums should have been removed by DotProductCreator&Finder");
    }
}
