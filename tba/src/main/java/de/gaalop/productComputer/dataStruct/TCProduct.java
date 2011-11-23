package de.gaalop.productComputer.dataStruct;

import de.gaalop.productComputer.dataStruct.visitor.TCExpressionVisitor;

/**
 * Represents a product
 * @author Christian Steinmetz
 */
public class TCProduct extends TCMultipleExpression {

    public TCProduct(TCExpression[] expressions) {
        super(expressions);
    }

    @Override
    public String toString() {
        return bracketComposite('*');
    }

    @Override
    public TCExpression copyExpression() {
        TCExpression[] expressionsCopy = new TCExpression[expressions.length];
        for (int i=0;i<expressionsCopy.length;i++)
            expressionsCopy[i] = expressions[i].copyExpression();
        return new TCProduct(expressionsCopy);
    }

    @Override
    public Object accept(TCExpressionVisitor visitor, Object arg) {
        return visitor.visitTCProduct(this, arg);
    }

    /**
     * Facade for creating a product from to TCExpressions
     * @param expr1 The first expression
     * @param expr2 The second expression
     * @return The product
     */
    public static TCProduct create(TCExpression expr1, TCExpression expr2) {
        TCExpression[] arr = new TCExpression[]{expr1,expr2};
        return new TCProduct(arr);
    }

}
