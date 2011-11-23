package de.gaalop.productComputer.simplification;

import de.gaalop.algorithms.IntArray;
import de.gaalop.algorithms.Permutation;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.productComputer.dataStruct.TCSum;
import de.gaalop.productComputer.dataStruct.visitor.TCExpressionVisitor;
import java.util.LinkedList;

/**
 * Expands a TCExpression to summands
 * @author Christian Steinmetz
 */
public class SummandsCreator implements TCExpressionVisitor {

    //return ListTCExpr

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        ListTCExpr result = new ListTCExpr();
        result.add(tcBlade);
        return result;
    }

    @Override
    public Object visitTCConstant(TCConstant tcConstant, Object arg) {
        ListTCExpr result = new ListTCExpr();
        result.add(tcConstant);
        return result;
    }

    @Override
    public Object visitTCSum(TCSum tcSum, Object arg) {
        ListTCExpr summands = new ListTCExpr();
        for (int i=0;i<tcSum.getExpressions().length;i++)
            summands.addAll((ListTCExpr) tcSum.getExpressions()[i].accept(this, null));
        return summands;
    }

    @Override
    public Object visitTCProduct(TCProduct tcProduct, Object arg) {
        //visit factors
        ListTCExpr[] factors = new ListTCExpr[tcProduct.getExpressions().length];
        for (int i=0;i<tcProduct.getExpressions().length;i++)
            factors[i] = ((ListTCExpr) tcProduct.getExpressions()[i].accept(this, null));
        
        //compute permutations
        int[] lengths = new int[factors.length];
        int i=0;
        for (ListTCExpr summand: factors)
            lengths[i++] = summand.size();
        LinkedList<IntArray> permutations = Permutation.getPermutations(lengths);

        //set summands from permutations
        ListTCExpr result = new ListTCExpr();
        for (IntArray permutation: permutations) {
            TCExpression[] expressions = new TCExpression[lengths.length];
            int f=0;
            for (ListTCExpr summand: factors) {
                expressions[f] = summand.get(permutation.getArray()[f]).copyExpression();
                f++;
            }
            result.add(new TCProduct(expressions));
        }
        return result;
    }

}
