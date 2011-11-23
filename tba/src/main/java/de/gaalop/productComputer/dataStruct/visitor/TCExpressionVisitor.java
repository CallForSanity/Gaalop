package de.gaalop.productComputer.dataStruct.visitor;

import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.TCProduct;
import de.gaalop.productComputer.dataStruct.TCSum;

/**
 * Defines a visitor for the TCExpression data structure
 * @author Christian Steinmetz
 */
public interface TCExpressionVisitor {

    public Object visitTCBlade(TCBlade tcBlade, Object arg);
    public Object visitTCConstant(TCConstant tcConstant, Object arg);
    public Object visitTCProduct(TCProduct tcProduct, Object arg);
    public Object visitTCSum(TCSum tcSum, Object arg);

}
