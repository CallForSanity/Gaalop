package de.gaalop.productComputer.transformer;

import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.productComputer.dataStruct.TCSum;
import de.gaalop.productComputer.dataStruct.visitor.TCExpressionVisitor;
import java.util.Stack;

/**
 * Creates Summands from a TCExpression
 * @author Christian Steinmetz
 */
public class SummandsCreator implements TCExpressionVisitor {

    private Summand result;
    private Stack<Float> prefactors = new Stack<Float>();

    public static Summand getMultivectorFromExpression(TCExpression expression) {
        SummandsCreator creator = new SummandsCreator();
        expression.accept(creator, null);
        return creator.result;
    }

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        float prefactor = 1;
        //if (tcBlade.isNegated()) prefactor *= -1;
        for (Float f: prefactors)
            prefactor *= f;

        result = new Summand(prefactor, tcBlade);
        return null;
    }

    @Override
    public Object visitTCConstant(TCConstant tcConstant, Object arg) {
        result = new Summand(tcConstant.getValue(), new TCBlade(new String[]{"1"}));
        return null;
    }

    @Override
    public Object visitTCProduct(TCProduct tcProduct, Object arg) {
        if (tcProduct.getExpressions()[0] instanceof TCConstant) {
            prefactors.push(((TCConstant) tcProduct.getExpressions()[0]).getValue());
            tcProduct.getExpressions()[1].accept(this, null);
            prefactors.pop();
        } else {
            prefactors.push(((TCConstant) tcProduct.getExpressions()[1]).getValue());
            tcProduct.getExpressions()[0].accept(this, null);
            prefactors.pop();
        }
        return null;
    }

    @Override
    public Object visitTCSum(TCSum tcSum, Object arg) {
        throw new IllegalStateException();
    }

    

}
