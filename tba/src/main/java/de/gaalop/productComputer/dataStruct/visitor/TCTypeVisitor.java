package de.gaalop.productComputer.dataStruct.visitor;

import de.gaalop.productComputer.dataStruct.TCMultipleExpression;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.productComputer.dataStruct.TCSum;
import de.gaalop.productComputer.dataStruct.TCTerminal;

/**
 * Implements a type visitor for the TCExpression data structure
 * @author Christian Steinmetz
 */
public abstract class TCTypeVisitor implements TCExpressionVisitor {

    /**
     * This method is called whenever a multipleExpression is reached
     * @param multipleExpression The MulitpleExpression object
     * @param arg An optional argument
     * @return An optional return value
     */
    public abstract Object visitMultipleExpression(TCMultipleExpression multipleExpression, Object arg);

    /**
     * This method is called whenever a terminal is reached
     * @param terminal The terminal object
     * @param arg An optional argument
     * @return An optional return value
     */
    public abstract Object visitTerminal(TCTerminal terminal, Object arg);

    @Override
    public Object visitTCConstant(TCConstant tcConstant, Object arg) {
        return visitTerminal(tcConstant, arg);
    }

    @Override
    public Object visitTCProduct(TCProduct tcProduct, Object arg) {
        return visitMultipleExpression(tcProduct, arg);
    }

    @Override
    public Object visitTCSum(TCSum tcSum, Object arg) {
        return visitMultipleExpression(tcSum, arg);
    }

}
