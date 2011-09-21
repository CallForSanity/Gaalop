package de.gaalop.gapp.importing;

import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.InputVariable;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectVisitor;
import de.gaalop.gapp.importing.parallelObjects.Product;
import de.gaalop.gapp.importing.parallelObjects.Sum;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.variables.GAPPConstant;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPScalarVariable;

/**
 *
 * @author Christian Steinmetz
 */
public class GAPPMultivectorCreator implements ParallelObjectVisitor {

    //return GAPPMultivector

    private GAPPCreator gappCreator;

    public GAPPMultivectorCreator(GAPPCreator gappCreator) {
        this.gappCreator = gappCreator;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
            GAPPMultivector mvTmp = gappCreator.createMv();

            Selectorset selDestSet = new Selectorset();
            selDestSet.add(new Selector(0, (byte) 1));

            Selectorset selSrcSet = new Selectorset();
            selSrcSet.add(new Selector(
                        mvComponent.getMultivectorComponent().getBladeIndex(),
                        mvComponent.isNegated() ? (byte) -1 : (byte) 1
                    )
            );

            gappCreator.gapp.addInstruction(new GAPPSetMv(mvTmp,
                    new GAPPMultivector(
                        mvComponent.getMultivectorComponent().getName()),
                        selDestSet,
                        selSrcSet
                    )
            );
            return mvTmp;
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        GAPPMultivector mvTmp = gappCreator.createMv();

        Selectorset selDestSet = new Selectorset();
        selDestSet.add(new Selector(0, constant.isNegated() ? (byte) -1 : (byte) 1));

        Variableset varSet = new Variableset();
        varSet.add(new GAPPConstant(constant.getValue()));

        gappCreator.gapp.addInstruction(new GAPPAssignMv(mvTmp,selDestSet,varSet));
        return mvTmp;
    }

    @Override
    public Object visitInputVariable(InputVariable inputVariable, Object arg) {
        GAPPMultivector mvTmp = gappCreator.createMv();

        Selectorset selDestSet = new Selectorset();
        selDestSet.add(new Selector(0, inputVariable.isNegated() ? (byte) -1 : (byte) 1));

        Variableset varSet = new Variableset();
        varSet.add(new GAPPScalarVariable(inputVariable.getVariable().getName()));

        gappCreator.gapp.addInstruction(new GAPPAssignMv(mvTmp,selDestSet,varSet));
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
