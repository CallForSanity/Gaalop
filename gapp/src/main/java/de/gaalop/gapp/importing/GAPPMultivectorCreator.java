package de.gaalop.gapp.importing;

import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Valueset;
import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParVariable;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectVisitor;
import de.gaalop.gapp.importing.parallelObjects.Product;
import de.gaalop.gapp.importing.parallelObjects.Sum;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.tba.Algebra;

/**
 * Creates a GAPPMultivector from ParallelObject, which is a terminal
 * @author Christian Steinmetz
 */
public class GAPPMultivectorCreator implements ParallelObjectVisitor {

    //return GAPPMultivector
    private GAPPCreator gappCreator;
    private int bladeCount;
    private Algebra algebra;

    public GAPPMultivectorCreator(GAPPCreator gappCreator, int bladeCount, Algebra algebra) {
        this.gappCreator = gappCreator;
        this.bladeCount = bladeCount;
        this.algebra = algebra;
    }

    @Override
    public Object visitVariable(ParVariable variable, Object arg) {
        throw new IllegalStateException("Variables shouldn't appear in GAPP transformation!");
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        GAPPMultivector mvTmp = gappCreator.createMv();

        PosSelectorset selDestSet = new PosSelectorset();
        selDestSet.add(new PosSelector(0, algebra.getBlade(0).toString()));

        Selectorset selSrcSet = new Selectorset();
        selSrcSet.add(new Selector(
                mvComponent.getMultivectorComponent().getBladeIndex(),
                mvComponent.isNegated() ? (byte) -1 : (byte) 1, 
                algebra.getBlade(mvComponent.getMultivectorComponent().getBladeIndex()).toString()));

        gappCreator.gapp.addInstruction(new GAPPSetMv(mvTmp,
                new GAPPMultivector(
                mvComponent.getMultivectorComponent().getName()),
                selDestSet,
                selSrcSet));
        return mvTmp;
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        GAPPMultivector mvTmp = gappCreator.createMv();

        PosSelectorset selDestSet = new PosSelectorset();
        selDestSet.add(new PosSelector(0, algebra.getBlade(0).toString()));

        Valueset valSet = new Valueset();
        valSet.add(new GAPPConstant((constant.isNegated() ? -1 : 1) * constant.getValue()));

        gappCreator.gapp.addInstruction(new GAPPAssignMv(mvTmp, selDestSet, valSet));
        return mvTmp;
    }

    // ========================== Illegal methods ==============================
    @Override
    public Object visitDotProduct(DotProduct dotProduct, Object arg) {
        throw new IllegalStateException("DotProducts are not allowed here");
    }

    @Override
    public Object visitSum(Sum sum, Object arg) {
        throw new IllegalStateException("Sums are not allowed here");
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        throw new IllegalStateException("Products are not allowed here");
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        throw new IllegalStateException("ExtCalculations are not allowed here");
    }
}
