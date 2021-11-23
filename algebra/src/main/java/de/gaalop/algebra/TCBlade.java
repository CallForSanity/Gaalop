package de.gaalop.algebra;

import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.OuterProduct;
import java.util.Arrays;

/**
 * Defines a blade
 * @author Christian Steinmetz
 */
public class TCBlade {

    private String[] base;

    public TCBlade(String[] base) {
        this.base = base;
    }

    public String[] getBase() {
        return base;
    }

    public void setBase(String[] base) {
        this.base = base;
    }

    /**
     * Creates an expression from this blade
     * @return The expression
     */
    public Expression toExpression() {
        if (base.length == 1 && base[0].equals("1"))
            return new FloatConstant(1);

        Expression[] exprArr = new Expression[base.length];
        for (int i=0;i<exprArr.length;i++)
            exprArr[i] = new BaseVector(base[i].substring(1));
        
        return exprArrToOuterProduct(exprArr);
    }

    /**
     * Creates a single expression of expression array, which represents the wedge arguments
     * @param arr The expression array
     * @return The created expression
     */
    public static Expression exprArrToOuterProduct(Expression[] arr) {
        if (arr.length == 1) {
            return arr[0];
        } else {
            OuterProduct r = new OuterProduct(arr[0], null);
            OuterProduct cur = r;
            for (int i=1;i<arr.length-1;i++) {
                OuterProduct add = new OuterProduct(arr[i], null);
                cur.setRight(add);
                cur = add;
            }
            cur.setRight(arr[arr.length-1]);
            return r;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TCBlade) {
            TCBlade comp = (TCBlade) obj;
            return Arrays.equals(comp.base, this.base);
        } else
            return false;
    }

}
