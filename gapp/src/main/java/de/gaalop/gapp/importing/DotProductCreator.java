package de.gaalop.gapp.importing;

import de.gaalop.gapp.importing.parallelObjects.Constant;
import de.gaalop.gapp.importing.parallelObjects.DotProduct;
import de.gaalop.gapp.importing.parallelObjects.ExtCalculation;
import de.gaalop.gapp.importing.parallelObjects.InputVariable;
import de.gaalop.gapp.importing.parallelObjects.MvComponent;
import de.gaalop.gapp.importing.parallelObjects.ParallelObject;
import de.gaalop.gapp.importing.parallelObjects.ParallelObjectVisitor;
import de.gaalop.gapp.importing.parallelObjects.Product;
import de.gaalop.gapp.importing.parallelObjects.Sum;

/**
 * Fills a given DotProduct
 * @author Christian Steinmetz
 */
public class DotProductCreator implements ParallelObjectVisitor {

    private DotProduct dotProduct;
    private int summandNo;

    /**
     * Calls DotProductsFinder to find more DotProducts in a given ParallelObject instance
     * @param object The ParallelObject instance
     */
    private DotProduct processMoreDotProducts(ParallelObject object) {
         DotProductsFinder finder = new DotProductsFinder();
         return (DotProduct) object.accept(finder, null);
    }

    public DotProductCreator(DotProduct dotProduct, int summandNo) {
        this.dotProduct = dotProduct;
        this.summandNo = summandNo;
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        

        int vectorNo = 0;
        for (ParallelObject factor: product.getFactors()) {
            dotProduct.set(summandNo, vectorNo, factor);

            DotProduct newDot = processMoreDotProducts(factor);
            if (newDot != null) 
                dotProduct.set(summandNo, vectorNo, newDot);

            if (product.isNegated()) {
                dotProduct.get(summandNo, vectorNo).negate();
                product.negate();
            }


            vectorNo++;
        }

        return null;
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        dotProduct.set(summandNo, 0, extCalculation);
        
        DotProduct newDot = processMoreDotProducts(extCalculation.getOperand1());
        if (newDot != null)
            extCalculation.setOperand1(newDot);

        if (extCalculation.getOperand2() != null) {
            DotProduct newDot2 = processMoreDotProducts(extCalculation.getOperand2());
            if (newDot2 != null)
                extCalculation.setOperand2(newDot2);
        }


        return null;
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        dotProduct.set(summandNo, 0, constant);
        return null;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        dotProduct.set(summandNo, 0, mvComponent);
        return null;
    }

    @Override
    public Object visitInputVariable(InputVariable inputVariable, Object arg) {
        dotProduct.set(summandNo, 0, inputVariable);
        return null;
    }

    // ========================== Illegal methods ==============================

    @Override
    public Object visitSum(Sum sum, Object arg) {
        throw new IllegalStateException("Sums are here not allowed");
    }

    @Override
    public Object visitDotProduct(DotProduct dotProduct, Object arg) {
        throw new IllegalStateException("DotProducts are here not allowed");
    }

}
