package de.gaalop.productComputer.dataStruct;

import de.gaalop.productComputer.dataStruct.visitor.TCExpressionVisitor;

/**
 * Represents a sum
 * @author Christian Steinmetz
 */
public class TCSum extends TCMultipleExpression {

    public TCSum(TCExpression[] expressions) {
        super(expressions);
    }
    
    @Override
    public String toString() {
        return bracketComposite('+');
    }

    @Override
    public TCExpression copyExpression() {
        TCExpression[] expressionsCopy = new TCExpression[expressions.length];
        for (int i=0;i<expressionsCopy.length;i++)
            expressionsCopy[i] = expressions[i].copyExpression();
        return new TCSum(expressionsCopy);
    }

    @Override
    public Object accept(TCExpressionVisitor visitor, Object arg) {
        return visitor.visitTCSum(this, arg);
    }

    /**
     * Facade for creating a sum from to TCExpressions
     * @param expr1 The first expression
     * @param expr2 The second expression
     * @return The sum
     */
    public static TCSum create(TCExpression expr1, TCExpression expr2) {
        TCExpression[] arr = new TCExpression[]{expr1,expr2};
        return new TCSum(arr);
    }

}
