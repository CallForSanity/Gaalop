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
import java.util.LinkedList;

/**
 * Finds DotProdurcs in a ParallelObject
 * @author Christian Steinmetz
 */
public class DotProductsFinder implements ParallelObjectVisitor {
    // return ParallelObject instance if created, otherwise null

    @Override
    public Object visitSum(Sum sum, Object arg) {
        // begin of a Dotproduct!
        DotProduct dotProduct = new DotProduct();
        int summandNo = 0;
        for (ParallelObject summand: sum.getSummands()) {
            DotProductCreator creator = new DotProductCreator(dotProduct, summandNo);
            summand.accept(creator, null);
            summandNo++;
        }

        return dotProduct;
    }

    @Override
    public Object visitProduct(Product product, Object arg) {

            LinkedList<ParallelObject> toRemove = new LinkedList<ParallelObject>();
            LinkedList<ParallelObject> toAdd = new LinkedList<ParallelObject>();

            for (ParallelObject factor: product.getFactors())  {
                DotProduct dp = (DotProduct) factor.accept(this, null);
                if (dp != null) {
                    toRemove.add(factor);
                    toAdd.add(dp);
                }
            }

            for (ParallelObject obj: toAdd)
                product.getFactors().add(obj);

            for (ParallelObject obj: toRemove)
                product.getFactors().remove(obj);


            return null;

    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        DotProduct dp1 = (DotProduct) extCalculation.getOperand1().accept(this, null);
        if (dp1 != null)
            extCalculation.setOperand1(dp1);
        
        if (extCalculation.getOperand2() != null) {
            DotProduct dp2 = (DotProduct) extCalculation.getOperand2().accept(this, null);
            if (dp2 != null)
                extCalculation.setOperand2(dp2);
        }

        return null;
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        return null;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        return null;
    }

    @Override
    public Object visitInputVariable(InputVariable inputVariable, Object arg) {
        return null;
    }

    @Override
    public Object visitDotProduct(DotProduct dotProduct, Object arg) {
        throw new IllegalStateException("DotProducts are here not allowed");
    }

}
