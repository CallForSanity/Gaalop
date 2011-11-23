package de.gaalop.productComputer.dataStruct.visitor;

import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCMultipleExpression;
import de.gaalop.productComputer.dataStruct.TCTerminal;

/**
 * Implements a replace visitor, which replaces a child of an TCExpression,
 * if the returned object of the latter method is not null
 * @author Christian Steinmetz
 */
public class TCReplaceVisitor extends TCTypeVisitor {

    protected TCExpression result = null;

    /**
     * Facade method for replacing expressions
     * @param expression The expression to search in
     * @return The new expression, if modified
     */
    public TCExpression replace(TCExpression expression) {
        expression.accept(this, null);
        return (result != null) ? result : expression;
    }

    @Override
    public Object visitMultipleExpression(TCMultipleExpression binaryExpression, Object arg) {
        for (int i=0;i<binaryExpression.getExpressions().length;i++) {
            binaryExpression.getExpressions()[i].accept(this, arg);
            if (result != null) {
                binaryExpression.getExpressions()[i] = result;
                result = null;
            }
        }
        return null;
    }

    @Override
    public Object visitTerminal(TCTerminal terminal, Object arg) {
        return null;
    }

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        return null;
    }

}
